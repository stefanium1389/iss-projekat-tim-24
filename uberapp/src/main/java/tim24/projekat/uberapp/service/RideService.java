package tim24.projekat.uberapp.service;

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

import tim24.projekat.uberapp.DTO.GeoCoordinateDTO;
import tim24.projekat.uberapp.DTO.OsrmResponse;
import tim24.projekat.uberapp.DTO.PanicDTO;
import tim24.projekat.uberapp.DTO.RideDTO;
import tim24.projekat.uberapp.DTO.RideRequestDTO;
import tim24.projekat.uberapp.DTO.RouteDTO;
import tim24.projekat.uberapp.DTO.UserRef;
import tim24.projekat.uberapp.exception.ActiveUserRideException;
import tim24.projekat.uberapp.exception.ConditionNotMetException;
import tim24.projekat.uberapp.exception.InvalidRideStatusException;
import tim24.projekat.uberapp.exception.InvalidTimeException;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
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
import tim24.projekat.uberapp.repo.LocationRepository;
import tim24.projekat.uberapp.repo.RideRepository;
import tim24.projekat.uberapp.repo.RouteRepository;
import tim24.projekat.uberapp.repo.UserRepository;
import tim24.projekat.uberapp.repo.VehicleRepository;

@Service
public class RideService
{
	@Autowired
	private RideRepository rideRepo;
	@Autowired
	private VehicleRepository vehicleRepo;
	@Autowired
	private LocationRepository locationRepo;
	@Autowired
	private RouteRepository routeRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private DriverService driverService;
	
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
		try {
			RouteDTO routeDto = rideRequestDTO.getLocations().get(0);
			GeoCoordinateDTO dep = routeDto.getDeparture();
			GeoCoordinateDTO dest = routeDto.getDestination();
			Location departure = new Location(dep.getLatitude(),dep.getLongitude());
			Location destination = new Location(dest.getLatitude(),dest.getLongitude());
			DurationDistance dd = getDurationDistance(dep.getLatitude(),dep.getLongitude(),dest.getLatitude(),dest.getLongitude());
			route = new Route(dd.getDistance(), (int)(dd.getDuration()/60), departure, destination);
		}
		catch(RuntimeException e){
			throw new RuntimeException("Neodgovarajuce lokacije u rideRequestDTO!");
		}
		
		Date startTime;
		Date endTime;
		Date scheduledTime;
		Date now = new Date(System.currentTimeMillis());
		if (rideRequestDTO.getScheduledTime() == null) //MOZDA BUDE PROBLEMA OVDE 
		{
			startTime = now;
			endTime = now;
			scheduledTime = null;
		}
		else 
		{
			Date scheduled = parseDate(rideRequestDTO.getScheduledTime());
			startTime = scheduled;
			endTime = scheduled;
			scheduledTime = scheduled;
		}
		boolean babyInVehicle = rideRequestDTO.isBabyTransport();
		boolean petInVehicle = rideRequestDTO.isPetTransport();
		RideStatus status = RideStatus.PENDING;
		CreateRideResult driverEstimation = getBestDriverForRide(rideRequestDTO);
		User driver = driverEstimation.getDriver(); 
		boolean panic = false;
		Refusal refusal = null;
		
		//ovde se podrazumeva da je sve poslo po redu
		
		Ride ride = new Ride(startTime,endTime,scheduledTime,status,panic,babyInVehicle,petInVehicle,route,driver,refusal,passengers);
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
	
