package tim24.projekat.uberapp.controller;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
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
import tim24.projekat.uberapp.DTO.DriverReportResponseDTO;
import tim24.projekat.uberapp.DTO.DurationDTO;
import tim24.projekat.uberapp.DTO.DTOList;
import tim24.projekat.uberapp.DTO.DriverChangeDTO;
import tim24.projekat.uberapp.DTO.RideDTO;
import tim24.projekat.uberapp.DTO.StatisticsResponseDTO;
import tim24.projekat.uberapp.DTO.SuccessDTO;
import tim24.projekat.uberapp.DTO.UserRegistrationDTO;
import tim24.projekat.uberapp.DTO.ErrorDTO;
import tim24.projekat.uberapp.DTO.UserResponseDTO;
import tim24.projekat.uberapp.DTO.UserUpdateRequestDTO;
import tim24.projekat.uberapp.DTO.VehicleDTO;
import tim24.projekat.uberapp.DTO.VehicleRequestDTO;
import tim24.projekat.uberapp.DTO.WorkingHourDTO;
import tim24.projekat.uberapp.DTO.WorkingHourPostDTO;
import tim24.projekat.uberapp.DTO.WorkingHourPutDTO;
import tim24.projekat.uberapp.DTO.statisticsRequestDTO;
import tim24.projekat.uberapp.exception.ConditionNotMetException;
import tim24.projekat.uberapp.exception.InvalidArgumentException;
import tim24.projekat.uberapp.exception.ObjectAlreadyPresentException;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.service.DriverService;
import tim24.projekat.uberapp.service.StatisticsService;


@RestController
@RequestMapping("api/statistics")
public class StatisticsController {
	
	@Autowired
	private StatisticsService statisticsService;
	
	
	
	@PostMapping("passenger/{id}/rides")
	public ResponseEntity<?> getPassengerRidesStats(
			@PathVariable("id") Long id,
			@RequestBody statisticsRequestDTO srd){
		try {
			StatisticsResponseDTO stats = statisticsService.getRidesStatsForPassenger(id, srd);
			return new ResponseEntity<StatisticsResponseDTO>(stats, HttpStatus.OK);
		}
		catch(ObjectNotFoundException e){
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
		}
		catch(InvalidArgumentException e){
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PostMapping("driver/{id}/rides")
	public ResponseEntity<?> getDriverRidesStats(
			@PathVariable("id") Long id,
			@RequestBody statisticsRequestDTO srd){
		try {
			StatisticsResponseDTO stats = statisticsService.getRidesStatsForDriver(id, srd);
			return new ResponseEntity<StatisticsResponseDTO>(stats, HttpStatus.OK);
		}
		catch(ObjectNotFoundException e){
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
		}
		catch(InvalidArgumentException e){
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("admin/rides")
	public ResponseEntity<?> getAdminRidesStats(
			@RequestBody statisticsRequestDTO srd){
		try {
			StatisticsResponseDTO stats = statisticsService.getRidesStatsForAdmin(srd);
			return new ResponseEntity<StatisticsResponseDTO>(stats, HttpStatus.OK);
		}
		catch(ObjectNotFoundException e){
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
		}
		catch(InvalidArgumentException e){
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PostMapping("passenger/{id}/kilometers")
	public ResponseEntity<?> getPassengerKilometersStats(
			@PathVariable("id") Long id,
			@RequestBody statisticsRequestDTO srd){
		try {
			StatisticsResponseDTO stats = statisticsService.getKilometersStatsForPassenger(id, srd);
			return new ResponseEntity<StatisticsResponseDTO>(stats, HttpStatus.OK);
		}
		catch(ObjectNotFoundException e){
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
		}
		catch(InvalidArgumentException e){
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PostMapping("driver/{id}/kilometers")
	public ResponseEntity<?> getDriverKilometersStats(
			@PathVariable("id") Long id,
			@RequestBody statisticsRequestDTO srd){
		try {
			StatisticsResponseDTO stats = statisticsService.getKilometersStatsForDriver(id, srd);
			return new ResponseEntity<StatisticsResponseDTO>(stats, HttpStatus.OK);
		}
		catch(ObjectNotFoundException e){
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
		}
		catch(InvalidArgumentException e){
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("admin/kilometers")
	public ResponseEntity<?> getAdminKilometersStats(
			@RequestBody statisticsRequestDTO srd){
		try {
			StatisticsResponseDTO stats = statisticsService.getKilometersStatsForAdmin(srd);
			return new ResponseEntity<StatisticsResponseDTO>(stats, HttpStatus.OK);
		}
		catch(ObjectNotFoundException e){
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
		}
		catch(InvalidArgumentException e){
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PostMapping("passenger/{id}/expenses")
	public ResponseEntity<?> getPassengerExpensesStats(
			@PathVariable("id") Long id,
			@RequestBody statisticsRequestDTO srd){
		try {
			StatisticsResponseDTO stats = statisticsService.getExpensesStatsForPassenger(id, srd);
			return new ResponseEntity<StatisticsResponseDTO>(stats, HttpStatus.OK);
		}
		catch(ObjectNotFoundException e){
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
		}
		catch(InvalidArgumentException e){
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PostMapping("driver/{id}/expenses")
	public ResponseEntity<?> getDriverExpensesStats(
			@PathVariable("id") Long id,
			@RequestBody statisticsRequestDTO srd){
		try {
			StatisticsResponseDTO stats = statisticsService.getExpensesStatsForDriver(id, srd);
			return new ResponseEntity<StatisticsResponseDTO>(stats, HttpStatus.OK);
		}
		catch(ObjectNotFoundException e){
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
		}
		catch(InvalidArgumentException e){
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("admin/expenses")
	public ResponseEntity<?> getAdminExpensesStats(
			@RequestBody statisticsRequestDTO srd){
		try {
			StatisticsResponseDTO stats = statisticsService.getExpensesStatsForAdmin(srd);
			return new ResponseEntity<StatisticsResponseDTO>(stats, HttpStatus.OK);
		}
		catch(ObjectNotFoundException e){
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
		}
		catch(InvalidArgumentException e){
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
		}
		
	}
	
}
