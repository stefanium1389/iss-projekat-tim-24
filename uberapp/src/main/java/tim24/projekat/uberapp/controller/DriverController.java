package tim24.projekat.uberapp.controller;

import java.util.ArrayList;
import java.util.List;

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

import tim24.projekat.uberapp.DTO.RideDTO;
import tim24.projekat.uberapp.model.DriverDocument;
import tim24.projekat.uberapp.model.GeoCoordinate;
import tim24.projekat.uberapp.model.User;
import tim24.projekat.uberapp.model.UserRef;
import tim24.projekat.uberapp.model.Vehicle;
import tim24.projekat.uberapp.model.WorkingHour;
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
	
	@PostMapping("driver")
	public ResponseEntity<User> CreateDriver() {
		User driver = new User();
		return new ResponseEntity<User>(driver, HttpStatus.OK);
	}
	
	@GetMapping("driver")
	public ResponseEntity<User> GetPaginatedDriverData() {
		
		User driver = new User(1L,"Stefan","Bogdanovic","profilePicture.jpg","+3810641234567","mail@email.com","Bulevar Oslobodjenja 169");
		
		return new ResponseEntity<User>(driver, HttpStatus.OK);
	}
	@GetMapping("driver/{id}")
	public ResponseEntity<User> GetDriverDetails(@PathVariable("id") Long id){
		User driver = new User(1L,"Stefan","Bogdanovic","profilePicture.jpg","+3810641234567","mail@email.com","Bulevar Oslobodjenja 169");
		return new ResponseEntity<User>(driver, HttpStatus.OK);
	}
	@PutMapping("driver/{id}")
	public ResponseEntity<User> UpdateDriverDetails(@PathVariable("id") Long id){
		User driver = new User(1L,"Stefan","Bogdanovic","profilePicture.jpg","+3810641234567","mail@email.com","Bulevar Oslobodjenja 169");
		return new ResponseEntity<User>(driver, HttpStatus.OK);
	}
	@GetMapping("driver/{id}/documents")
	public ResponseEntity<DriverDocument> GetDriverDocuments(@PathVariable("id") Long id){
		DriverDocument dd = new DriverDocument(1L,"stefanova vozacka","slika.png",1L);
		return new ResponseEntity<>(dd, HttpStatus.OK);
	}
	@PostMapping("driver/{id}/documents")
	public ResponseEntity<DriverDocument> CreateDriverDocuments(@PathVariable("id") Long id){
		DriverDocument dd = new DriverDocument(1L,"stefanova vozacka","slika.png",1L);
		return new ResponseEntity<>(dd, HttpStatus.OK);
	}
	@DeleteMapping("driver/{id}/documents")
	public ResponseEntity<DriverDocument> DeleteDriverDocuments(@PathVariable("id") Long id){
		DriverDocument dd = new DriverDocument(1L,"stefanova vozacka","slika.png",1L);
		return new ResponseEntity<DriverDocument>(dd, HttpStatus.OK);
	}
	
	@GetMapping("driver/{id}/vehicle")
	public ResponseEntity<Vehicle> GetDriverVehicle(@PathVariable("id") Long id){
		Vehicle v = new Vehicle(1L,"STANDARD","Ford Mondeo","NS-42069", new GeoCoordinate("Kraj sveta",1,1), 1,false,false );
		
		return new ResponseEntity<Vehicle>(v, HttpStatus.OK);
	}
	@PutMapping("driver/{id}/vehicle")
	public ResponseEntity<Vehicle> UpdateDriverVehicle(@PathVariable("id") Long id){
		Vehicle v = new Vehicle(1L,"STANDARD","Ford Mondeo","NS-42069", new GeoCoordinate("Kraj sveta",1,1), 1,false,false );
		
		return new ResponseEntity<Vehicle>(v,HttpStatus.OK);
	}
	@PostMapping("driver/{id}/vehicle")
	public ResponseEntity<Vehicle> AddDriverVehicle(@PathVariable("id") Long id){
		Vehicle v = new Vehicle(1L,"STANDARD","Ford Mondeo","NS-42069", new GeoCoordinate("Kraj sveta",1,1), 1,false,false );
		
		return new ResponseEntity<Vehicle>(v, HttpStatus.OK);
	}
	
	@GetMapping("driver/{id}/working-hours")
	public ResponseEntity<WorkingHour> GetDriverWorkinghours(@PathVariable("id") Long id){
		WorkingHour wh = new WorkingHour(1L,"18.11.1991T19:00","19.11.1991T00:00");
		return new ResponseEntity<WorkingHour>(wh, HttpStatus.OK);
	}
	@PostMapping("driver/{id}/working-hours")
	public ResponseEntity<WorkingHour> CreateDriverWorkinghours(@PathVariable("id") Long id){
		WorkingHour wh = new WorkingHour(1L,"18.11.1991T19:00","19.11.1991T00:00");
		return new ResponseEntity<WorkingHour>(wh, HttpStatus.OK);
	}
	
	@GetMapping("driver/{id}/ride")
	public ResponseEntity<List<RideDTO>> GetDriverRides(@PathVariable("id") Long id){
		List<RideDTO> rides = new ArrayList<RideDTO>();
		RideDTO r = new RideDTO(1L,"18.11.1991T19:30","18.11.1991T20:00",420,new UserRef(1L,"email@mail.com","VOZAC"),30,"STANDARD",false,false);
		r.addPassenger(new UserRef(1L, "mailic@mail.com","PUTNIK"));
		rides.add(r);
		return new ResponseEntity<List<RideDTO>>(rides,HttpStatus.OK);
	}
	
	@GetMapping("driver/{driver-id}/working-hour/{working-hour-id}")
	public ResponseEntity<WorkingHour> GetDriverWorkinghoursDetails(@PathVariable("driver-id") Long driverId, @PathVariable("working-hour-id") Long workinghoursId){
		WorkingHour wh = new WorkingHour(1L,"18.11.1991T19:00","19.11.1991T00:00");
		return new ResponseEntity<WorkingHour>(wh, HttpStatus.OK);
	}
	
	@PutMapping("driver/{driver-id}/working-hour/{working-hour-id}")
	public ResponseEntity<WorkingHour> ChangeDriverWorkinghoursDetails(@PathVariable("driver-id") Long driverId, @PathVariable("working-hour-id") Long workinghoursId){
		WorkingHour wh = new WorkingHour(1L,"18.11.1991T19:00","19.11.1991T00:00");
		return new ResponseEntity<WorkingHour>(wh, HttpStatus.OK);
	}
	
}
