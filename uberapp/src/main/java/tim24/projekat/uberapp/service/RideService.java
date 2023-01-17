package tim24.projekat.uberapp.service;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tim24.projekat.uberapp.DTO.*;
import tim24.projekat.uberapp.exception.ActiveUserRideException;
import tim24.projekat.uberapp.exception.ConditionNotMetException;
import tim24.projekat.uberapp.exception.InvalidArgumentException;
import tim24.projekat.uberapp.exception.InvalidRideStatusException;
import tim24.projekat.uberapp.exception.InvalidTimeException;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.model.*;
import tim24.projekat.uberapp.repo.*;
import tim24.projekat.uberapp.model.CreateRideResult;
import tim24.projekat.uberapp.model.DurationDistance;
import tim24.projekat.uberapp.model.Location;
import tim24.projekat.uberapp.model.Refusal;
import tim24.projekat.uberapp.model.Ride;
import tim24.projekat.uberapp.model.RideStatus;
import tim24.projekat.uberapp.model.Role;
import tim24.projekat.uberapp.model.Route;
import tim24.projekat.uberapp.model.User;
import tim24.projekat.uberapp.model.Vehicle;
import tim24.projekat.uberapp.model.VehicleType;
import tim24.projekat.uberapp.repo.LocationRepository;
import tim24.projekat.uberapp.repo.RideRepository;
import tim24.projekat.uberapp.repo.RouteRepository;
import tim24.projekat.uberapp.repo.UserRepository;
import tim24.projekat.uberapp.repo.VehicleRepository;
import tim24.projekat.uberapp.repo.VehicleTypeRepository;

@Service
public class RideService
{
	@Autowired
	private RideRepository rideRepo;
	@Autowired
	private VehicleRepository vehicleRepo;
	@Autowired
	private VehicleTypeRepository vehicleTypeRepo;
	@Autowired
	private LocationRepository locationRepo;
	@Autowired
	private RouteRepository routeRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private UserService userService;
	@Autowired
	private DriverService driverService;
	@Autowired
	private PanicRepository panicRepository;
	@Autowired
	private FavoriteRideRepository favoriteRideRepo;
	@Autowired
	private RefusalRepository refusalRepo;

	public Ride findRideById (Long id)
	{
		return rideRepo.findRideById(id).orElseThrow(()-> new ObjectNotFoundException("Ride not found."));
	}
	
