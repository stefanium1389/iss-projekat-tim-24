package tim24.projekat.uberapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim24.projekat.uberapp.model.User;
import tim24.projekat.uberapp.service.DriverService;


@RestController
@RequestMapping("api/")
public class DriverController {
	
	private final DriverService DriverService;
	
	@Autowired
	public DriverController(DriverService DriverService) {
		super();
		this.DriverService = DriverService;
	}
	
	@PostMapping
	public ResponseEntity<UserResponseDTO> CreateDriver(
			@RequestBody UserRequestDTO newDriver) {
		UserResponseDTO driver = DriverService.createDriver(newDriver);
		return new ResponseEntity<UserResponseDTO>(driver, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<DTOList<UserResponseDTO>> GetAllDrivers(
			@RequestParam("page") int page, 
			@RequestParam("size") int size) {
		DTOList<UserResponseDTO> list = DriverService.GetAllDrivers(page, size);
		return new ResponseEntity<DTOList<UserResponseDTO>>(list, HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDTO> GetDriverDetails(
			@PathVariable("id") Long id){
		UserResponseDTO driver = new UserResponseDTO(1L,"Stefan","Bogdanovic","profilePicture.jpg","+3810641234567","mail@email.com","Bulevar Oslobodjenja 169");
		return new ResponseEntity<UserResponseDTO>(driver, HttpStatus.OK);
	}
	@GetMapping("driver/{id}")
	public ResponseEntity<User> GetDriverDetails(@PathVariable("id") Long id){
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@PutMapping("driver/{id}")
	public ResponseEntity<User> UpdateDriverDetails(@PathVariable("id") Long id){
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@GetMapping("driver/{id}/documents")
	public ResponseEntity<User> GetDriverDocuments(@PathVariable("id") Long id){
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@DeleteMapping("driver/{id}/documents")
	public ResponseEntity<User> DeleteDriverDocuments(@PathVariable("id") Long id){
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("driver/{id}/vehicle")
	public ResponseEntity<User> GetDriverVehicle(@PathVariable("id") Long id){
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@PutMapping("driver/{id}/vehicle")
	public ResponseEntity<User> UpdateDriverVehicle(@PathVariable("id") Long id){
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@PostMapping("driver/{id}/vehicle")
	public ResponseEntity<User> AddDriverVehicle(@PathVariable("id") Long id){
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("driver/{id}/working-hours")
	public ResponseEntity<User> GetDriverWorkinghours(@PathVariable("id") Long id){
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@PostMapping("driver/{id}/working-hours")
	public ResponseEntity<User> CreateDriverWorkinghours(@PathVariable("id") Long id){
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("driver/{id}/ride")
	public ResponseEntity<User> GetDriverRides(@PathVariable("id") Long id){
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("driver/{driver-id}/working-hour/{working-hour-id}")
	public ResponseEntity<User> GetDriverWorkinghoursDetails(@PathVariable("driver-id") Long driverId, @PathVariable("working-hour-id") Long workinghoursId){
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("driver/{driver-id}/working-hour/{working-hour-id}")
	public ResponseEntity<User> ChangeDriverWorkinghoursDetails(@PathVariable("driver-id") Long driverId, @PathVariable("working-hour-id") Long workinghoursId){
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
