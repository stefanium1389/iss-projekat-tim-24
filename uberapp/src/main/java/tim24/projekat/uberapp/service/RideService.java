package tim24.projekat.uberapp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim24.projekat.uberapp.DTO.GeoCoordinateDTO;
import tim24.projekat.uberapp.DTO.PanicDTO;
import tim24.projekat.uberapp.DTO.RideDTO;
import tim24.projekat.uberapp.DTO.RouteDTO;
import tim24.projekat.uberapp.DTO.UserRef;
import tim24.projekat.uberapp.exception.InvalidRideStatusException;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.model.Location;
import tim24.projekat.uberapp.model.Refusal;
import tim24.projekat.uberapp.model.Ride;
import tim24.projekat.uberapp.model.RideStatus;
import tim24.projekat.uberapp.model.Route;
import tim24.projekat.uberapp.model.User;
import tim24.projekat.uberapp.model.Vehicle;
import tim24.projekat.uberapp.model.VehicleType;
import tim24.projekat.uberapp.repo.LocationRepository;
import tim24.projekat.uberapp.repo.RefusalRepository;
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
	private UserRepository userRepo;
	@Autowired
	private RouteRepository routeRepo;
	@Autowired
	private LocationRepository locRepo;
	@Autowired
	private RefusalRepository refusalRepo;
	@Autowired
	private VehicleRepository vehicleRepo;
	@Autowired
	private VehicleTypeRepository vehicleTypeRepo;

	public RideDTO postRide()
	{
		RideDTO ride = new RideDTO();
		return ride;
	}

	public RideDTO getDriverRide(Long id)
	{
		this.GenerateRide(1L,RideStatus.PENDING);
		this.GenerateVehicle();
		Optional<Ride> ride = rideRepo.findRideByDriverId(id);
		if(ride.isEmpty()) {
			throw new ObjectNotFoundException("Ride does not exist!");
		}
		RideDTO dto = new RideDTO(ride.get());
		return dto;
	}

	public RideDTO getPassengerRide(Long id)
	{
		RideDTO ride = new RideDTO();
		return ride;
	}

	public RideDTO getRides(Long id)
	{
		RideDTO ride = new RideDTO();
		return ride;
	}

	public RideDTO withdrawRide(Long id)
	{
		RideDTO ride = new RideDTO();
		return ride;
	}

	public PanicDTO panicRide(Long id)
	{
		 PanicDTO panic = new PanicDTO();
		 return panic;
	}

	public RideDTO acceptRide(Long id)
	{
		RideDTO ride = new RideDTO();
		return ride;
	}

	public RideDTO endRide(Long id)
	{
		 RideDTO ride = new RideDTO();
		 return ride;
	}

	public RideDTO cancelRide(Long id)
	{
		RideDTO ride = new RideDTO();
		return ride;
	}

	public RideDTO startRide(Long id) {
		this.GenerateRide(id,RideStatus.ACCEPTED); 	//generating valid ride because we have none in database, delete later
		this.GenerateVehicle();						//same for vehicle
		
		Optional<Ride> ride = rideRepo.findById(id);
		if(ride.isEmpty()) {
			throw new ObjectNotFoundException("Ride does not exist!");
		}
		if(ride.get().getStatus() != RideStatus.ACCEPTED) {
			throw new InvalidRideStatusException("Cannot start a ride that is not in status ACCEPTED!");
		}
		Ride actualRide = ride.get();
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

	private void GenerateVehicle() {
		VehicleType type = new VehicleType(1L,"STANDARDNO",200,100);
		vehicleTypeRepo.save(type);
		vehicleTypeRepo.flush();
		Vehicle vehicle = new Vehicle(1L,"NS-069-XD",userRepo.findById(3L).get(),type,4,false,false);
		vehicleRepo.save(vehicle);
		vehicleRepo.flush();
	}

	public void GenerateRide(Long rideId, RideStatus status) {
		Optional<User> driver = userRepo.findById(3L);
		List<User> passengers = new ArrayList<User>();
		passengers.add(userRepo.findById(2L).get());
		passengers.add(userRepo.findById(4L).get());
		Location dep = new Location(1L,69.42,69.42);
		Location dest = new Location(2L,42.69,42.69);
		locRepo.save(dep);
		locRepo.save(dest);
		locRepo.flush();
		Route route = new Route(1L, 5.5, 11, dep, dest);
		routeRepo.save(route);
		routeRepo.flush();
		Refusal refusal = new Refusal(1L,"iks de",new Date(System.currentTimeMillis()),driver.get());
		refusalRepo.save(refusal);
		refusalRepo.flush();
		Ride ride = new Ride(rideId,new Date(System.currentTimeMillis()),new Date(System.currentTimeMillis()+690000),status,false,false,false,driver.get(),refusal,passengers,route);
		rideRepo.save(ride);
		rideRepo.flush();
	}
}