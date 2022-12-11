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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tim24.projekat.uberapp.DTO.DriverDocumentDTO;
import tim24.projekat.uberapp.DTO.DriverDocumentRequestDTO;
import tim24.projekat.uberapp.DTO.DTOList;
import tim24.projekat.uberapp.DTO.GeoCoordinateDTO;
import tim24.projekat.uberapp.DTO.RejectionDTO;
import tim24.projekat.uberapp.DTO.RideDTO;
import tim24.projekat.uberapp.DTO.RouteDTO;
import tim24.projekat.uberapp.DTO.UserResponseDTO;
import tim24.projekat.uberapp.DTO.UserRef;
import tim24.projekat.uberapp.DTO.UserRequestDTO;
import tim24.projekat.uberapp.DTO.VehicleDTO;
import tim24.projekat.uberapp.DTO.VehicleRequestDTO;
import tim24.projekat.uberapp.DTO.WorkingHourDTO;
import tim24.projekat.uberapp.service.DriverService;


@RestController
@RequestMapping("api/driver")
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
		UserResponseDTO driver = new UserResponseDTO(1L,"Stefan","Bogdanovic","profilePicture.jpg","+3810641234567","mail@email.com","Bulevar Oslobodjenja 169");
		return new ResponseEntity<UserResponseDTO>(driver, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<DTOList<UserResponseDTO>> GetAllDrivers(
			@RequestParam("page") int page, 
			@RequestParam("size") int size) {
		DTOList<UserResponseDTO> list = new DTOList<UserResponseDTO>();
		UserResponseDTO driver1 = new UserResponseDTO(1L,"Stefan","Bogdanovic","profilePicture.jpg","+3810641234567","mail@email.com","Bulevar Oslobodjenja 169");
		list.add(driver1);
		UserResponseDTO driver2 = new UserResponseDTO(1L,"Pero","Perovic","profilePicture.jpg","+3810641235667","mail@email.com","Bulevar Evrope 42");
		list.add(driver2);
		return new ResponseEntity<DTOList<UserResponseDTO>>(list, HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDTO> GetDriverDetails(
			@PathVariable("id") Long id){
		UserResponseDTO driver = new UserResponseDTO(1L,"Stefan","Bogdanovic","profilePicture.jpg","+3810641234567","mail@email.com","Bulevar Oslobodjenja 169");
		return new ResponseEntity<UserResponseDTO>(driver, HttpStatus.OK);
	}
	@PutMapping("/{id}")
	public ResponseEntity<UserResponseDTO> UpdateDriverDetails(
			@RequestBody UserRequestDTO updatedDriver, 
			@PathVariable("id") Long id){
		UserResponseDTO driver = new UserResponseDTO(1L,"Stefan","Bogdanovic","profilePicture.jpg","+3810641234567","mail@email.com","Bulevar Oslobodjenja 169");
		return new ResponseEntity<UserResponseDTO>(driver, HttpStatus.OK);
	}
	@GetMapping("/{id}/documents")
	public ResponseEntity<ArrayList<DriverDocumentDTO>> GetDriverDocuments(
			@PathVariable("id") Long id){
		ArrayList<DriverDocumentDTO> list = new ArrayList<DriverDocumentDTO>();
		DriverDocumentDTO dd = new DriverDocumentDTO(1L,"stefanova vozacka","slika.png",1L);
		list.add(dd);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	@PostMapping("/{id}/documents")
	public ResponseEntity<DriverDocumentDTO> CreateDriverDocuments(
			@RequestBody DriverDocumentRequestDTO ddrq, 
			@PathVariable("id") Long id){
		DriverDocumentDTO dd = new DriverDocumentDTO(1L,"stefanova vozacka","slika.png",1L);
		return new ResponseEntity<>(dd, HttpStatus.OK);
	}
	@DeleteMapping("/document/{id}")
	public ResponseEntity<Error> DeleteDriverDocuments(
			@PathVariable("id") Long id){
		
		return new ResponseEntity<>( HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/{id}/vehicle")
	public ResponseEntity<VehicleDTO> GetDriverVehicle(
			@PathVariable("id") Long id){
		VehicleDTO v = new VehicleDTO(1L, 1L,"STANDARD","Ford Mondeo","NS-42069", new GeoCoordinateDTO("Kraj sveta",1,1), 4,false,false );
		
		return new ResponseEntity<VehicleDTO>(v, HttpStatus.OK);
	}
	@PutMapping("/{id}/vehicle")
	public ResponseEntity<VehicleDTO> UpdateDriverVehicle(
			@RequestBody VehicleRequestDTO newV, 
			@PathVariable("id") Long id){
		VehicleDTO v = new VehicleDTO(1L, 1L,"STANDARD","Ford Mondeo","NS-42069", new GeoCoordinateDTO("Kraj sveta",1,1), 4,false,false );
		
		return new ResponseEntity<VehicleDTO>(v,HttpStatus.OK);
	}
	@PostMapping("/{id}/vehicle")
	public ResponseEntity<VehicleDTO> AddDriverVehicle(
			@RequestBody VehicleRequestDTO newV, 
			@PathVariable("id") Long id){
		VehicleDTO v = new VehicleDTO(1L, 1L,"STANDARD","Ford Mondeo","NS-42069", new GeoCoordinateDTO("Kraj sveta",1,1), 4,false,false );
		
		return new ResponseEntity<VehicleDTO>(v, HttpStatus.OK);
	}
	
	@GetMapping("/{id}/working-hour")
	public ResponseEntity<DTOList<WorkingHourDTO>> GetDriverWorkinghours(
			@PathVariable("id") Long id, 
			@RequestParam("page") int page, 
			@RequestParam("size") int size,
			@RequestParam("from") String fromDate,
			@RequestParam("to") String toDated)
	{
		DTOList<WorkingHourDTO> list = new DTOList<WorkingHourDTO>();
		WorkingHourDTO wh = new WorkingHourDTO(1L,"18.11.1991T19:00","19.11.1991T00:00");
		list.add(wh);
		return new ResponseEntity<DTOList<WorkingHourDTO>>(list, HttpStatus.OK);
	}
	@PostMapping("/{id}/working-hour")
	public ResponseEntity<WorkingHourDTO> CreateDriverWorkinghours(
			@PathVariable("id") Long id){
		WorkingHourDTO wh = new WorkingHourDTO(1L,"18.11.1991T19:00","19.11.1991T00:00");
		return new ResponseEntity<WorkingHourDTO>(wh, HttpStatus.OK);
	}
	
	@GetMapping("/{id}/ride")
	public ResponseEntity<DTOList<RideDTO>> GetDriverRides(
			@PathVariable("id") Long id,
			@RequestParam("page") int page,
			@RequestParam("size") int size,
			@RequestParam("sort") String sort,
			@RequestParam("from") String fromDate,
			@RequestParam("to") String toDate)
			{
		List<RideDTO> rides = new ArrayList<RideDTO>();
		RejectionDTO rej = new RejectionDTO ("neki razlog","datummm");
		GeoCoordinateDTO gcd1 = new GeoCoordinateDTO ("adresa1",123,321);
		GeoCoordinateDTO gcd2 = new GeoCoordinateDTO ("adresa2",424,572);
		
		ArrayList<RouteDTO> routes = new ArrayList<RouteDTO>();
		routes.add(new RouteDTO(gcd1,gcd2));
		
		ArrayList<UserRef> passengers = new ArrayList<UserRef>();
		passengers.add(new UserRef(1L, "mailic@mail.com"));
		
		RideDTO r = new RideDTO(300L, "18:44", "19:30", 123,new UserRef(2L, "mailicXD@mail.com"),passengers,40,"tip",false,true,rej,routes, "PENDING");
		rides.add(r);
		DTOList<RideDTO> dtoList = new DTOList<RideDTO>(rides.size(), rides);
		return new ResponseEntity<DTOList<RideDTO>>(dtoList,HttpStatus.OK);
	}
	
	@GetMapping("/working-hour/{working-hour-id}")
	public ResponseEntity<WorkingHourDTO> GetDriverWorkinghoursDetails(
			@PathVariable("working-hour-id") Long workinghoursId){
		WorkingHourDTO wh = new WorkingHourDTO(1L,"18.11.1991T19:00","19.11.1991T00:00");
		return new ResponseEntity<WorkingHourDTO>(wh, HttpStatus.OK);
	}
	
	@PutMapping("/working-hour/{working-hour-id}")
	public ResponseEntity<WorkingHourDTO> ChangeDriverWorkinghoursDetails(
			@PathVariable("working-hour-id") Long workinghoursId){
		WorkingHourDTO wh = new WorkingHourDTO(1L,"18.11.1991T19:00","19.11.1991T00:00");
		return new ResponseEntity<WorkingHourDTO>(wh, HttpStatus.OK);
	}
	
}
