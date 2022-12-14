package tim24.projekat.uberapp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim24.projekat.uberapp.DTO.PanicDTO;
import tim24.projekat.uberapp.DTO.RideDTO;
import tim24.projekat.uberapp.service.RideService;

@RestController
@RequestMapping("api/ride")
public class RideController
{
	
	@Autowired
	private RideService rideService;
	
    @PostMapping
    public ResponseEntity<RideDTO> postRide()
    {
        RideDTO ride = rideService.postRide();
        return new ResponseEntity<>(ride, HttpStatus.OK);
    }

    @GetMapping("/driver/{driverId}/active")
    public ResponseEntity<RideDTO> getDriverRide(@PathVariable("driverId") Long id)
    {

        RideDTO ride = rideService.getDriverRide(id);
        return new ResponseEntity<>(ride, HttpStatus.OK);
    }

    @GetMapping("/passenger/{passengerId}/active")
    public ResponseEntity<RideDTO> getPassengerRide(@PathVariable("passengerId") Long id)
    {
        
        RideDTO ride = rideService.getPassengerRide(id);
        return new ResponseEntity<>(ride, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RideDTO> getRides(@PathVariable("id") Long id)
    {
        
        RideDTO ride = rideService.getRides(id);
        return new ResponseEntity<>(ride, HttpStatus.OK);
    }

    @PutMapping("/{id}/withdraw")
    public ResponseEntity<RideDTO> withdrawRide(@PathVariable("id") Long id)
    {
        
        RideDTO ride = rideService.withdrawRide(id);
        return new ResponseEntity<>(ride, HttpStatus.OK);
    }

    @PutMapping("/{id}/panic")
    public ResponseEntity<PanicDTO> panicRide(@PathVariable("id") Long id)
    {
       
        PanicDTO panic = rideService.panicRide(id);
        return new ResponseEntity<>(panic, HttpStatus.OK);
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<RideDTO> acceptRide(@PathVariable("id") Long id)
    {
        
        RideDTO ride = rideService.acceptRide(id);
        return new ResponseEntity<>(ride, HttpStatus.OK);
    }

    @PutMapping("/{id}/end")
    public ResponseEntity<RideDTO> endRide(@PathVariable("id") Long id)
    {
       
        RideDTO ride = rideService.endRide(id);
        return new ResponseEntity<>(ride, HttpStatus.OK);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<RideDTO> cancelRide(@PathVariable("id") Long id)
    {
        
        RideDTO ride = rideService.cancelRide(id);
        return new ResponseEntity<>(ride, HttpStatus.OK);
    }
}
