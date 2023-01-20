package tim24.projekat.uberapp.service;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import tim24.projekat.uberapp.DTO.DTOList;
import tim24.projekat.uberapp.DTO.DriverChangeDTO;
import tim24.projekat.uberapp.DTO.DriverDocumentDTO;
import tim24.projekat.uberapp.DTO.DriverDocumentRequestDTO;
import tim24.projekat.uberapp.DTO.DriverReportResponseDTO;
import tim24.projekat.uberapp.DTO.RideDTO;
import tim24.projekat.uberapp.DTO.StatisticsResponseDTO;
import tim24.projekat.uberapp.DTO.UserRegistrationDTO;
import tim24.projekat.uberapp.DTO.UserResponseDTO;
import tim24.projekat.uberapp.DTO.UserUpdateRequestDTO;
import tim24.projekat.uberapp.DTO.VehicleDTO;
import tim24.projekat.uberapp.DTO.VehicleRequestDTO;
import tim24.projekat.uberapp.DTO.WorkingHourDTO;
import tim24.projekat.uberapp.DTO.WorkingHourPostDTO;
import tim24.projekat.uberapp.DTO.WorkingHourPutDTO;
import tim24.projekat.uberapp.DTO.statisticsRequestDTO;
import tim24.projekat.uberapp.controller.DriverReportDTO;
import tim24.projekat.uberapp.exception.ConditionNotMetException;
import tim24.projekat.uberapp.exception.InvalidArgumentException;
import tim24.projekat.uberapp.exception.ObjectAlreadyPresentException;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.model.DriverDocument;
import tim24.projekat.uberapp.model.DriverReport;
import tim24.projekat.uberapp.model.DriverUpdateDetails;
import tim24.projekat.uberapp.model.Ride;
import tim24.projekat.uberapp.model.Role;
import tim24.projekat.uberapp.model.UpdateState;
import tim24.projekat.uberapp.model.User;
import tim24.projekat.uberapp.model.Vehicle;
import tim24.projekat.uberapp.model.VehicleType;
import tim24.projekat.uberapp.model.WorkingHour;
import tim24.projekat.uberapp.repo.DriverDocumentRepository;
import tim24.projekat.uberapp.repo.DriverReportRepo;
import tim24.projekat.uberapp.repo.DriveruUpdateDetailsRepo;
import tim24.projekat.uberapp.repo.RideRepository;
import tim24.projekat.uberapp.repo.UserRepository;
import tim24.projekat.uberapp.repo.VehicleRepository;
import tim24.projekat.uberapp.repo.VehicleTypeRepository;
import tim24.projekat.uberapp.repo.WorkingHourRepo;

