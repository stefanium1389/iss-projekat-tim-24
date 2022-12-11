package tim24.projekat.uberapp.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim24.projekat.uberapp.DTO.RideDTO;

@RestController
@RequestMapping("api/ride")
public class RideController
{
    @PostMapping
    public ResponseEntity<RideDTO> postRide()
    {
        RideDTO ride = new RideDTO();
        return new ResponseEntity<>(ride, HttpStatus.OK);
    }
}
