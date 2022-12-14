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
	
	@Autowired
	private DriverService driverService;
	
	@PostMapping
	public ResponseEntity<UserResponseDTO> CreateDriver(
			@RequestBody UserRequestDTO newDriver) {
		
		UserResponseDTO driver = driverService.createDriver(newDriver);
		return new ResponseEntity<UserResponseDTO>(driver, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<DTOList<UserResponseDTO>> GetAllDrivers(
			@RequestParam("page") int page, 
			@RequestParam("size") int size) {
		DTOList<UserResponseDTO> list = driverService.GetAllDrivers(page, size);
		return new ResponseEntity<DTOList<UserResponseDTO>>(list, HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDTO> GetDriverDetails(
			@PathVariable("id") Long id){
		UserResponseDTO driver = driverService.getDriverById(id);
		return new ResponseEntity<UserResponseDTO>(driver, HttpStatus.OK);
	}
	@PutMapping("/{id}")
	public ResponseEntity<UserResponseDTO> UpdateDriverDetails(
			@RequestBody UserRequestDTO updatedDriver, 
			@PathVariable("id") Long id){
		UserResponseDTO driver = driverService.updateDriver(id, updatedDriver);
		return new ResponseEntity<UserResponseDTO>(driver, HttpStatus.OK);
	}
	@GetMapping("/{id}/documents")
	public ResponseEntity<ArrayList<DriverDocumentDTO>> GetDriverDocuments(
			@PathVariable("id") Long id){
		ArrayList<DriverDocumentDTO> list = driverService.getDriverDocuments(id);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	@PostMapping("/{id}/documents")
	public ResponseEntity<DriverDocumentDTO> CreateDriverDocuments(
			@RequestBody DriverDocumentRequestDTO ddrq, 
			@PathVariable("id") Long id){
		
		DriverDocumentDTO dd = driverService.createDriverDocuments(id, ddrq);
		return new ResponseEntity<>(dd, HttpStatus.OK);
	}
	@DeleteMapping("/document/{id}")
	public ResponseEntity<Error> DeleteDriverDocuments(
			@PathVariable("id") Long id){
		driverService.DeleteDriver(id);
		
		return new ResponseEntity<>( HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/{id}/vehicle")
	public ResponseEntity<VehicleDTO> GetDriverVehicle(
			@PathVariable("id") Long id){
		VehicleDTO v = driverService.getDriverVehicle(id);
		
		return new ResponseEntity<VehicleDTO>(v, HttpStatus.OK);
	}
	@PutMapping("/{id}/vehicle")
	public ResponseEntity<VehicleDTO> UpdateDriverVehicle(
			@RequestBody VehicleRequestDTO newV, 
			@PathVariable("id") Long id){
		VehicleDTO v = driverService.updateVehicle(id, newV);
		
		return new ResponseEntity<VehicleDTO>(v,HttpStatus.OK);
	}
	@PostMapping("/{id}/vehicle")
	public ResponseEntity<VehicleDTO> AddDriverVehicle(
			@RequestBody VehicleRequestDTO newV, 
			@PathVariable("id") Long id){
		VehicleDTO v = driverService.addDriverVehicle(id, newV);
		
		return new ResponseEntity<VehicleDTO>(v, HttpStatus.OK);
	}
	
	@GetMapping("/{id}/working-hour")
	public ResponseEntity<DTOList<WorkingHourDTO>> GetDriverWorkinghours(
			@PathVariable("id") Long id, 
			@RequestParam("page") int page, 
			@RequestParam("size") int size,
			@RequestParam("from") String fromDate,
			@RequestParam("to") String toDate)
	{
		DTOList<WorkingHourDTO> list = driverService.getDriverWorkinghour(id, page, size, fromDate, toDate);
		
		return new ResponseEntity<DTOList<WorkingHourDTO>>(list, HttpStatus.OK);
	}
	@PostMapping("/{id}/working-hour")
	public ResponseEntity<WorkingHourDTO> CreateDriverWorkinghours(
			@PathVariable("id") Long id){
		WorkingHourDTO wh = driverService.createDriverWorkinghour(id);
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
		
		DTOList<RideDTO> dtoList = driverService.getDriverRides(id,page,size,sort,fromDate,toDate);
		return new ResponseEntity<DTOList<RideDTO>>(dtoList,HttpStatus.OK);
	}
	
	@GetMapping("/working-hour/{working-hour-id}")
	public ResponseEntity<WorkingHourDTO> GetDriverWorkinghoursDetails(
			@PathVariable("working-hour-id") Long workinghoursId){
		WorkingHourDTO wh = driverService.getDriverWorkinghourDetails(workinghoursId);
		return new ResponseEntity<WorkingHourDTO>(wh, HttpStatus.OK);
	}
	
	@PutMapping("/working-hour/{working-hour-id}")
	public ResponseEntity<WorkingHourDTO> ChangeDriverWorkinghoursDetails(
			@PathVariable("working-hour-id") Long workinghoursId){
		WorkingHourDTO wh = driverService.changeDriverWorkinghourDetails(workinghoursId);
		return new ResponseEntity<WorkingHourDTO>(wh, HttpStatus.OK);
	}
	
}
