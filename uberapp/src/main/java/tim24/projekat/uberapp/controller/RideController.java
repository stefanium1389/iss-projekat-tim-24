package tim24.projekat.uberapp.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim24.projekat.uberapp.DTO.PanicDTO;
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

    @GetMapping("/driver/{driverId}/active")
    public ResponseEntity<RideDTO> getDriverRide(@PathVariable("driverId") Long id)
    {
        RideDTO ride = new RideDTO();
        return new ResponseEntity<>(ride, HttpStatus.OK);
    }

    @GetMapping("/passenger/{passengerId}/active")
    public ResponseEntity<RideDTO> getPassengerRide(@PathVariable("passengerId") Long id)
    {
        RideDTO ride = new RideDTO();
        return new ResponseEntity<>(ride, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RideDTO> getRide(@PathVariable("id") Long id)
    {
        RideDTO ride = new RideDTO();
        return new ResponseEntity<>(ride, HttpStatus.OK);
    }

    @PutMapping("/{id}/withdraw")
    public ResponseEntity<RideDTO> withdrawRide(@PathVariable("id") Long id)
    {
        RideDTO ride = new RideDTO();
        return new ResponseEntity<>(ride, HttpStatus.OK);
    }

    @PutMapping("/{id}/panic")
    public ResponseEntity<PanicDTO> panicRide(@PathVariable("id") Long id)
    {
        PanicDTO panic = new PanicDTO();
        return new ResponseEntity<>(panic, HttpStatus.OK);
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<RideDTO> acceptRide(@PathVariable("id") Long id)
    {
        RideDTO ride = new RideDTO();
        return new ResponseEntity<>(ride, HttpStatus.OK);
    }

    @PutMapping("/{id}/end")
    public ResponseEntity<RideDTO> endRide(@PathVariable("id") Long id)
    {
        RideDTO ride = new RideDTO();
        return new ResponseEntity<>(ride, HttpStatus.OK);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<RideDTO> cancelRide(@PathVariable("id") Long id)
    {
        RideDTO ride = new RideDTO();
        return new ResponseEntity<>(ride, HttpStatus.OK);
    }
}