	public RideDTO postRide(RideRequestDTO rideRequestDTO)
	{
		
		List<User> passengers = new ArrayList<User>();
		for(UserRef passengerDTO : rideRequestDTO.getPassengers()) {
			Optional<User> passenger = userRepo.findUserByEmail(passengerDTO.getEmail());
			if(passenger.isEmpty()) {
				throw new ObjectNotFoundException("Putnik ne postoji u bazi! "+passengerDTO.getEmail());
			}
			if(rideRepo.findActiveRideByPassengerId(passenger.get().getId()).isPresent()) {
				throw new ActiveUserRideException("Putnik "+passenger.get().getEmail()+" je vec u aktivnoj voznji");
			}
			passengers.add(passenger.get());
		}
		if(passengers.size() < 1) {
			throw new ObjectNotFoundException("Ne mozete zapoceti voznju bez putnika!");
		}
		
		Route route;
		DurationDistance dd;
		try {
			RouteDTO routeDto = rideRequestDTO.getLocations().get(0);
			GeoCoordinateDTO dep = routeDto.getDeparture();
			GeoCoordinateDTO dest = routeDto.getDestination();
			Location departure = new Location(dep.getLatitude(),dep.getLongitude(), dep.getAddress());
			Location destination = new Location(dest.getLatitude(),dest.getLongitude(), dest.getAddress());
			dd = getDurationDistance(dep.getLatitude(),dep.getLongitude(),dest.getLatitude(),dest.getLongitude());
			route = new Route(dd.getDistance(), (int)(dd.getDuration()/60), departure, destination);
		}
		catch(RuntimeException e){
			throw new RuntimeException("Neodgovarajuce lokacije u rideRequestDTO!");
		}
		
		boolean babyInVehicle = rideRequestDTO.isBabyTransport();
		boolean petInVehicle = rideRequestDTO.isPetTransport();
		RideStatus status = RideStatus.PENDING;
		CreateRideResult driverEstimation = null;
		User driver = null;
		if (rideRequestDTO.getScheduledTime() == null) 
		{
			driverEstimation = getBestDriverForRide(rideRequestDTO);
			driver = driverEstimation.getDriver(); 
		}
		
		boolean panic = false;
		Refusal refusal = null;
		
		Date startTime;
		Date endTime;
		Date scheduledTime;
		Date now = new Date(System.currentTimeMillis());
		
		if (rideRequestDTO.getScheduledTime() == null) //MOZDA BUDE PROBLEMA OVDE 
		{
			startTime = addMinutesToDate(now,driverEstimation.getMinutes());
			endTime = addMinutesToDate(now,driverEstimation.getMinutes());;
			scheduledTime = null;
		}
		else 
		{
			//seljacki nacin da se resi problem sa vremenskom zonom
			Date scheduled = subtractMinutesFromDate(parseDate(rideRequestDTO.getScheduledTime()),60);
			if (scheduled.before(now)) 
			{
				throw new InvalidTimeException("Scheduled date is in past!");
			}
			Date fiveHoursAfterNow = Date.from(now.toInstant().plus(Duration.ofHours(5)));
			if (scheduled.after(fiveHoursAfterNow)) 
			{
				throw new InvalidTimeException("Cant schedule more than 5 hours in future!");
			}
			
			startTime = scheduled;
			endTime = scheduled;
			scheduledTime = scheduled;
		}
		
		int totalCost = calculatePrice(rideRequestDTO.getVehicleType(),dd.getDistance());
		Optional<VehicleType> vehicleTypeOpt = vehicleTypeRepo.findOneByTypeName(rideRequestDTO.getVehicleType());
		if (vehicleTypeOpt.isEmpty()) 
		{
			throw new InvalidArgumentException("Selected vehicle type not found!");
		}
		VehicleType vehicleType = vehicleTypeOpt.get();
		//ovde se podrazumeva da je sve poslo po redu
		
		
		Ride ride = new Ride(startTime,endTime,scheduledTime,status,panic,babyInVehicle,petInVehicle,route,driver,refusal,passengers,totalCost,vehicleType);
		saveRideDetailsOnDatabase(ride);
		RideDTO dto = new RideDTO(ride);
		dto.setVehicleType(rideRequestDTO.getVehicleType());
		return dto;
	}
	
	public void saveRideDetailsOnDatabase(Ride ride) 
	{
		locationRepo.save(ride.getRoute().getStartLocation());
		locationRepo.save(ride.getRoute().getEndLocation());
		locationRepo.flush();
		
		routeRepo.save(ride.getRoute());
		routeRepo.flush();
		
		rideRepo.save(ride);
		rideRepo.flush();
	}
	
	public int calculatePrice(String type, double distance) 
	{
		Optional<VehicleType> typeOpt =vehicleTypeRepo.findOneByTypeName(type);
		if (typeOpt.isEmpty()) 
		{
			throw new ObjectNotFoundException("Vehicle type not found!");
		}
		
		VehicleType vehicleType = typeOpt.get();
		double costInDouble = vehicleType.getStartingPrice() + (distance/1000)*vehicleType.getPricePerKm();
		int cost = (int) costInDouble;
		return cost;
	}
	
