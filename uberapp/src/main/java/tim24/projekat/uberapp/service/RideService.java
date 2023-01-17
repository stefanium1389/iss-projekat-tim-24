package tim24.projekat.uberapp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim24.projekat.uberapp.DTO.*;
import tim24.projekat.uberapp.exception.ActiveUserRideException;
import tim24.projekat.uberapp.exception.ConditionNotMetException;
import tim24.projekat.uberapp.exception.InvalidRideStatusException;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.model.*;
import tim24.projekat.uberapp.repo.*;

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
	private UserService userService;
	@Autowired
	private PanicRepository panicRepository;

	public Ride findRideById (Long id)
	{
		return rideRepo.findRideById(id).orElseThrow(()-> new ObjectNotFoundException("Ride not found."));
	}

	public RideDTO postRide(RideRequestDTO rideRequestDTO)
	{
		Ride ride = new Ride();
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
		ride.setPassengers(passengers);
		try {
			RouteDTO routeDto = rideRequestDTO.getLocations().get(0);
			GeoCoordinateDTO dep = routeDto.getDeparture();
			GeoCoordinateDTO dest = routeDto.getDestination();
			Location departure = new Location();
			departure.setGeoHeight(dep.getLatitude());
			departure.setGeoWidth(dep.getLongitude());
			Location destination = new Location();
			destination.setGeoHeight(dest.getLatitude());
			destination.setGeoWidth(dest.getLongitude());
			locationRepo.save(departure);
			locationRepo.save(destination);
			locationRepo.flush();
			Route route = new Route();
			route.setStartLocation(departure);
			route.setEndLocation(destination);
			routeRepo.save(route);
			routeRepo.flush();
			ride.setRoute(route);
		}
		catch(RuntimeException e){
			throw new RuntimeException("Neodgovarajuce lokacije u rideRequestDTO!");
		}
		ride.setStartTime(new Date(System.currentTimeMillis()));
		ride.setEndTime(new Date(System.currentTimeMillis()));
		ride.setBabyInVehicle(rideRequestDTO.isBabyTransport());
		ride.setPetInVehicle(rideRequestDTO.isPetTransport());
		ride.setStatus(RideStatus.PENDING);
		
		ride.setDriver(null); //ODRADITI ALGORITAM ZA ODREDJIVANJE VOZACA!
		
		rideRepo.save(ride);
		rideRepo.flush();
		
		
		RideDTO dto = new RideDTO(ride);
		dto.setVehicleType(rideRequestDTO.getVehicleType());
		return dto;
	}

	public RideDTO getDriverRide(Long id)
	{
		Optional<Ride> ride = rideRepo.findActiveRideByDriverId(id);
		if(ride.isEmpty()) {
			throw new ObjectNotFoundException("Ride does not exist!");
		}
		RideDTO dto = new RideDTO(ride.get());
		dto.setVehicleType(vehicleRepo.findVehicleByDriverId(ride.get().getDriver().getId()).get().getVehicleType().getTypeName());
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
		dto.setVehicleType(vehicleRepo.findVehicleByDriverId(ride.get().getDriver().getId()).get().getVehicleType().getTypeName());
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

	public PanicDTO panicRide(Long id, ReasonDTO reason, String userMail)
	{
		User user = userService.findUserByEmail(userMail);
		Ride ride = findRideById(id);
		Date time = new Date();
		if(ride.getStatus() != RideStatus.STARTED)
			throw new ConditionNotMetException("You cannot panic in a ride that isn't active.");
		if(! ride.getPassengers().contains(user))
			throw new ConditionNotMetException("You cannot panic in a ride that you aren't in.");
		Panic panic = panicRepository.save(new Panic(time, reason.getReason(), ride, user));
		PanicDTO panicDTO = new PanicDTO(panic.getId(), new UserRef(user), new RideDTO(ride), time.toString(), reason.getReason());
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
}