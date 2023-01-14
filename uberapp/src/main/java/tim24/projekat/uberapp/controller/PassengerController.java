package tim24.projekat.uberapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim24.projekat.uberapp.DTO.DTOList;
import tim24.projekat.uberapp.DTO.ErrorDTO;
import tim24.projekat.uberapp.DTO.RideDTO;
import tim24.projekat.uberapp.DTO.SuccessDTO;
import tim24.projekat.uberapp.DTO.UserRegistrationDTO;
import tim24.projekat.uberapp.DTO.UserResponseDTO;
import tim24.projekat.uberapp.exception.ActionExpiredException;
import tim24.projekat.uberapp.exception.EmailAlreadyExistsException;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.service.ActivationService;
import tim24.projekat.uberapp.service.PassengerService;

@RestController
@RequestMapping("api/passenger")
public class PassengerController
{
	@Autowired
	private PassengerService passengerService;
	@Autowired
	private ActivationService activationService;
	
    @PostMapping
    public ResponseEntity<?> postPassenger(@RequestBody UserRegistrationDTO userRegistrationDTO)
    {
    	try {
    		UserResponseDTO user = passengerService.postPassenger(userRegistrationDTO);
            return new ResponseEntity<UserResponseDTO>(user, HttpStatus.OK);
    	}
        catch(EmailAlreadyExistsException e) {
        	ErrorDTO error = new ErrorDTO(e.getMessage());
        	return new ResponseEntity<ErrorDTO>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<DTOList<UserResponseDTO>> getPassengers()
    {
        DTOList<UserResponseDTO> list = passengerService.getPassengers();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/activate/{activationId}")
    public ResponseEntity<?> activatePassenger(@PathVariable("activationId") String id)
    {
    	try {
    		SuccessDTO dto = activationService.activatePassenger(id);
            return new ResponseEntity<SuccessDTO>(dto, HttpStatus.OK);
    	}
    	catch(ObjectNotFoundException e) {
    		ErrorDTO dto = new ErrorDTO(e.getMessage());
            return new ResponseEntity<ErrorDTO>(dto, HttpStatus.NOT_FOUND);
    	}
    	catch(ActionExpiredException e) {
    		ErrorDTO dto = new ErrorDTO(e.getMessage());
            return new ResponseEntity<ErrorDTO>(dto, HttpStatus.NOT_FOUND);
    	}
    	
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getPassenger(@PathVariable("id") Long id)
    {
        UserResponseDTO user = passengerService.getPassenger(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updatePassenger(@PathVariable("id") Long id)
    {
        
        UserResponseDTO user = passengerService.updatePassenger(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/{id}/ride")
    public ResponseEntity<DTOList<RideDTO>> getPassengerRides(
            @PathVariable("id") Long id,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String sort,
            @RequestParam("from") String from,
            @RequestParam("to") String to)
    {
    	
        DTOList<RideDTO> list = passengerService.getPassengerRides(id,page,size,sort,from,to);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
