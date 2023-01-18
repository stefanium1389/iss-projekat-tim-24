package tim24.projekat.uberapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim24.projekat.uberapp.DTO.ErrorDTO;
import tim24.projekat.uberapp.DTO.GeoCoordinateDTO;
import tim24.projekat.uberapp.DTO.SuccessDTO;
import tim24.projekat.uberapp.DTO.VehicleDTO;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.service.VehicleService;

@RestController
@RequestMapping("api/vehicle")
public class VehicleController
{
	@Autowired
	private VehicleService vehicleService;
	
    @PutMapping("/{id}/location")
    public ResponseEntity<?> putVehicleLocation(@PathVariable("id") Long id,@RequestBody GeoCoordinateDTO dto)
    {
    	try {
    		vehicleService.putVehicleLocation(id,dto); //izmeni ovo
    		SuccessDTO s = new SuccessDTO("Coordinates successfully updated");
            return new ResponseEntity<SuccessDTO>(s,HttpStatus.NO_CONTENT);
    	}
    	catch(ObjectNotFoundException e) {
    		ErrorDTO err = new ErrorDTO(e.getMessage());
    		return new ResponseEntity<ErrorDTO>(err,HttpStatus.NOT_FOUND);
    	}
    	
    }
    
}