	public CreateRideResult getBestDriverForRide(RideRequestDTO requestDTO) 
	{
		//VRATITI NA OVO!
		//List<Vehicle> vehicles = vehicleRepo.findDistinctVehiclesWithActiveWorkingHours();
		List<Vehicle> vehicles = vehicleRepo.findAll();
		if (vehicles.isEmpty()) 
		{
			throw new ConditionNotMetException("There are no vehicles!");
		}
		List<Vehicle> suitableVehicles = new ArrayList<Vehicle>();
		
		//provera za vozila
		for(Vehicle vehicle : vehicles) //i cant do sql
		{
			
			if (!(vehicle.getVehicleType().getTypeName().toString().toUpperCase().trim().equals(requestDTO.getVehicleType().toUpperCase().trim())))  //ovde mozda zezne
			{
				System.out.println(vehicle.getDriver().getName()+" nije prosao TIP proveru");
				continue;
			}
			if (vehicle.getNumberOfSeats() < requestDTO.getPassengers().size())
			{
				System.out.println(vehicle.getDriver().getName()+" nije prosao BROJ VOZILA proveru");
				continue;
			}
			if (requestDTO.isBabyTransport() && !vehicle.isAllowedBabyInVehicle())
			{
				System.out.println(vehicle.getDriver().getName()+" nije prosao BEBA proveru");
				continue;
			}
			if (requestDTO.isPetTransport() && !vehicle.isAllowedPetInVehicle())
			{
				System.out.println(vehicle.getDriver().getName()+" nije prosao LJUBIMAC proveru");
				continue;
			}
			suitableVehicles.add(vehicle);
		}
		
		HashMap<User, Integer> minutesMap = new HashMap<User,Integer>();
		//provera za vozače
		
		for(Vehicle v : suitableVehicles) 
		{
			User driver = v.getDriver();
			RouteDTO routeDto = requestDTO.getLocations().get(0);
			GeoCoordinateDTO dep = routeDto.getDeparture();
			GeoCoordinateDTO dest = routeDto.getDestination();
			Optional<Ride> activeOpt = rideRepo.findActiveRideByDriverId(driver.getId());
			Optional<List<Ride>> scheduledOpt = rideRepo.findPendingScheduledRidesByDriverId(driver.getId());
			int minutes = 0;
			Duration driverTodaysTime = driverService.getDurationOfTodaysWorkByDriverId(v.getDriver().getId());
			System.out.println("provera za "+v.getDriver().getName()+" ,scheduled velicina:" + scheduledOpt.get().size());
			boolean isScheduledOptEmpty = true;
			if (scheduledOpt.get().size() != 0) 
			{
				isScheduledOptEmpty = false;
			}
			
			//slucaj 1
			if (activeOpt.isEmpty() && isScheduledOptEmpty)
			{
				System.out.println("desio se slucaj #1");
				Location vehicleLoc = v.getLocation();
				DurationDistance dd = getDurationDistance(vehicleLoc.getLatitude(),vehicleLoc.getLongitude(),dep.getLatitude(), dep.getLongitude());
				minutes = (int) dd.getDuration()/60;
			}
			//slucaj 2
			else if (activeOpt.isPresent() && isScheduledOptEmpty)
			{
				System.out.println("desio se slucaj #2");
				
				Ride activeRide = activeOpt.get();
				Location activeEnd = activeRide.getRoute().getEndLocation();
				DurationDistance ddToNew = getDurationDistance(activeEnd.getLatitude(),activeEnd.getLongitude(), dep.getLatitude(), dep.getLongitude());
				if (activeRide.getStatus() == RideStatus.PENDING || activeRide.getStatus() == RideStatus.ACCEPTED) 
				{
					Duration timeToGetToPassenger = Duration.between(new Date().toInstant(),activeRide.getStartTime().toInstant()); //problematicno
					minutes = (int) timeToGetToPassenger.toMinutes() + activeRide.getEstimatedTime() +(int)ddToNew.getDuration()/60;
				}
				else
				{
					int minutesUntilEnd = this.getMinutesUntilRideCompletion(activeRide);
					minutes = minutesUntilEnd + (int) ddToNew.getDuration()/60;
				}
				
			}
			//slucaj 3
			else if (activeOpt.isEmpty() && !isScheduledOptEmpty)
			{	System.out.println("desio se slucaj #3");
				if (getRideDateIfCanStartBeforeFirstScheduled(requestDTO,scheduledOpt.get().get(0),v) != null) 
				{
					Location vehicleLoc = v.getLocation();
					DurationDistance dd = getDurationDistance(vehicleLoc.getLatitude(),vehicleLoc.getLongitude(),dep.getLatitude(), dep.getLongitude());
					minutes = (int) dd.getDuration()/60;
				}
				else 
				{
					Date whenCanRideBegin = getWhenCanRideBegin(scheduledOpt.get(), requestDTO);
					minutes = (int)Duration.between(new Date().toInstant(),whenCanRideBegin.toInstant()).toMinutes();
				}
			}
			//slucaj 4
			else if (activeOpt.isPresent() && !isScheduledOptEmpty)
			{	System.out.println("desio se slucaj #4");
				List<Ride> rides = new ArrayList<Ride>();
				rides.add(activeOpt.get());
				for (Ride r : scheduledOpt.get()) 
				{
					rides.add(r);
				}
				Date whenCanRideBegin = getWhenCanRideBegin(rides, requestDTO);
				minutes = (int)Duration.between(new Date().toInstant(),whenCanRideBegin.toInstant()).toMinutes();
			}
			//nesto ne valja xd
			else
			{
				throw new RuntimeException ("Error while checking for optimal driver!");
			}
			
			//if (driverTodaysTime.toMinutes() + (long)minutes < 8*60)
			if (true)
			{
				minutesMap.put(driver, minutes);
			}
			
		}
		
		//od odgovarajućih naći koji je najbolji
		System.out.println("                       ");
		User bestUser = null;
		int bestMinutes = 99999; //veoma glupavo, znam
		for (Map.Entry<User, Integer> set :
            minutesMap.entrySet()) {
			System.out.println("#finalni kandidat# "+ set.getKey().getName()+" - "+set.getValue()+ " minuta");
			if (set.getValue() < bestMinutes) 
			{
				bestUser = set.getKey();
				bestMinutes = set.getValue();
			}
       }
		
		if (bestUser == null) 
		{
			throw new RuntimeException("Cant find best user!");
		}
		System.out.println("### odabran je ### "+ bestUser.getName()+" - " + bestMinutes + " minuta");
		System.out.println("                       ");
		
		return new CreateRideResult(bestUser, bestMinutes);

	}
	
