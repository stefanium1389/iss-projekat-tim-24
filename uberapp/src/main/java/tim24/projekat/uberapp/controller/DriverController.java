package tim24.projekat.uberapp.controller;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import tim24.projekat.uberapp.DTO.DurationDTO;
import tim24.projekat.uberapp.DTO.DTOList;
import tim24.projekat.uberapp.DTO.RideDTO;
import tim24.projekat.uberapp.DTO.UserRegistrationDTO;
import tim24.projekat.uberapp.DTO.ErrorDTO;
import tim24.projekat.uberapp.DTO.UserResponseDTO;
import tim24.projekat.uberapp.DTO.UserRequestDTO;
import tim24.projekat.uberapp.DTO.VehicleDTO;
import tim24.projekat.uberapp.DTO.VehicleRequestDTO;
import tim24.projekat.uberapp.DTO.WorkingHourDTO;
import tim24.projekat.uberapp.DTO.WorkingHourPostDTO;
import tim24.projekat.uberapp.DTO.WorkingHourPutDTO;
import tim24.projekat.uberapp.exception.ConditionNotMetException;
import tim24.projekat.uberapp.exception.ObjectAlreadyPresentException;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.model.User;
import tim24.projekat.uberapp.service.DriverService;


@RestController
@RequestMapping("api/driver")
public class DriverController {
	
	@Autowired
	private DriverService driverService;
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> CreateDriver(
			@RequestBody UserRegistrationDTO newDriver) {
		try {
			UserResponseDTO driver = driverService.createDriver(newDriver);
			return new ResponseEntity<UserResponseDTO>(driver, HttpStatus.OK);
		}
		catch(ObjectAlreadyPresentException e) {
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping
	public ResponseEntity<?> GetAllDrivers(
			@RequestParam("page") int page, 
			@RequestParam("size") int size) {
		DTOList<UserResponseDTO> list = driverService.GetAllDrivers(page, size);
		return new ResponseEntity<DTOList<UserResponseDTO>>(list, HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> GetDriverDetails(
			@PathVariable("id") Long id){
		try {
			UserResponseDTO driver = driverService.getDriverById(id);
			return new ResponseEntity<UserResponseDTO>(driver, HttpStatus.OK);
		}
		catch(ObjectNotFoundException e){
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
		}
		
	}
	@PutMapping("/{id}")
	public ResponseEntity<?> UpdateDriverDetails(
			@RequestBody UserRequestDTO updatedDriver, 
			@PathVariable("id") Long id)
	{
		try {
			UserResponseDTO driver = driverService.updateDriver(id, updatedDriver);
			return new ResponseEntity<UserResponseDTO>(driver, HttpStatus.OK);
		}
		catch(ObjectNotFoundException e) {
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
		}
		catch(Exception e) {
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error, HttpStatus.BAD_REQUEST);
		}
		
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
	public ResponseEntity<?> GetDriverWorkinghours(
			@PathVariable("id") Long id, 
			@RequestParam("page") int page, 
			@RequestParam("size") int size,
			@RequestParam("from") String fromDate,
			@RequestParam("to") String toDate)
	{
		DTOList<WorkingHourDTO> list = driverService.getDriverWorkinghour(id, page, size, fromDate, toDate);
		
		return new ResponseEntity<DTOList<WorkingHourDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/{id}/last-active-working-hour")
	public ResponseEntity<DTOList<WorkingHourDTO>> GetLastDriverWorkinghour(
			@PathVariable("id") Long id)
	{
		DTOList<WorkingHourDTO> list = driverService.getLastActiveDriverWorkinghour(id);
		
		return new ResponseEntity<DTOList<WorkingHourDTO>>(list, HttpStatus.OK);
	}
	
	@PostMapping("/{id}/working-hour")
	public ResponseEntity<?> CreateDriverWorkinghours(
			@PathVariable("id") Long id ,
			@RequestBody WorkingHourPostDTO whDTO){
		try {
			WorkingHourDTO wh = driverService.createDriverWorkinghour(id, whDTO);
			return new ResponseEntity<WorkingHourDTO>(wh, HttpStatus.OK);
		}
		catch(ObjectNotFoundException e) {
    		Error error = new Error(e.getMessage());
    		return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
    	}
		catch(ObjectAlreadyPresentException e) {
    		Error error = new Error(e.getMessage());
    		return new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);
    	}
		catch(ConditionNotMetException e) {
    		Error error = new Error(e.getMessage());
    		return new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);
    	}
		
	}
	
	@GetMapping("/{id}/working-hours-of-last-24h")
	public ResponseEntity<?> CreateDriverWorkinghours(
			@PathVariable("id") Long id){
		try {
			String duration = driverService.getDriverActiveHoursInLast24h(id);
			DurationDTO durationDTO = new DurationDTO(duration);
			return new ResponseEntity<DurationDTO>(durationDTO, HttpStatus.OK);
		}
		catch(ObjectNotFoundException e) {
    		Error error = new Error(e.getMessage());
    		return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
    	}
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
		WorkingHourDTO wh = driverService.getDriverWorkingHourDetails(workinghoursId);
		return new ResponseEntity<WorkingHourDTO>(wh, HttpStatus.OK);
	}
	
	@PutMapping("/working-hour/{working-hour-id}")
	public ResponseEntity<?> ChangeDriverWorkinghoursDetails(
			@PathVariable("working-hour-id") Long workinghoursId,
			@RequestBody WorkingHourPutDTO putDTO ){
		try {
			WorkingHourDTO wh = driverService.changeDriverWorkingHourDetails(workinghoursId,putDTO);
			return new ResponseEntity<WorkingHourDTO>(wh, HttpStatus.OK);
		}
		catch(ObjectNotFoundException e) {
    		Error error = new Error(e.getMessage());
    		return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
    	}
	}
	
}
