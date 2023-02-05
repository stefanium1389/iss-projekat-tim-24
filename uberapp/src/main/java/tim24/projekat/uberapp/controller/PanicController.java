package tim24.projekat.uberapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim24.projekat.uberapp.DTO.DTOList;
import tim24.projekat.uberapp.DTO.ErrorDTO;
import tim24.projekat.uberapp.DTO.PanicDTO;
import tim24.projekat.uberapp.DTO.SuccessDTO;
import tim24.projekat.uberapp.exception.ActionExpiredException;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.service.PanicService;

@RestController
@RequestMapping("api/panic")
public class PanicController
{
	@Autowired
	private PanicService panicService;
	
    @GetMapping
    public ResponseEntity<DTOList<PanicDTO>> getPanic()
    {
        DTOList<PanicDTO> panicList = panicService.getPanic();
        return new ResponseEntity<>(panicList, HttpStatus.OK);
    }
    
    @GetMapping("/ride/{rideId}")
    public ResponseEntity<?> activatePassenger(@PathVariable("rideId") Long rideId)
    {
    	try {
    		DTOList<PanicDTO> panicList = panicService.getPanicForRide(rideId);
            return new ResponseEntity<>(panicList, HttpStatus.OK);
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
}