@Service
public class StatisticsService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private WorkingHourRepo workingHourRepo;
	
	@Autowired
	private DriverDocumentRepository driverDocumentRepo;
	
	@Autowired
	private VehicleRepository vehicleRepo;
	
	@Autowired
	private DriverReportRepo driverReportRepo;
	
	@Autowired
	private RideRepository rideRepo;
	
	@Autowired
	private DriveruUpdateDetailsRepo dudRepo;
	
	@Autowired
	private VehicleTypeRepository viehicleTypeRepo;

	public StatisticsResponseDTO getRidesStatsForPassenger(Long id, statisticsRequestDTO requestDTO) {
		
		Optional<User> userOpt = userRepo.findByIdAndRole(id, Role.USER);
		if (userOpt.isEmpty()) 
		{
			throw new ObjectNotFoundException("User does not exist!");
		}
		
		User user = userOpt.get();
		Date startDate = parseDate(requestDTO.getStartDate());
		Date endDate = parseDate(requestDTO.getEndDate());
		Duration duration = Duration.between(startDate.toInstant(), endDate.toInstant());
		StatisticsResponseDTO dto = null;
		if (duration.toDays()<31) 
		{
			dto = generateSmallRidesPassenger(id, startDate, endDate);
		}
		else if (duration.toDays()>=31 && duration.toDays()<=365) 
		{
			dto = generateMediumRidesPassenger(id, startDate, endDate);
		}
		else if (duration.toDays()>365) 
		{
			dto = generateLargeRidesPassenger(id, startDate, endDate);
		}
		else 
		{
			throw new InvalidArgumentException ("Error while parsing dates in statistics");
		}
		
		return dto;
	}
	
	public StatisticsResponseDTO getRidesStatsForDriver(Long id, statisticsRequestDTO requestDTO) {
		
		Optional<User> userOpt = userRepo.findByIdAndRole(id, Role.DRIVER);
		if (userOpt.isEmpty()) 
		{
			throw new ObjectNotFoundException("Driver does not exist!");
		}
		
		User user = userOpt.get();
		Date startDate = parseDate(requestDTO.getStartDate());
		Date endDate = parseDate(requestDTO.getEndDate());
		Duration duration = Duration.between(startDate.toInstant(), endDate.toInstant());
		StatisticsResponseDTO dto = null;
		if (duration.toDays()<31) 
		{
			dto = generateSmallRidesDriver(id, startDate, endDate);
		}
		else if (duration.toDays()>=31 && duration.toDays()<=365) 
		{
			dto = generateMediumRidesDriver(id, startDate, endDate);
		}
		else if (duration.toDays()>365) 
		{
			dto = generateLargeRidesDriver(id, startDate, endDate);
		}
		else 
		{
			throw new InvalidArgumentException ("Error while parsing dates in statistics");
		}
		
		return dto;
	}
	
	public StatisticsResponseDTO getRidesStatsForAdmin(statisticsRequestDTO requestDTO) {
		
		
		Date startDate = parseDate(requestDTO.getStartDate());
		Date endDate = parseDate(requestDTO.getEndDate());
		Duration duration = Duration.between(startDate.toInstant(), endDate.toInstant());
		StatisticsResponseDTO dto = null;
		if (duration.toDays()<31) 
		{
			dto = generateSmallRidesAdmin(startDate, endDate);
		}
		else if (duration.toDays()>=31 && duration.toDays()<=365) 
		{
			dto = generateMediumRidesAdmin(startDate, endDate);
		}
		else if (duration.toDays()>365) 
		{
			dto = generateLargeRidesAdmin(startDate, endDate);
		}
		else 
		{
			throw new InvalidArgumentException ("Error while parsing dates in statistics");
		}
		
		return dto;
	}
	
	public StatisticsResponseDTO getKilometersStatsForPassenger(Long id, statisticsRequestDTO requestDTO) {
		
		Optional<User> userOpt = userRepo.findByIdAndRole(id, Role.USER);
		if (userOpt.isEmpty()) 
		{
			throw new ObjectNotFoundException("User does not exist!");
		}
		
		User user = userOpt.get();
		Date startDate = parseDate(requestDTO.getStartDate());
		Date endDate = parseDate(requestDTO.getEndDate());
		Duration duration = Duration.between(startDate.toInstant(), endDate.toInstant());
		StatisticsResponseDTO dto = null;
		if (duration.toDays()<31) 
		{
			dto = generateSmallKilometersPassenger(id, startDate, endDate);
		}
		else if (duration.toDays()>=31 && duration.toDays()<=365) 
		{
			dto = generateMediumKilometersPassenger(id, startDate, endDate);
		}
		else if (duration.toDays()>365) 
		{
			dto = generateLargeKilometersPassenger(id, startDate, endDate);
		}
		else 
		{
			throw new InvalidArgumentException ("Error while parsing dates in statistics");
		}
		
		return dto;
	}
	


	public StatisticsResponseDTO getKilometersStatsForDriver(Long id, statisticsRequestDTO requestDTO) {
		
		Optional<User> userOpt = userRepo.findByIdAndRole(id, Role.DRIVER);
		if (userOpt.isEmpty()) 
		{
			throw new ObjectNotFoundException("Driver does not exist!");
		}
		
		User user = userOpt.get();
		Date startDate = parseDate(requestDTO.getStartDate());
		Date endDate = parseDate(requestDTO.getEndDate());
		Duration duration = Duration.between(startDate.toInstant(), endDate.toInstant());
		StatisticsResponseDTO dto = null;
		if (duration.toDays()<31) 
		{
			dto = generateSmallKilometersDriver(id, startDate, endDate);
		}
		else if (duration.toDays()>=31 && duration.toDays()<=365) 
		{
			dto = generateMediumKilometersDriver(id, startDate, endDate);
		}
		else if (duration.toDays()>365) 
		{
			dto = generateLargeKilometersDriver(id, startDate, endDate);
		}
		else 
		{
			throw new InvalidArgumentException ("Error while parsing dates in statistics");
		}
		
		return dto;
	}
	
	
	public StatisticsResponseDTO getKilometersStatsForAdmin(statisticsRequestDTO requestDTO) {
		
		
		Date startDate = parseDate(requestDTO.getStartDate());
		Date endDate = parseDate(requestDTO.getEndDate());
		Duration duration = Duration.between(startDate.toInstant(), endDate.toInstant());
		StatisticsResponseDTO dto = null;
		if (duration.toDays()<31) 
		{
			dto = generateSmallKilometersAdmin(startDate, endDate);
		}
		else if (duration.toDays()>=31 && duration.toDays()<=365) 
		{
			dto = generateMediumKilometersAdmin(startDate, endDate);
		}
		else if (duration.toDays()>365) 
		{
			dto = generateLargeKilometersAdmin(startDate, endDate);
		}
		else 
		{
			throw new InvalidArgumentException ("Error while parsing dates in statistics");
		}
		
		return dto;
	}
	
