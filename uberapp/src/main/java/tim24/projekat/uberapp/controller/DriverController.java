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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim24.projekat.uberapp.DTO.DriverDocumentDTO;
import tim24.projekat.uberapp.DTO.DriverDocumentRequestDTO;
import tim24.projekat.uberapp.DTO.DTOList;
import tim24.projekat.uberapp.DTO.GeoCoordinateDTO;
import tim24.projekat.uberapp.DTO.RideDTO;
import tim24.projekat.uberapp.DTO.UserResponseDTO;
import tim24.projekat.uberapp.DTO.UserRef;
import tim24.projekat.uberapp.DTO.UserRequestDTO;
import tim24.projekat.uberapp.DTO.VehicleDTO;
import tim24.projekat.uberapp.DTO.VehicleRequestDTO;
import tim24.projekat.uberapp.DTO.WorkingHourDTO;
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
	public ResponseEntity<UserResponseDTO> CreateDriver(@RequestBody UserRequestDTO newDriver) {
		UserResponseDTO driver = new UserResponseDTO(1L,"Stefan","Bogdanovic","profilePicture.jpg","+3810641234567","mail@email.com","Bulevar Oslobodjenja 169");
		return new ResponseEntity<UserResponseDTO>(driver, HttpStatus.OK);
	}
	
	@GetMapping("driver")
	public ResponseEntity<DTOList<UserResponseDTO>> GetAllDrivers() {
		DTOList<UserResponseDTO> list = new DTOList<UserResponseDTO>();
		UserResponseDTO driver1 = new UserResponseDTO(1L,"Stefan","Bogdanovic","profilePicture.jpg","+3810641234567","mail@email.com","Bulevar Oslobodjenja 169");
		list.add(driver1);
		UserResponseDTO driver2 = new UserResponseDTO(1L,"Pero","Perovic","profilePicture.jpg","+3810641235667","mail@email.com","Bulevar Evrope 42");
		list.add(driver2);
		return new ResponseEntity<DTOList<UserResponseDTO>>(list, HttpStatus.OK);
	}
	@GetMapping("driver/{id}")
	public ResponseEntity<UserResponseDTO> GetDriverDetails(@PathVariable("id") Long id){
		UserResponseDTO driver = new UserResponseDTO(1L,"Stefan","Bogdanovic","profilePicture.jpg","+3810641234567","mail@email.com","Bulevar Oslobodjenja 169");
		return new ResponseEntity<UserResponseDTO>(driver, HttpStatus.OK);
	}
	@PutMapping("driver/{id}")
	public ResponseEntity<UserResponseDTO> UpdateDriverDetails(@RequestBody UserRequestDTO updatedDriver, @PathVariable("id") Long id){
		UserResponseDTO driver = new UserResponseDTO(1L,"Stefan","Bogdanovic","profilePicture.jpg","+3810641234567","mail@email.com","Bulevar Oslobodjenja 169");
		return new ResponseEntity<UserResponseDTO>(driver, HttpStatus.OK);
	}
	@GetMapping("driver/{id}/documents")
	public ResponseEntity<ArrayList<DriverDocumentDTO>> GetDriverDocuments(@PathVariable("id") Long id){
		ArrayList<DriverDocumentDTO> list = new ArrayList<DriverDocumentDTO>();
		DriverDocumentDTO dd = new DriverDocumentDTO(1L,"stefanova vozacka","slika.png",1L);
		list.add(dd);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	@PostMapping("driver/{id}/documents")
	public ResponseEntity<DriverDocumentDTO> CreateDriverDocuments(@RequestBody DriverDocumentRequestDTO ddrq, @PathVariable("id") Long id){
		DriverDocumentDTO dd = new DriverDocumentDTO(1L,"stefanova vozacka","slika.png",1L);
		return new ResponseEntity<>(dd, HttpStatus.OK);
	}
	@DeleteMapping("driver/{id}/documents")
	public ResponseEntity<Error> DeleteDriverDocuments(@PathVariable("id") Long id){
		
		return new ResponseEntity<>( HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("driver/{id}/vehicle")
	public ResponseEntity<VehicleDTO> GetDriverVehicle(@PathVariable("id") Long id){
		VehicleDTO v = new VehicleDTO(1L, 1L,"STANDARD","Ford Mondeo","NS-42069", new GeoCoordinateDTO("Kraj sveta",1,1), 4,false,false );
		
		return new ResponseEntity<VehicleDTO>(v, HttpStatus.OK);
	}
	@PutMapping("driver/{id}/vehicle")
	public ResponseEntity<VehicleDTO> UpdateDriverVehicle(@RequestBody VehicleRequestDTO newV, @PathVariable("id") Long id){
		VehicleDTO v = new VehicleDTO(1L, 1L,"STANDARD","Ford Mondeo","NS-42069", new GeoCoordinateDTO("Kraj sveta",1,1), 4,false,false );
		
		return new ResponseEntity<VehicleDTO>(v,HttpStatus.OK);
	}
	@PostMapping("driver/{id}/vehicle")
	public ResponseEntity<VehicleDTO> AddDriverVehicle(@RequestBody VehicleRequestDTO newV, @PathVariable("id") Long id){
		VehicleDTO v = new VehicleDTO(1L, 1L,"STANDARD","Ford Mondeo","NS-42069", new GeoCoordinateDTO("Kraj sveta",1,1), 4,false,false );
		
		return new ResponseEntity<VehicleDTO>(v, HttpStatus.OK);
	}
	
	@GetMapping("driver/{id}/working-hours")
	public ResponseEntity<DTOList<WorkingHourDTO>> GetDriverWorkinghours(@PathVariable("id") Long id){
		DTOList<WorkingHourDTO> list = new DTOList<WorkingHourDTO>();
		WorkingHourDTO wh = new WorkingHourDTO(1L,"18.11.1991T19:00","19.11.1991T00:00");
		list.add(wh);
		return new ResponseEntity<DTOList<WorkingHourDTO>>(list, HttpStatus.OK);
	}
	@PostMapping("driver/{id}/working-hours")
	public ResponseEntity<WorkingHourDTO> CreateDriverWorkinghours(@PathVariable("id") Long id){
		WorkingHourDTO wh = new WorkingHourDTO(1L,"18.11.1991T19:00","19.11.1991T00:00");
		return new ResponseEntity<WorkingHourDTO>(wh, HttpStatus.OK);
	}
	
	@GetMapping("driver/{id}/ride")
	public ResponseEntity<DTOList<RideDTO>> GetDriverRides(@PathVariable("id") Long id){
		DTOList<RideDTO> rides = new DTOList<RideDTO>();
		RideDTO r = new RideDTO(1L,"18.11.1991T19:30","18.11.1991T20:00",420,new UserRef(1L,"email@mail.com","VOZAC"),30,"STANDARD",false,false);
		r.addPassenger(new UserRef(1L, "mailic@mail.com","PUTNIK"));
		rides.add(r);
		return new ResponseEntity<DTOList<RideDTO>>(rides,HttpStatus.OK);
	}
	
	@GetMapping("driver/{driver-id}/working-hour/{working-hour-id}")
	public ResponseEntity<WorkingHourDTO> GetDriverWorkinghoursDetails(@PathVariable("driver-id") Long driverId, @PathVariable("working-hour-id") Long workinghoursId){
		WorkingHourDTO wh = new WorkingHourDTO(1L,"18.11.1991T19:00","19.11.1991T00:00");
		return new ResponseEntity<WorkingHourDTO>(wh, HttpStatus.OK);
	}
	
	@PutMapping("driver/{driver-id}/working-hour/{working-hour-id}")
	public ResponseEntity<WorkingHourDTO> ChangeDriverWorkinghoursDetails(@PathVariable("driver-id") Long driverId, @PathVariable("working-hour-id") Long workinghoursId){
		WorkingHourDTO wh = new WorkingHourDTO(1L,"18.11.1991T19:00","19.11.1991T00:00");
		return new ResponseEntity<WorkingHourDTO>(wh, HttpStatus.OK);
	}
	
}
