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
import tim24.projekat.uberapp.DTO.UserUpdateRequestDTO;
import tim24.projekat.uberapp.exception.ActionExpiredException;
import tim24.projekat.uberapp.exception.EmailAlreadyExistsException;
import tim24.projekat.uberapp.exception.InvalidArgumentException;
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
    public ResponseEntity<DTOList<UserResponseDTO>> getPassengers(
    		@RequestParam("page") int page, 
			@RequestParam("size") int size)
    {
        DTOList<UserResponseDTO> list = passengerService.getPassengers(page, size);
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
            return new ResponseEntity<ErrorDTO>(dto, HttpStatus.BAD_REQUEST);
    	}
    	
    }
    @GetMapping("/activate/resend/{activationId}")
    public ResponseEntity<?> activatePassengerResend(@PathVariable("activationId") String id)
    {
    	try {
    		SuccessDTO dto = passengerService.resendActivation(id);
            return new ResponseEntity<SuccessDTO>(dto, HttpStatus.OK);
    	}
    	catch(ObjectNotFoundException e) {
    		ErrorDTO dto = new ErrorDTO(e.getMessage());
            return new ResponseEntity<ErrorDTO>(dto, HttpStatus.NOT_FOUND);
    	}    	
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPassenger(@PathVariable("id") Long id)
    {
    	try {
        UserResponseDTO user = passengerService.getPassenger(id);
        return new ResponseEntity<UserResponseDTO>(user, HttpStatus.OK);
    	}
    	catch(ObjectNotFoundException e){
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
		}
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePassenger(@RequestBody UserUpdateRequestDTO dto, @PathVariable("id") Long id)
    {
        try {
        UserResponseDTO user = passengerService.updatePassenger(id, dto);
        return new ResponseEntity<UserResponseDTO>(user, HttpStatus.OK);
        }
        catch(ObjectNotFoundException e) {
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
		}
		catch(Exception e) {
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error, HttpStatus.BAD_REQUEST);
		}
    }

    @GetMapping("/{id}/ride")
    public ResponseEntity<?> getPassengerRides(
            @PathVariable("id") Long id,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String sort,
            @RequestParam("from") String from,
            @RequestParam("to") String to)
    {
    	try {
        DTOList<RideDTO> list = passengerService.getPassengerRides(id,page,size,sort,from,to);
        return new ResponseEntity<DTOList<RideDTO>>(list, HttpStatus.OK);
        }
    	catch(ObjectNotFoundException e) {
    		Error error = new Error(e.getMessage());
    		return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
    	}	
		catch(InvalidArgumentException e) {
    		Error error = new Error(e.getMessage());
    		return new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);
    	}	
    }
}
