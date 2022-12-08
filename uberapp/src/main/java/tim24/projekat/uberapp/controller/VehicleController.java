package tim24.projekat.uberapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim24.projekat.uberapp.DTO.VehicleDTO;

@RestController
@RequestMapping("api/vehicle")
public class VehicleController
{
    @PutMapping("/{id}/location")
    public ResponseEntity<VehicleDTO> putVehicleLocation(@PathVariable("id") Long id)
    {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
