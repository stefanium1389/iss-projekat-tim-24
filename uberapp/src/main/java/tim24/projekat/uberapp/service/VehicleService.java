package tim24.projekat.uberapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim24.projekat.uberapp.DTO.GeoCoordinateDTO;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.model.Location;
import tim24.projekat.uberapp.model.Vehicle;
import tim24.projekat.uberapp.repo.LocationRepository;
import tim24.projekat.uberapp.repo.VehicleRepository;

@Service
public class VehicleService
{
	@Autowired
	private VehicleRepository vehicleRepo;
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

}