	//zovi samo za started voznje
	public int getMinutesUntilRideCompletion(Ride ride) 
	{
		
		if (ride.getStartTime().after(new Date())) 
		{
			throw new InvalidTimeException("Start time is after current time!");
		}
		
		Duration duration = Duration.between(ride.getStartTime().toInstant(), new Date().toInstant());
		int minutes = (int)duration.toMinutes();
		
		if (minutes >= ride.getEstimatedTime()) 
		{

			return ride.getEstimatedTime();
		}
		
		return ride.getEstimatedTime() - minutes;
		
	}
	
	public Date getRideDateIfCanStartBeforeFirstScheduled(RideRequestDTO requestDTO, Ride firstScheduledRide, Vehicle vehicle) 
	{
		RouteDTO routeDto = requestDTO.getLocations().get(0);
		GeoCoordinateDTO dep = routeDto.getDeparture();
		GeoCoordinateDTO dest = routeDto.getDestination();
		DurationDistance dd = getDurationDistance(dep.getLatitude(),dep.getLongitude(),dest.getLatitude(),dest.getLongitude());
		int durationInMinutes = (int) dd.getDuration()/60;
		Date suitableDate;
		
		
			Date nextStart = firstScheduledRide.getStartTime();
			Instant currentEndInstant = new Date().toInstant();
			
			int fromCurrentEndToStartMinutes = (int)getDurationDistance(vehicle.getLocation().getLatitude(), vehicle.getLocation().getLongitude(),
					dep.getLatitude(), dep.getLongitude()).getDuration()/60;
			Duration fromCurrentEndToStartDuration = Duration.ofMinutes(fromCurrentEndToStartMinutes);
			
			Location l = firstScheduledRide.getRoute().getStartLocation();
			int fromNewEndToStartNextMinutes = (int)getDurationDistance(dest.getLatitude(),dest.getLongitude()
					,l.getLatitude(), l.getLongitude()).getDuration()/60;
			Duration fromNewEndToStartNextDuration = Duration.ofMinutes(fromCurrentEndToStartMinutes);
			
			Duration freeTime = Duration.between(currentEndInstant, nextStart.toInstant());
			
			if (durationInMinutes < freeTime.minus(fromCurrentEndToStartDuration).minus(fromNewEndToStartNextDuration).toMinutes()) 
			{
				Instant ins = currentEndInstant.plus(Duration.ofMinutes(fromCurrentEndToStartMinutes));
				return Date.from(ins);
			}
			
			return null;
	}
	
