package tim24.projekat.uberapp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim24.projekat.uberapp.DTO.PanicDTO;
import tim24.projekat.uberapp.DTO.RideDTO;
import tim24.projekat.uberapp.DTO.RideRequestDTO;
import tim24.projekat.uberapp.DTO.ErrorDTO;
import tim24.projekat.uberapp.exception.ActiveUserRideException;
import tim24.projekat.uberapp.exception.InvalidRideStatusException;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.service.RideService;

@RestController
@RequestMapping("api/ride")
public class RideController
{
	@Autowired
	private RideService rideService;
	
    @PostMapping
    public ResponseEntity<?> postRide(@RequestBody RideRequestDTO rideRequestDTO)
    {
    	try {
    		System.out.println("loc iz requesta" + rideRequestDTO.getLocations().get(0).getDeparture().getLatitude());
	        RideDTO ride = rideService.postRide(rideRequestDTO);
	        return new ResponseEntity<RideDTO>(ride, HttpStatus.OK);
    	}
    	catch(ActiveUserRideException e) 
    	{
    		ErrorDTO error = new ErrorDTO(e.getMessage());
    		return new ResponseEntity<ErrorDTO>(error,HttpStatus.BAD_REQUEST);
    	}
    }

    @GetMapping("/driver/{driverId}/active")
    public ResponseEntity<?> getDriverRide(@PathVariable("driverId") Long id)
    {
    	try {
        RideDTO ride = rideService.getDriverRide(id);
        return new ResponseEntity<RideDTO>(ride, HttpStatus.OK);
    	}
    	catch(ObjectNotFoundException e) {
    		ErrorDTO error = new ErrorDTO(e.getMessage());
    		return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
    	}
    }

    @GetMapping("/passenger/{passengerId}/active")
    public ResponseEntity<?> getPassengerRide(@PathVariable("passengerId") Long id)
    {
    	try {
        RideDTO ride = rideService.getPassengerRide(id);
        return new ResponseEntity<RideDTO>(ride, HttpStatus.OK);
        }
    	catch(ObjectNotFoundException e) {
    		ErrorDTO error = new ErrorDTO(e.getMessage());
    		return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
    	}
    }

    @GetMapping("/{id}")
    public ResponseEntity<RideDTO> getRide(@PathVariable("id") Long id)
    {
        RideDTO ride = rideService.getRide(id);
        return new ResponseEntity<>(ride, HttpStatus.OK);
    }

    @PutMapping("/{id}/withdraw")
    public ResponseEntity<?> withdrawRide(@PathVariable("id") Long id)
    {
    	try {
    		RideDTO ride = rideService.withdrawRide(id);
            return new ResponseEntity<RideDTO>(ride, HttpStatus.OK);
    	}
    	catch(ObjectNotFoundException e) {
    		ErrorDTO error = new ErrorDTO(e.getMessage());
    		return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
    	}
    	catch(InvalidRideStatusException e) {
    		ErrorDTO error = new ErrorDTO(e.getMessage());
    		return new ResponseEntity<ErrorDTO>(error, HttpStatus.BAD_REQUEST);
    	}
    }

    @PutMapping("/{id}/panic")
    public ResponseEntity<PanicDTO> panicRide(@PathVariable("id") Long id)
    {
        PanicDTO panic = rideService.panicRide(id);
        return new ResponseEntity<>(panic, HttpStatus.OK);
    }
    @PutMapping("/{id}/start")
    public ResponseEntity<?> startRide(@PathVariable("id") Long id)
    {
    	try {
    		RideDTO ride = rideService.startRide(id);
            return new ResponseEntity<RideDTO>(ride, HttpStatus.OK);
    	}
    	catch(ObjectNotFoundException e) {
    		ErrorDTO error = new ErrorDTO(e.getMessage());
    		return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
    	}
    	catch(InvalidRideStatusException e) {
    		ErrorDTO error = new ErrorDTO(e.getMessage());
    		return new ResponseEntity<ErrorDTO>(error, HttpStatus.BAD_REQUEST);
    	}
        
    }
    @PutMapping("/{id}/accept")
    public ResponseEntity<?> acceptRide(@PathVariable("id") Long id)
    {
    	try {
        RideDTO ride = rideService.acceptRide(id);
        return new ResponseEntity<RideDTO>(ride, HttpStatus.OK);
    	}
    	catch(ObjectNotFoundException e) {
    		ErrorDTO error = new ErrorDTO(e.getMessage());
    		return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
    	}
    	catch(InvalidRideStatusException e) {
    		ErrorDTO error = new ErrorDTO(e.getMessage());
    		return new ResponseEntity<ErrorDTO>(error, HttpStatus.BAD_REQUEST);
    	}
    }

    @PutMapping("/{id}/end")
    public ResponseEntity<?> endRide(@PathVariable("id") Long id)
    {
    	try {
    		RideDTO ride = rideService.endRide(id);
            return new ResponseEntity<RideDTO>(ride, HttpStatus.OK);
    	}
        catch(ObjectNotFoundException e) {
    		ErrorDTO error = new ErrorDTO(e.getMessage());
    		return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
    	}
    	catch(InvalidRideStatusException e) {
    		ErrorDTO error = new ErrorDTO(e.getMessage());
    		return new ResponseEntity<ErrorDTO>(error, HttpStatus.BAD_REQUEST);
    	}
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelRide(@PathVariable("id") Long id)
    {
    	try {
    		RideDTO ride = rideService.cancelRide(id);
            return new ResponseEntity<RideDTO>(ride, HttpStatus.OK);
    	}
        catch(ObjectNotFoundException e) {
    		ErrorDTO error = new ErrorDTO(e.getMessage());
    		return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
    	}
    	catch(InvalidRideStatusException e) {
    		ErrorDTO error = new ErrorDTO(e.getMessage());
    		return new ResponseEntity<ErrorDTO>(error, HttpStatus.BAD_REQUEST);
    	}
    }
}