public StatisticsResponseDTO getExpensesStatsForPassenger(Long id, statisticsRequestDTO requestDTO) {
		
		Optional<User> userOpt = userRepo.findByIdAndRole(id, Role.USER);
		if (userOpt.isEmpty()) 
		{
			throw new ObjectNotFoundException("User does not exist!");
		}
		
		User user = userOpt.get();
		Date startDate = parseDate(requestDTO.getStartDate());
		Date endDate = parseDate(requestDTO.getEndDate());
		Duration duration = Duration.between(startDate.toInstant(), endDate.toInstant());
		StatisticsResponseDTO dto = null;
		if (duration.toDays()<31) 
		{
			dto = generateSmallExpensesPassenger(id, startDate, endDate);
		}
		else if (duration.toDays()>=31 && duration.toDays()<=365) 
		{
			dto = generateMediumExpensesPassenger(id, startDate, endDate);
		}
		else if (duration.toDays()>365) 
		{
			dto = generateLargeExpensesPassenger(id, startDate, endDate);
		}
		else 
		{
			throw new InvalidArgumentException ("Error while parsing dates in statistics");
		}
		
		return dto;
	}
	

	public StatisticsResponseDTO getExpensesStatsForDriver(Long id, statisticsRequestDTO requestDTO) {
		
		Optional<User> userOpt = userRepo.findByIdAndRole(id, Role.DRIVER);
		if (userOpt.isEmpty()) 
		{
			throw new ObjectNotFoundException("Driver does not exist!");
		}
		
		User user = userOpt.get();
		Date startDate = parseDate(requestDTO.getStartDate());
		Date endDate = parseDate(requestDTO.getEndDate());
		Duration duration = Duration.between(startDate.toInstant(), endDate.toInstant());
		StatisticsResponseDTO dto = null;
		if (duration.toDays()<31) 
		{
			dto = generateSmallExpensesDriver(id, startDate, endDate);
		}
		else if (duration.toDays()>=31 && duration.toDays()<=365) 
		{
			dto = generateMediumExpensesDriver(id, startDate, endDate);
		}
		else if (duration.toDays()>365) 
		{
			dto = generateLargeExpensesDriver(id, startDate, endDate);
		}
		else 
		{
			throw new InvalidArgumentException ("Error while parsing dates in statistics");
		}
		
		return dto;
	}
	

	public StatisticsResponseDTO getExpensesStatsForAdmin(statisticsRequestDTO requestDTO) {
		
		
		Date startDate = parseDate(requestDTO.getStartDate());
		Date endDate = parseDate(requestDTO.getEndDate());
		Duration duration = Duration.between(startDate.toInstant(), endDate.toInstant());
		StatisticsResponseDTO dto = null;
		if (duration.toDays()<31) 
		{
			dto = generateSmallExpensesAdmin(startDate, endDate);
		}
		else if (duration.toDays()>=31 && duration.toDays()<=365) 
		{
			dto = generateMediumExpensesAdmin(startDate, endDate);
		}
		else if (duration.toDays()>365) 
		{
			dto = generateLargeExpensesAdmin(startDate, endDate);
		}
		else 
		{
			throw new InvalidArgumentException ("Error while parsing dates in statistics");
		}
		
		return dto;
	}
	
	private StatisticsResponseDTO generateLargeExpensesDriver(Long id, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	private StatisticsResponseDTO generateMediumExpensesDriver(Long id, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	private StatisticsResponseDTO generateSmallExpensesDriver(Long id, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private StatisticsResponseDTO generateLargeExpensesPassenger(Long id, Date startDate, Date endDate) {
	// TODO Auto-generated method stub
	return null;
}

	private StatisticsResponseDTO generateMediumExpensesPassenger(Long id, Date startDate, Date endDate) {
	// TODO Auto-generated method stub
	return null;
}

	private StatisticsResponseDTO generateSmallExpensesPassenger(Long id, Date startDate, Date endDate) {
	// TODO Auto-generated method stub
	return null;
}
	
	private StatisticsResponseDTO generateLargeExpensesAdmin(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	private StatisticsResponseDTO generateMediumExpensesAdmin(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	private StatisticsResponseDTO generateSmallExpensesAdmin(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	private StatisticsResponseDTO generateLargeKilometersAdmin(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	private StatisticsResponseDTO generateMediumKilometersAdmin(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	private StatisticsResponseDTO generateSmallKilometersAdmin(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	private StatisticsResponseDTO generateLargeRidesAdmin(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	private StatisticsResponseDTO generateMediumRidesAdmin(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	private StatisticsResponseDTO generateSmallRidesAdmin(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	private StatisticsResponseDTO generateLargeRidesDriver(Long id, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	private StatisticsResponseDTO generateMediumRidesDriver(Long id, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	private StatisticsResponseDTO generateSmallRidesDriver(Long id, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	private StatisticsResponseDTO generateLargeRidesPassenger(Long id, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	private StatisticsResponseDTO generateMediumRidesPassenger(Long id, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	private StatisticsResponseDTO generateSmallRidesPassenger(Long id, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private StatisticsResponseDTO generateLargeKilometersDriver(Long id, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	private StatisticsResponseDTO generateMediumKilometersDriver(Long id, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	private StatisticsResponseDTO generateSmallKilometersDriver(Long id, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private StatisticsResponseDTO generateLargeKilometersPassenger(Long id, Date startDate, Date endDate) {
	// TODO Auto-generated method stub
	return null;
}

	private StatisticsResponseDTO generateMediumKilometersPassenger(Long id, Date startDate, Date endDate) {
	// TODO Auto-generated method stub
	return null;
}

	private StatisticsResponseDTO generateSmallKilometersPassenger(Long id, Date startDate, Date endDate) {
	// TODO Auto-generated method stub
	return null;
}

	public Date parseDate (String date) 
	{
		Instant instant = Instant.parse(date);
		Date parsedDate = Date.from(instant);
		return parsedDate;
	}

	

}