	public Date getWhenCanRideBegin(List<Ride> rides, RideRequestDTO requestDTO)
	{
		RouteDTO routeDto = requestDTO.getLocations().get(0);
		GeoCoordinateDTO dep = routeDto.getDeparture();
		GeoCoordinateDTO dest = routeDto.getDestination();
		DurationDistance dd = getDurationDistance(dep.getLatitude(),dep.getLongitude(),dest.getLatitude(),dest.getLongitude());
		int durationInMinutes = (int) dd.getDuration()/60;
		Date suitableDate;
		for (int i = 0; i<rides.size()-1; i++) 
		{
			Date nextStart = rides.get(i+1).getStartTime();
			int currentEstimation = rides.get(i).getEstimatedTime();
			Instant currentStartInstant = rides.get(i).getStartTime().toInstant();
			Instant currentEndInstant = currentStartInstant.plus(Duration.ofMinutes(currentEstimation));
			
			Location currentEndLocation = rides.get(i).getRoute().getEndLocation();
			Location nextStartLocation = rides.get(i+1).getRoute().getStartLocation();
			
			int fromCurrentEndToStartMinutes = (int)getDurationDistance(currentEndLocation.getLatitude(), currentEndLocation.getLongitude(),
					dep.getLatitude(), dep.getLongitude()).getDuration()/60;
			Duration fromCurrentEndToStartDuration = Duration.ofMinutes(fromCurrentEndToStartMinutes);
			
			int fromNewEndToStartNextMinutes = (int)getDurationDistance(dest.getLatitude(),dest.getLongitude(),nextStartLocation.getLatitude(),
					nextStartLocation.getLongitude()).getDuration()/60;
			Duration fromNewEndToStartNextDuration = Duration.ofMinutes(fromCurrentEndToStartMinutes);
			
			Duration freeTime = Duration.between(currentEndInstant, nextStart.toInstant());
			
			if (durationInMinutes < freeTime.minus(fromCurrentEndToStartDuration).minus(fromNewEndToStartNextDuration).toMinutes()) 
			{
				Instant ins = currentEndInstant.plus(Duration.ofMinutes(fromCurrentEndToStartMinutes));
				return Date.from(ins);
			}
			
		}
		
		Date lastEnd = rides.get(rides.size()-1).getEndTime();
		Location lastEndLocation = rides.get(rides.size()-1).getRoute().getEndLocation();
		int fromLastEndToNewMinutes = (int)getDurationDistance(lastEndLocation.getLatitude(), lastEndLocation.getLongitude(),
				dep.getLatitude(), dep.getLongitude()).getDuration()/60;
		return Date.from(lastEnd.toInstant().plus(Duration.ofMinutes(fromLastEndToNewMinutes)));
	}

	public RideDTO getDriverRide(Long id)
	{
		Optional<Ride> ride = rideRepo.findActiveRideByDriverId(id);
		if(ride.isEmpty()) {
			throw new ObjectNotFoundException("Ride does not exist!");
		}
		RideDTO dto = new RideDTO(ride.get());
		if(ride.get().getDriver()!=null) {
			dto.setVehicleType(vehicleRepo.findVehicleByDriverId(ride.get().getDriver().getId()).get().getVehicleType().getTypeName());
		}
		return dto;
	}

	public RideDTO getPassengerRide(Long id)
	{
		Optional<Ride> ride = rideRepo.findActiveRideByPassengerId(id);
		if(ride.isEmpty()) {
			throw new ObjectNotFoundException("Ride does not exist!");
		}
		if(!ride.get().isActive()) {
			throw new ObjectNotFoundException("Active ride not found!");
		}
		RideDTO dto = new RideDTO(ride.get());
		if(ride.get().getDriver()!=null) {
			dto.setVehicleType(vehicleRepo.findVehicleByDriverId(ride.get().getDriver().getId()).get().getVehicleType().getTypeName());
		}
		return dto;
	}

	public RideDTO getRide(Long id)
	{
		Optional<Ride> ride = rideRepo.findRideByPassengersId(id);
		if(ride.isEmpty()) {
			throw new ObjectNotFoundException("Ride does not exist!");
		}
		RideDTO dto = new RideDTO(ride.get());
		dto.setVehicleType(vehicleRepo.findVehicleByDriverId(ride.get().getDriver().getId()).get().getVehicleType().getTypeName());
		return dto;
	}

	public RideDTO withdrawRide(Long id) //putnik ubija voznju
	{
		Optional<Ride> ride = rideRepo.findById(id);
		if(ride.isEmpty()) {
			throw new ObjectNotFoundException("Ride does not exist!");
		}
		RideStatus status = ride.get().getStatus();
		if(!status.equals(RideStatus.PENDING)  && !status.equals(RideStatus.ACCEPTED)) {
			throw new InvalidRideStatusException("Cannot cancel a ride that is not in status PENDING or ACCEPTED!");
		}
		Ride actualRide = ride.get();
		actualRide.setStatus(RideStatus.CANCELED);
		rideRepo.save(actualRide);
		rideRepo.flush();
		Optional<Vehicle> v = vehicleRepo.findVehicleByDriverId(3L);
		if(v.isEmpty()) {
			throw new ObjectNotFoundException("Vehicle does not exist!");
		}
		RideDTO dto = new RideDTO(actualRide);
		dto.setVehicleType(v.get().getVehicleType().getTypeName());
		return dto;
	}

