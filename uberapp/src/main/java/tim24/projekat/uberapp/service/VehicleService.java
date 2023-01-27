package tim24.projekat.uberapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim24.projekat.uberapp.DTO.GeoCoordinateDTO;
import tim24.projekat.uberapp.DTO.VehicleChangeDTO;
import tim24.projekat.uberapp.DTO.VehicleDTO;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.model.Location;
import tim24.projekat.uberapp.model.Vehicle;
import tim24.projekat.uberapp.model.VehicleType;
import tim24.projekat.uberapp.repo.LocationRepository;
import tim24.projekat.uberapp.repo.VehicleRepository;
import tim24.projekat.uberapp.repo.VehicleTypeRepository;

@Service
public class VehicleService
{
	@Autowired
	private VehicleRepository vehicleRepo;
	@Autowired
	private VehicleTypeRepository vehicleTypeRepo;
	@Autowired
	private LocationRepository locationRepo;

	public void putVehicleLocation(Long id, GeoCoordinateDTO dto)
	{
		Optional<Vehicle> vOpt = vehicleRepo.findById(id);
		if(vOpt.isEmpty()) {
			throw new ObjectNotFoundException("Vehicle does not exist!");
		}
		Vehicle v = vOpt.get();
		Location l = v.getLocation();
		l.setAddress(dto.getAddress());
		l.setLatitude(dto.getLatitude());
		l.setLongitude(dto.getLongitude());
		v.setLocation(l);
		locationRepo.save(l);
		locationRepo.flush();
		vehicleRepo.save(v);
		vehicleRepo.flush();
		
	}

	public VehicleDTO getVehicle(Long id) {
		Optional<Vehicle> vOpt = vehicleRepo.findByDriverId(id);
	    if(vOpt.isPresent()) {
	        Vehicle v = vOpt.get();
	        return new VehicleDTO(v);
	    } else {
	        throw new ObjectNotFoundException("Vehicle does not exist!");
	    }
	}
	
	public VehicleDTO changeVehicle(Long id, VehicleChangeDTO dto) {
		Optional<Vehicle> vOpt = vehicleRepo.findByDriverId(id);
		if(vOpt.isEmpty()) {
			throw new ObjectNotFoundException("Vehicle does not exist!");
		}
		Vehicle v = vOpt.get();
		v.setAllowedBabyInVehicle(dto.isBabyTransport());
		v.setAllowedPetInVehicle(dto.isPetTransport());
		v.setModel(dto.getModel());
		v.setNumberOfSeats((int)dto.getPassengerSeats());
		
		
		
		Optional<VehicleType> vtOpt = vehicleTypeRepo.findOneByTypeName(dto.getVehicleType().toUpperCase());
		System.out.println(dto.getVehicleType().toUpperCase());
		if(vtOpt.isEmpty()) {
			throw new ObjectNotFoundException("Vehicle type does not exist!");
		}
		v.setVehicleType(vtOpt.get());
		vehicleRepo.save(v);
		vehicleRepo.flush();
		return new VehicleDTO(v);
	}

}
