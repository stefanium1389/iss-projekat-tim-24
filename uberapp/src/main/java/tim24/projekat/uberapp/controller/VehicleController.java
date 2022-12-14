package tim24.projekat.uberapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim24.projekat.uberapp.DTO.VehicleDTO;
import tim24.projekat.uberapp.service.VehicleService;

@RestController
@RequestMapping("api/vehicle")
public class VehicleController
{
	@Autowired
	private VehicleService vehicleService;
	
    @PutMapping("/{id}/location")
    public ResponseEntity<VehicleDTO> putVehicleLocation(@PathVariable("id") Long id)
    {
    	vehicleService.putVehicleLocation(id); //izmeni ovo
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
