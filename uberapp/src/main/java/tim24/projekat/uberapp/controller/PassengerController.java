package tim24.projekat.uberapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim24.projekat.uberapp.DTO.DTOList;
import tim24.projekat.uberapp.DTO.RideDTO;
import tim24.projekat.uberapp.DTO.UserResponseDTO;

@RestController
@RequestMapping("api/passenger")
public class PassengerController
{
    @PostMapping
    public ResponseEntity<UserResponseDTO> postPassenger()
    {
        UserResponseDTO user = new UserResponseDTO();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<DTOList<UserResponseDTO>> getPassengers()
    {
        DTOList<UserResponseDTO> list = new DTOList<UserResponseDTO>();
        UserResponseDTO user = new UserResponseDTO();
        list.add(user);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/activate/{activationId}")
    public ResponseEntity<UserResponseDTO> activatePassenger(@PathVariable("activationId") Long id)
    {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getPassenger(@PathVariable("id") Long id)
    {
        UserResponseDTO user = new UserResponseDTO();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updatePassenger(@PathVariable("id") Long id)
    {
        UserResponseDTO user = new UserResponseDTO();
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
        DTOList<RideDTO> list = new DTOList<>();
        RideDTO ride = new RideDTO();
        list.add(ride);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