	public CreateRideResult getBestDriverForRide(RideRequestDTO requestDTO) 
	{
		
		Optional<List<User>> optDrivers = userRepo.findAllByRole(Role.DRIVER);
		if (optDrivers.isEmpty()) 
		{
			throw new ConditionNotMetException("There are no drivers!");
		}
		
		Optional<List<Vehicle>> optVehicles = Optional.of(vehicleRepo.findAll());
		if (optVehicles.isEmpty()) 
		{
			throw new ConditionNotMetException("There are no vehicles!");
		}
		List<Vehicle> vehicles = optVehicles.get();
		List<Vehicle> suitableVehicles = new ArrayList<Vehicle>();
		
		//provera za vozila
		for(Vehicle vehicle : vehicles) //i cant do sql
		{
			System.out.println("pre suitable vehicle for-a: "+vehicle.getDriver().getName());
			if (!(vehicle.getVehicleType().getTypeName().toString().toUpperCase().trim().equals(requestDTO.getVehicleType().toUpperCase().trim())))  //ovde mozda zezne
			{
				System.out.println("prosao tip proveru");
				continue;
			}
			if (vehicle.getNumberOfSeats() < requestDTO.getPassengers().size())
			{
				continue;
			}
			if (requestDTO.isBabyTransport() && !vehicle.isAllowedBabyInVehicle())
			{
				continue;
			}
			if (requestDTO.isPetTransport() && !vehicle.isAllowedPetInVehicle())
			{
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
			System.out.println("da li je scheduled opt prazan: "+scheduledOpt.isEmpty());
			System.out.println("scheduled veleicina:" + scheduledOpt.get().size());
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
				DurationDistance dd = getDurationDistance(vehicleLoc.getGeoWidth(),vehicleLoc.getGeoHeight(),dep.getLatitude(), dep.getLongitude());
				minutes = (int) dd.getDuration()/60;
				System.out.println("minuti "+minutes);
			}
			//slucaj 2
			else if (activeOpt.isPresent() && isScheduledOptEmpty)
			{
				System.out.println("desio se slucaj #2");
				Ride activeRide = activeOpt.get();
				int minutesUntilEnd = this.getMinutesUntilRideCompletion(activeRide);
				Location activeEnd = activeRide.getRoute().getEndLocation();
				DurationDistance dd = getDurationDistance(activeEnd.getGeoWidth(),activeEnd.getGeoHeight(), dep.getLatitude(), dep.getLongitude());
				minutes = (int) dd.getDuration()/60 + minutesUntilEnd;
			}
			//slucaj 3
			else if (activeOpt.isEmpty() && !isScheduledOptEmpty)
			{	System.out.println("desio se slucaj #3");
				if (getRideIfCanStartBeforeFirstScheduled(requestDTO,scheduledOpt.get().get(0),v) != null) 
				{
					Location vehicleLoc = v.getLocation();
					DurationDistance dd = getDurationDistance(vehicleLoc.getGeoWidth(),vehicleLoc.getGeoHeight(),dep.getLatitude(), dep.getLongitude());
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
			
			if (true) 
			{
				minutesMap.put(driver, minutes);
			}
			
		}
		
		//od odgovarajućih naći koji je najbolji
		User bestUser = null;
		int bestMinutes = 99999; //veoma glupavo, znam
		for (Map.Entry<User, Integer> set :
            minutesMap.entrySet()) {
			System.out.println(set.getKey().getName());
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
		return new CreateRideResult(bestUser, bestMinutes);

	}
	
	//zovi samo za aktivne voznje
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
	
	public Date getRideIfCanStartBeforeFirstScheduled(RideRequestDTO requestDTO, Ride firstScheduledRide, Vehicle vehicle) 
	{
		RouteDTO routeDto = requestDTO.getLocations().get(0);
		GeoCoordinateDTO dep = routeDto.getDeparture();
		GeoCoordinateDTO dest = routeDto.getDestination();
		DurationDistance dd = getDurationDistance(dep.getLatitude(),dep.getLongitude(),dest.getLatitude(),dest.getLongitude());
		int durationInMinutes = (int) dd.getDuration()/60;
		Date suitableDate;
		
		
			Date nextStart = firstScheduledRide.getStartTime();
			Instant currentEndInstant = new Date().toInstant();
			
			int fromCurrentEndToStartMinutes = (int)getDurationDistance(vehicle.getLocation().getGeoWidth(), vehicle.getLocation().getGeoHeight(),
					dep.getLatitude(), dep.getLongitude()).getDuration()/60;
			Duration fromCurrentEndToStartDuration = Duration.ofMinutes(fromCurrentEndToStartMinutes);
			
			Location l = firstScheduledRide.getRoute().getStartLocation();
			int fromNewEndToStartNextMinutes = (int)getDurationDistance(dest.getLatitude(),dest.getLongitude()
					,l.getGeoWidth(), l.getGeoHeight()).getDuration()/60;
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
			
			int fromCurrentEndToStartMinutes = (int)getDurationDistance(currentEndLocation.getGeoWidth(), currentEndLocation.getGeoHeight(),
					dep.getLatitude(), dep.getLongitude()).getDuration()/60;
			Duration fromCurrentEndToStartDuration = Duration.ofMinutes(fromCurrentEndToStartMinutes);
			
			int fromNewEndToStartNextMinutes = (int)getDurationDistance(dest.getLatitude(),dest.getLongitude(),nextStartLocation.getGeoWidth(),
					nextStartLocation.getGeoHeight()).getDuration()/60;
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
		int fromLastEndToNewMinutes = (int)getDurationDistance(lastEndLocation.getGeoWidth(), lastEndLocation.getGeoHeight(),
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
		if(ride.get().getStatus() != RideStatus.PENDING || ride.get().getStatus() != RideStatus.STARTED) {
			throw new InvalidRideStatusException("Cannot cancel a ride that is not in status PENDING or STARTED!");
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

	public PanicDTO panicRide(Long id)
	{
		 PanicDTO panic = new PanicDTO();
		 return panic;
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

	public RideDTO cancelRide(Long id) //driver ubija voznju
	{
		Optional<Ride> ride = rideRepo.findById(id);
		if(ride.isEmpty()) {
			throw new ObjectNotFoundException("Ride does not exist!");
		}
		if(ride.get().getStatus() != RideStatus.PENDING) {
			throw new InvalidRideStatusException("Cannot cancel a ride that is not in status PENDING!");
		}
		Ride actualRide = ride.get();
		actualRide.setStatus(RideStatus.REJECTED);
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
	}



//	private void GenerateVehicle() {
//		VehicleType type = new VehicleType(1L,"STANDARDNO",200,100);
//		vehicleTypeRepo.save(type);
//		vehicleTypeRepo.flush();
//		Vehicle vehicle = new Vehicle(1L,"NS-069-XD",userRepo.findById(3L).get(),type,4,false,false);
//		vehicleRepo.save(vehicle);
//		vehicleRepo.flush();
//	}

//	public void GenerateRide(Long rideId, RideStatus status) {
//		Optional<User> driver = userRepo.findById(3L);
//		List<User> passengers = new ArrayList<User>();
//		passengers.add(userRepo.findById(2L).get());
//		passengers.add(userRepo.findById(4L).get());
//		Location dep = new Location(1L,69.42,69.42);
//		Location dest = new Location(2L,42.69,42.69);
//		locRepo.save(dep);
//		locRepo.save(dest);
//		locRepo.flush();
//		Route route = new Route(1L, 5.5, 11, dep, dest);
//		routeRepo.save(route);
//		routeRepo.flush();
//		Refusal refusal = new Refusal(1L,"iks de",new Date(System.currentTimeMillis()),driver.get());
//		refusalRepo.save(refusal);
//		refusalRepo.flush();
//		Ride ride = new Ride(rideId,new Date(System.currentTimeMillis()),new Date(System.currentTimeMillis()+690000),status,false,false,false,driver.get(),refusal,passengers,route);
//		rideRepo.save(ride);
//		rideRepo.flush();
//	}