	public PanicDTO panicRide(Long id, ReasonDTO reason, String userMail)
	{
		User user = userService.findUserByEmail(userMail);
		Ride ride = findRideById(id);
		Date time = new Date();
		if(ride.getStatus() != RideStatus.STARTED)
			throw new ConditionNotMetException("You cannot panic in a ride that isn't active.");
		if((user.getRole() == Role.USER && ! ride.getPassengers().contains(user)) || (user.getRole() == Role.DRIVER && user != ride.getDriver()))
			throw new ConditionNotMetException("You cannot panic in a ride that you aren't in.");
		Panic panic = panicRepository.save(new Panic(time, reason.getReason(), ride, user));
		ride.setPanic(true);
		rideRepo.save(ride);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		String timeString = sdf.format(time);
		PanicDTO panicDTO = new PanicDTO(panic.getId(), new UserRef(user), new RideDTO(ride), timeString, reason.getReason());
		return panicDTO;
	}

	public RideDTO acceptRide(Long id)
	{
		Optional<Ride> ride = rideRepo.findById(id);
		if(ride.isEmpty()) {
			throw new ObjectNotFoundException("Ride does not exist!");
		}
		if(ride.get().getStatus() != RideStatus.PENDING) {
			throw new InvalidRideStatusException("Cannot accept a ride that is not in status PENDING!");
		}
		Ride actualRide = ride.get();
		actualRide.setStatus(RideStatus.ACCEPTED);
		rideRepo.save(actualRide);
		rideRepo.flush();
		Optional<Vehicle> v = vehicleRepo.findVehicleByDriverId(3L);
		if(v.isEmpty()) {
			throw new ObjectNotFoundException("Vehicle does not exist!");
		}
		RideDTO dto = new RideDTO(actualRide);
		dto.setVehicleType(v.get().getVehicleType().getTypeName());
		return dto;
	}

	public RideDTO endRide(Long id)
	{
		Optional<Ride> ride = rideRepo.findById(id);
		if(ride.isEmpty()) {
			throw new ObjectNotFoundException("Ride does not exist!");
		}
		if(ride.get().getStatus() != RideStatus.STARTED) {
			throw new InvalidRideStatusException("Cannot end a ride that is not in status STARTED!");
		}
		Ride actualRide = ride.get();
		actualRide.setEndTime(new Date(System.currentTimeMillis()));
		actualRide.setStatus(RideStatus.FINISHED);
		rideRepo.save(actualRide);
		rideRepo.flush();
		Optional<Vehicle> v = vehicleRepo.findVehicleByDriverId(3L);
		if(v.isEmpty()) {
			throw new ObjectNotFoundException("Vehicle does not exist!");
		}
		RideDTO dto = new RideDTO(actualRide);
		dto.setVehicleType(v.get().getVehicleType().getTypeName());
		return dto;
	}

	public RideDTO cancelRide(Long id, ReasonDTO reason) //driver ubija voznju
	{
		Optional<Ride> ride = rideRepo.findById(id);
		if(ride.isEmpty()) {
			throw new ObjectNotFoundException("Ride does not exist!");
		}
		RideStatus status = ride.get().getStatus();
		if(!status.equals(RideStatus.PENDING)  && !status.equals(RideStatus.ACCEPTED)) {
			throw new InvalidRideStatusException("Cannot cancel a ride that is not in status PENDING!");
		}
		Ride actualRide = ride.get();
		actualRide.setStatus(RideStatus.REJECTED);
		Refusal ref = new Refusal(reason);
		actualRide.setRefusal(ref);
		refusalRepo.save(ref);
		refusalRepo.flush();
		rideRepo.save(actualRide);
		rideRepo.flush();
		Optional<Vehicle> v = vehicleRepo.findVehicleByDriverId(3L);
		if(v.isEmpty()) {
			throw new ObjectNotFoundException("Vehicle does not exist!");
		}
		RideDTO dto = new RideDTO(actualRide);
		dto.setVehicleType(v.get().getVehicleType().getTypeName());
		return dto;
	}

