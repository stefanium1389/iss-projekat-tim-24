package tim24.projekat.uberapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim24.projekat.uberapp.repo.VehicleRepository;

@Service
public class VehicleService
{
	@Autowired
	private VehicleRepository vehicleRepo;

	public void putVehicleLocation(Long id)
	{
		// TODO Auto-generated method stub
	}

}
