package tim24.projekat.uberapp.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tim24.projekat.uberapp.DTO.*;
import tim24.projekat.uberapp.exception.ActiveUserRideException;
import tim24.projekat.uberapp.exception.ConditionNotMetException;
import tim24.projekat.uberapp.exception.InvalidArgumentException;
import tim24.projekat.uberapp.exception.InvalidRideStatusException;
import tim24.projekat.uberapp.exception.InvalidTimeException;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.security.JwtTokenUtil;
import tim24.projekat.uberapp.service.RideService;

@RestController
@RequestMapping("api/ride")
public class RideController
{
	
	@Autowired
	private RideService rideService;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
    @PostMapping
    public ResponseEntity<?> postRide(@RequestBody RideRequestDTO rideRequestDTO)
    {
    	try {
    		
	        RideDTO ride = rideService.postRide(rideRequestDTO);
	        return new ResponseEntity<RideDTO>(ride, HttpStatus.OK);
    	}
    	catch(ActiveUserRideException e) 
    	{
    		ErrorDTO error = new ErrorDTO(e.getMessage());
    		return new ResponseEntity<ErrorDTO>(error,HttpStatus.BAD_REQUEST);
    	}
    	catch(ObjectNotFoundException e) 
    	{
    		ErrorDTO error = new ErrorDTO(e.getMessage());
    		return new ResponseEntity<ErrorDTO>(error,HttpStatus.NOT_FOUND);
    	}
    	catch(InvalidTimeException e) 
    	{
    		ErrorDTO error = new ErrorDTO(e.getMessage());
    		return new ResponseEntity<ErrorDTO>(error,HttpStatus.BAD_REQUEST);
    	}
    	catch(ConditionNotMetException e) 
    	{
    		ErrorDTO error = new ErrorDTO(e.getMessage());
    		return new ResponseEntity<ErrorDTO>(error,HttpStatus.BAD_REQUEST);
    	}
    	catch(RuntimeException e) 
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

	@PreAuthorize("hasRole('USER') || hasRole('DRIVER')")
    @PutMapping("/{id}/panic")
    public ResponseEntity<?> panicRide(@PathVariable("id") Long id, @RequestBody ReasonDTO reason, @RequestHeader("Authorization") String auth)
    {
		try
		{
			String userMail = jwtTokenUtil.getUsernameFromToken(auth.substring(7));
			PanicDTO panic = rideService.panicRide(id, reason, userMail);
			return new ResponseEntity<>(panic, HttpStatus.OK);
		}
		catch(ObjectNotFoundException e)
		{
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		}
		catch(ConditionNotMetException e)
		{
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
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
    public ResponseEntity<?> cancelRide(@RequestBody ReasonDTO reason, @PathVariable("id") Long id)
    {
    	try {
    		RideDTO ride = rideService.cancelRide(id, reason);
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
    
    @PostMapping("/favorites")
    public ResponseEntity<?> postFavoriteRide(@RequestHeader("Authorization") String auth, @RequestBody FavoriteRideDTO dto){    	
    	try {
    		String userMail = jwtTokenUtil.getUsernameFromToken(auth.substring(7));
    		FavoriteRideResponseDTO dto2 = rideService.postFavorite(userMail, dto);
    		return new ResponseEntity<FavoriteRideResponseDTO>(dto2, HttpStatus.OK);
    	}
    	catch(InvalidArgumentException e) {
    		ErrorDTO error = new ErrorDTO(e.getMessage());
    		return new ResponseEntity<ErrorDTO>(error, HttpStatus.BAD_REQUEST);
    	}
    	catch(ObjectNotFoundException e) {
    		ErrorDTO error = new ErrorDTO(e.getMessage());
    		return new ResponseEntity<ErrorDTO>(error, HttpStatus.BAD_REQUEST);
    	}
    }
    
    @GetMapping("/favorites")
    public ResponseEntity<?> getFavoriteRides(@RequestHeader("Authorization") String auth){    	
    	try {
    		String userMail = jwtTokenUtil.getUsernameFromToken(auth.substring(7));
    		List<FavoriteRideResponseDTO> dto2 = rideService.getFavorites(userMail);
    		return new ResponseEntity<List<FavoriteRideResponseDTO>>(dto2, HttpStatus.OK);
    	}
    	catch(ObjectNotFoundException e) {
    		ErrorDTO error = new ErrorDTO(e.getMessage());
    		return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
    	}
    }
    @DeleteMapping("/favorites/{id}")
    public ResponseEntity<?> deleteFavoriteRide(@PathVariable("id") Long id){
    	try {
    		SuccessDTO dto = rideService.deleteFavoriteRide(id);
    		return new ResponseEntity<SuccessDTO>(dto, HttpStatus.NO_CONTENT);
    	}
    	catch(ObjectNotFoundException e) {
    		ErrorDTO error = new ErrorDTO(e.getMessage());
    		return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
    	}
    }
}
