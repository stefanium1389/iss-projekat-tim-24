package tim24.projekat.uberapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim24.projekat.uberapp.DTO.DTOList;
import tim24.projekat.uberapp.DTO.PanicDTO;
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
}