	public RideDTO startRide(Long id) {
		//this.GenerateRide(id,RideStatus.ACCEPTED); 	//generating valid ride because we have none in database, delete later
		//this.GenerateVehicle();						//same for vehicle
		
		Optional<Ride> ride = rideRepo.findById(id);
		if(ride.isEmpty()) {
			throw new ObjectNotFoundException("Ride does not exist!");
		}
		if(ride.get().getStatus() != RideStatus.ACCEPTED) {
			throw new InvalidRideStatusException("Cannot start a ride that is not in status ACCEPTED!");
		}
		Ride actualRide = ride.get();
		actualRide.setStartTime(new Date(System.currentTimeMillis()));
		actualRide.setStatus(RideStatus.STARTED);
		rideRepo.save(actualRide);
		rideRepo.flush();
		Optional<Vehicle> v = vehicleRepo.findVehicleByDriverId(3L);
		if(v.isEmpty()) {
			throw new ObjectNotFoundException("Vehicle does not exist!");
		}
		RideDTO dto = new RideDTO(actualRide);
		dto.setVehicleType(v.get().getVehicleType().getTypeName());
		return dto;
	}
	
	  public DurationDistance getDurationDistance(double startLat, double startLng, double endLat, double endLng) {
		  
		  	RestTemplate restTemplate = new RestTemplate();
	        String url = String.format("http://router.project-osrm.org/route/v1/driving/%s,%s;%s,%s", startLng, startLat, endLng, endLat);
	        OsrmResponse response = restTemplate.getForObject(url, OsrmResponse.class);

	        double duration = response.getRoutes().get(0).getDuration();
	        double distance = response.getRoutes().get(0).getDistance();

	        return new DurationDistance(duration, distance);
	    }
	  
	  	public Date parseDate (String date) 
		{
			Instant instant = Instant.parse(date);
			Date parsedDate = Date.from(instant);
			return parsedDate;
		}
	
	  	public Date addMinutesToDate(Date date, int minutes) 
		{
			Instant ins = date.toInstant();
			ins = ins.plus(Duration.ofMinutes(minutes));
			return Date.from(ins);
		}
	  	
	  	public Date subtractMinutesFromDate(Date date, int minutes) 
		{
			Instant ins = date.toInstant();
			ins = ins.minus(Duration.ofMinutes(minutes));
			return Date.from(ins);
		}

		public void assignDriverToScheduledRide() {
			System.out.println("periodicna provera da li ima zakazanih voznji");
			int threshold = 5;
			int timeout = 3;
			
			List<Ride> ridesWithoutDrivers = rideRepo.findScheduledRidesWithoutDriverInTimePeriod(new Date(), addMinutesToDate(new Date(), 15));
			System.out.println("broj zakazanih voznji kojima treba naci vozaca: "+ridesWithoutDrivers.size());
			
			for (Ride r : ridesWithoutDrivers) 
			{
				boolean found = false;
				RideRequestDTO dto = new RideRequestDTO(r);
				try 
				{
					CreateRideResult result = this.getBestDriverForRide(dto);
					Duration remainingDuration = Duration.between(new Date().toInstant(), r.getScheduledTime().toInstant());
					if (result.getMinutes() < (int)remainingDuration.toMinutes()) 
					{
						r.setDriver(result.getDriver());
						//todo: poslati notifikaciju korisniku
						rideRepo.save(r);
						found = true;
					}
					else if (result.getMinutes() < (int)remainingDuration.toMinutes()+threshold) 
					{
						int difference = (int)remainingDuration.toMinutes()+threshold - result.getMinutes();
						Date newTime = Date.from(r.getStartTime().toInstant().plus(Duration.ofMinutes(difference)));
						r.setStartTime(newTime);
						r.setDriver(result.getDriver());
						//todo: poslati notifikaciju korisniku
						rideRepo.save(r);
						found = true;
					}
					
				}
				catch(Exception e) 
				{
					System.out.println(e.getMessage()); //za sad
				}
				
				Duration d = Duration.between(new Date().toInstant(), r.getScheduledTime().toInstant());
				if (found == false && d.toMinutes()<=timeout) //ili je uvatio catch ili nema vozaca da stigne na vreme, a vec je tesko da ce se iko pojaviti
				{
					r.setStatus(RideStatus.CANCELED);
					System.out.println("zakazana voznja se otkazuje zbog timeout-a");
					rideRepo.save(r);
					//todo:poslati notifikaciju korisniku
				}
				
			}
			
		}

