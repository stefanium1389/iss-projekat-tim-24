package tim24.projekat.uberapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim24.projekat.uberapp.DTO.DTOList;
import tim24.projekat.uberapp.DTO.PanicDTO;

@RestController
@RequestMapping("api/panic")
public class PanicController
{
    @GetMapping("")
    public ResponseEntity<DTOList<PanicDTO>> getPanic()
    {
        DTOList<PanicDTO> panicList = new DTOList<PanicDTO>();
        PanicDTO panicDto = new PanicDTO();
        panicList.add(panicDto);
        return new ResponseEntity<>(panicList, HttpStatus.OK);
    }
}
