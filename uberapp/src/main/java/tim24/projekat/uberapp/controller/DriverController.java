package tim24.projekat.uberapp.controller;

import java.util.ArrayList;

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

import tim24.projekat.uberapp.DTO.DtoList;
import tim24.projekat.uberapp.DTO.GeoCoordinate;
import tim24.projekat.uberapp.DTO.RideDTO;
import tim24.projekat.uberapp.DTO.UserDTO;
import tim24.projekat.uberapp.DTO.UserRef;
import tim24.projekat.uberapp.DTO.VehicleDTO;
import tim24.projekat.uberapp.model.DriverDocument;
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
	public ResponseEntity<UserDTO> CreateDriver() {
		UserDTO driver = new UserDTO(1L,"Stefan","Bogdanovic","profilePicture.jpg","+3810641234567","mail@email.com","Bulevar Oslobodjenja 169");
		return new ResponseEntity<UserDTO>(driver, HttpStatus.OK);
	}
	
	@GetMapping("driver")
	public ResponseEntity<DtoList<UserDTO>> GetAllDrivers() {
		DtoList<UserDTO> list = new DtoList<UserDTO>();
		UserDTO driver1 = new UserDTO(1L,"Stefan","Bogdanovic","profilePicture.jpg","+3810641234567","mail@email.com","Bulevar Oslobodjenja 169");
		list.add(driver1);
		UserDTO driver2 = new UserDTO(1L,"Pero","Perovic","profilePicture.jpg","+3810641235667","mail@email.com","Bulevar Evrope 42");
		list.add(driver2);
		return new ResponseEntity<DtoList<UserDTO>>(list, HttpStatus.OK);
	}
	@GetMapping("driver/{id}")
	public ResponseEntity<UserDTO> GetDriverDetails(@PathVariable("id") Long id){
		UserDTO driver = new UserDTO(1L,"Stefan","Bogdanovic","profilePicture.jpg","+3810641234567","mail@email.com","Bulevar Oslobodjenja 169");
		return new ResponseEntity<UserDTO>(driver, HttpStatus.OK);
	}
	@PutMapping("driver/{id}")
	public ResponseEntity<UserDTO> UpdateDriverDetails(@PathVariable("id") Long id){
		UserDTO driver = new UserDTO(1L,"Stefan","Bogdanovic","profilePicture.jpg","+3810641234567","mail@email.com","Bulevar Oslobodjenja 169");
		return new ResponseEntity<UserDTO>(driver, HttpStatus.OK);
	}
	@GetMapping("driver/{id}/documents")
	public ResponseEntity<ArrayList<DriverDocument>> GetDriverDocuments(@PathVariable("id") Long id){
		ArrayList<DriverDocument> list = new ArrayList<DriverDocument>();
		DriverDocument dd = new DriverDocument(1L,"stefanova vozacka","slika.png",1L);
		list.add(dd);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	@PostMapping("driver/{id}/documents")
	public ResponseEntity<DriverDocument> CreateDriverDocuments(@PathVariable("id") Long id){
		DriverDocument dd = new DriverDocument(1L,"stefanova vozacka","slika.png",1L);
		return new ResponseEntity<>(dd, HttpStatus.OK);
	}
	@DeleteMapping("driver/{id}/documents")
	public ResponseEntity<Error> DeleteDriverDocuments(@PathVariable("id") Long id){
		
		return new ResponseEntity<>( HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("driver/{id}/vehicle")
	public ResponseEntity<VehicleDTO> GetDriverVehicle(@PathVariable("id") Long id){
		VehicleDTO v = new VehicleDTO(1L, 1L,"STANDARD","Ford Mondeo","NS-42069", new GeoCoordinate("Kraj sveta",1,1), 4,false,false );
		
		return new ResponseEntity<VehicleDTO>(v, HttpStatus.OK);
	}
	@PutMapping("driver/{id}/vehicle")
	public ResponseEntity<VehicleDTO> UpdateDriverVehicle(@PathVariable("id") Long id){
		VehicleDTO v = new VehicleDTO(1L, 1L,"STANDARD","Ford Mondeo","NS-42069", new GeoCoordinate("Kraj sveta",1,1), 4,false,false );
		
		return new ResponseEntity<VehicleDTO>(v,HttpStatus.OK);
	}
	@PostMapping("driver/{id}/vehicle")
	public ResponseEntity<VehicleDTO> AddDriverVehicle(@PathVariable("id") Long id){
		VehicleDTO v = new VehicleDTO(1L, 1L,"STANDARD","Ford Mondeo","NS-42069", new GeoCoordinate("Kraj sveta",1,1), 4,false,false );
		
		return new ResponseEntity<VehicleDTO>(v, HttpStatus.OK);
	}
	
	@GetMapping("driver/{id}/working-hours")
	public ResponseEntity<DtoList<WorkingHour>> GetDriverWorkinghours(@PathVariable("id") Long id){
		DtoList<WorkingHour> list = new DtoList<WorkingHour>();
		WorkingHour wh = new WorkingHour(1L,"18.11.1991T19:00","19.11.1991T00:00");
		list.add(wh);
		return new ResponseEntity<DtoList<WorkingHour>>(list, HttpStatus.OK);
	}
	@PostMapping("driver/{id}/working-hours")
	public ResponseEntity<WorkingHour> CreateDriverWorkinghours(@PathVariable("id") Long id){
		WorkingHour wh = new WorkingHour(1L,"18.11.1991T19:00","19.11.1991T00:00");
		return new ResponseEntity<WorkingHour>(wh, HttpStatus.OK);
	}
	
	@GetMapping("driver/{id}/ride")
	public ResponseEntity<DtoList<RideDTO>> GetDriverRides(@PathVariable("id") Long id){
		DtoList<RideDTO> rides = new DtoList<RideDTO>();
		RideDTO r = new RideDTO(1L,"18.11.1991T19:30","18.11.1991T20:00",420,new UserRef(1L,"email@mail.com","VOZAC"),30,"STANDARD",false,false);
		r.addPassenger(new UserRef(1L, "mailic@mail.com","PUTNIK"));
		rides.add(r);
		return new ResponseEntity<DtoList<RideDTO>>(rides,HttpStatus.OK);
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