		public FavoriteRideResponseDTO postFavorite(String userMail, FavoriteRideDTO dto) {
			
			Optional<User> optUser = userRepo.findUserByEmail(userMail);
			if(optUser.isEmpty()) {
				throw new ObjectNotFoundException("User does not exist!");
			}
			
			List<FavoriteRide> fav = favoriteRideRepo.findAllFavoriteRideByPassengerId(optUser.get().getId());
			if(fav.size() == 10) {
				throw new InvalidArgumentException("Number of favorite rides cannot exceed 10!");
			}
			Optional<VehicleType> vtOpt = vehicleTypeRepo.findByTypeName(dto.getVehicleType());
			if(vtOpt.isEmpty()) {
				throw new InvalidArgumentException("Invalid vehicle type!");
			}
			Route route;
			DurationDistance dd;
			FavoriteRide favRide = new FavoriteRide(dto, optUser.get());
			try {
				RouteDTO routeDto = dto.getLocations().get(0);
				GeoCoordinateDTO dep = routeDto.getDeparture();
				GeoCoordinateDTO dest = routeDto.getDestination();
				Location departure = new Location(dep.getLatitude(),dep.getLongitude(), dep.getAddress());
				Location destination = new Location(dest.getLatitude(),dest.getLongitude(), dest.getAddress());
				dd = getDurationDistance(dep.getLatitude(),dep.getLongitude(),dest.getLatitude(),dest.getLongitude());
				route = new Route(dd.getDistance(), (int)(dd.getDuration()/60), departure, destination);
			}
			catch(RuntimeException e){
				throw new InvalidArgumentException("Neodgovarajuce lokacije u rideRequestDTO!");
			}
			favRide.setRoute(route);
			
			locationRepo.save(favRide.getRoute().getStartLocation());
			locationRepo.save(favRide.getRoute().getEndLocation());
			locationRepo.flush();
			
			routeRepo.save(favRide.getRoute());
			routeRepo.flush();
			
			favoriteRideRepo.save(favRide);
			favoriteRideRepo.flush();
			
			FavoriteRideResponseDTO dto2 = new FavoriteRideResponseDTO(favRide);
			return dto2;
		}

		public List<FavoriteRideResponseDTO> getFavorites(String userMail) {
			Optional<User> optUser = userRepo.findUserByEmail(userMail);
			if(optUser.isEmpty()) {
				throw new ObjectNotFoundException("User does not exist!");
			}			
			List<FavoriteRide> fav = favoriteRideRepo.findAllFavoriteRideByPassengerId(optUser.get().getId());
			ArrayList<FavoriteRideResponseDTO> favdto = new ArrayList<FavoriteRideResponseDTO>();
			for(FavoriteRide ride : fav) {
				favdto.add(new FavoriteRideResponseDTO(ride));
			}
			
			return  favdto;
		}

		public SuccessDTO deleteFavoriteRide(Long id) {
			Optional<FavoriteRide> fav = favoriteRideRepo.findById(id);
			if(fav.isEmpty()) {
				throw new ObjectNotFoundException("Favorite location does not exist!");
			}
			favoriteRideRepo.delete(fav.get());
			return new SuccessDTO("Successful deletion of favorite location!");
		}
		
		public UnregisteredResponseDTO postUnregistered(UnregisteredRequestDTO urd) {
			
			Optional<VehicleType> vtOpt = vehicleTypeRepo.findByTypeName(urd.getVehicleType());
			if(vtOpt.isEmpty()) {
				throw new InvalidArgumentException("Invalid vehicle type!");
			}
			if(urd.getLocations().size()==0) {
				throw new InvalidArgumentException("Invalid Locations!");
			}
			RouteDTO r = urd.getLocations().get(0);
			
			DurationDistance dd = getDurationDistance(
					r.getDeparture().getLatitude(), r.getDeparture().getLongitude(), 
					r.getDestination().getLatitude(), r.getDestination().getLongitude()
			);
			int price = calculatePrice(urd.getVehicleType(), dd.getDistance());
			
			UnregisteredResponseDTO u = new UnregisteredResponseDTO((long) dd.getDuration(),(long) price);
			return u;
		}


}
