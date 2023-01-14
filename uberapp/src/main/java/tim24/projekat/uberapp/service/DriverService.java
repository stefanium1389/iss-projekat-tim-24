package tim24.projekat.uberapp.service;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim24.projekat.uberapp.DTO.DTOList;
import tim24.projekat.uberapp.DTO.DriverDocumentDTO;
import tim24.projekat.uberapp.DTO.DriverDocumentRequestDTO;
import tim24.projekat.uberapp.DTO.GeoCoordinateDTO;
import tim24.projekat.uberapp.DTO.RejectionDTO;
import tim24.projekat.uberapp.DTO.RideDTO;
import tim24.projekat.uberapp.DTO.RouteDTO;
import tim24.projekat.uberapp.DTO.UserRef;
import tim24.projekat.uberapp.DTO.UserRequestDTO;
import tim24.projekat.uberapp.DTO.UserResponseDTO;
import tim24.projekat.uberapp.DTO.VehicleDTO;
import tim24.projekat.uberapp.DTO.VehicleRequestDTO;
import tim24.projekat.uberapp.DTO.WorkingHourDTO;
import tim24.projekat.uberapp.DTO.WorkingHourPostDTO;
import tim24.projekat.uberapp.DTO.WorkingHourPutDTO;
import tim24.projekat.uberapp.exception.ConditionNotMetException;
import tim24.projekat.uberapp.exception.InvalidRoleException;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.model.Role;
import tim24.projekat.uberapp.model.User;
import tim24.projekat.uberapp.model.WorkingHour;
import tim24.projekat.uberapp.repo.UserRepository;
import tim24.projekat.uberapp.repo.WorkingHourRepo;

@Service
public class DriverService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private WorkingHourRepo workingHourRepo;

	public UserResponseDTO createDriver(UserRequestDTO newDriver) {
		return new UserResponseDTO(1L,"Stefan","Bogdanovic","profilePicture.jpg","+3810641234567","mail@email.com","Bulevar Oslobodjenja 169");
	}

	public DTOList<UserResponseDTO> GetAllDrivers(int page, int size) {
		DTOList<UserResponseDTO> list = new DTOList<UserResponseDTO>();
		UserResponseDTO driver1 = new UserResponseDTO(1L,"Stefan","Bogdanovic","profilePicture.jpg","+3810641234567","mail@email.com","Bulevar Oslobodjenja 169");
		list.add(driver1);
		UserResponseDTO driver2 = new UserResponseDTO(1L,"Pero","Perovic","profilePicture.jpg","+3810641235667","mail@email.com","Bulevar Evrope 42");
		list.add(driver2);
		return list;
	}

	public UserResponseDTO getDriverById(Long id) {
		
		Optional<User> driverOpt = userRepo.findUserById(id);
		if (driverOpt.isEmpty()) 
		{
			throw new ObjectNotFoundException("Driver does not exist!");
		}
		
		User driver = driverOpt.get();
		
		if (driver.getRole() == Role.DRIVER) 
		{
			UserResponseDTO dto = new UserResponseDTO(driver);
			return dto;
		}
		else {
			throw new InvalidRoleException("Found user is not a driver!");
		}
	}

	public UserResponseDTO updateDriver(Long id, UserRequestDTO updatedDriver) {
		return new UserResponseDTO(1L,"Stefan","Bogdanovic","profilePicture.jpg","+3810641234567","mail@email.com","Bulevar Oslobodjenja 169");
	}

	public ArrayList<DriverDocumentDTO> getDriverDocuments(Long id) {
		ArrayList<DriverDocumentDTO> list = new ArrayList<DriverDocumentDTO>();
		DriverDocumentDTO dd = new DriverDocumentDTO(1L,"stefanova vozacka","slika.png",1L);
		list.add(dd);
		return list;
	}

	public DriverDocumentDTO createDriverDocuments(Long id, DriverDocumentRequestDTO ddrq) {
		DriverDocumentDTO dd = new DriverDocumentDTO(1L,"stefanova vozacka","slika.png",1L);
		return dd;
	}

	public void DeleteDriver(Long id) {
		
		
	}

	public VehicleDTO getDriverVehicle(Long id) {
		VehicleDTO v = new VehicleDTO(1L, 1L,"STANDARD","Ford Mondeo","NS-42069", new GeoCoordinateDTO("Kraj sveta",1,1), 4,false,false );
		return v;
	}

	public VehicleDTO updateVehicle(Long id, VehicleRequestDTO newV) {
		VehicleDTO v = new VehicleDTO(1L, 1L,"STANDARD","Ford Mondeo","NS-42069", new GeoCoordinateDTO("Kraj sveta",1,1), 4,false,false );
		return v;
	}

	public VehicleDTO addDriverVehicle(Long id, VehicleRequestDTO newV) {
		VehicleDTO v = new VehicleDTO(1L, 1L,"STANDARD","Ford Mondeo","NS-42069", new GeoCoordinateDTO("Kraj sveta",1,1), 4,false,false );
		return v;
	}

	public DTOList<WorkingHourDTO> getDriverWorkinghour(Long id, int page, int size, String fromDate, String toDate) {
		DTOList<WorkingHourDTO> list = new DTOList<WorkingHourDTO>();
		WorkingHourDTO wh = new WorkingHourDTO(1L,"18.11.1991T19:00","19.11.1991T00:00");
		list.add(wh);
		return list;
	}
	
	public DTOList<WorkingHourDTO> getLastActiveDriverWorkinghour(Long id) {
		DTOList<WorkingHourDTO> list = new DTOList<WorkingHourDTO>();
		
		Optional<User> driverOpt = userRepo.findByIdAndRole(id, Role.DRIVER);
		if (driverOpt.isEmpty()) 
		{
			throw new ObjectNotFoundException("There is no driver by the given id!");
		}
		
		Optional<WorkingHour> whOpt = workingHourRepo.findLastWorkingHourByDriverId(id);
		if (whOpt.isEmpty()) 
		{
			return list;
		}
		
		WorkingHour wh = whOpt.get();
		String startTime = formatDate(wh.getStartTime());
		String endTime = formatDate(wh.getEndTime());
		WorkingHourDTO dto = new WorkingHourDTO(wh.getId(),startTime, endTime);
		list.add(dto);
		return list;
	}

	public WorkingHourDTO createDriverWorkinghour(Long id, WorkingHourPostDTO whDTO) {
		
		//provera da li postoji driver
		Optional<User> driverOpt = userRepo.findByIdAndRole(id, Role.DRIVER);
		if (driverOpt.isEmpty()) 
		{
			throw new ObjectNotFoundException("Driver does not exist!");
		}
		
		//provera da li već postoji aktivan wh
		Optional<WorkingHour> alreadyOpt = workingHourRepo.findLastWorkingHourByDriverId(id);
		
		if (alreadyOpt.isPresent()) 
		{
			throw new ObjectNotFoundException("Working hour has already started!");
		}
		
		//provera da li je vozač premašio 8h
		Duration todaysDuration = getDurationOfTodaysWorkByDriverId(id);
		long totalDurationHours = todaysDuration.toHours();
		
		if (totalDurationHours >= 8)
		{
			throw new ConditionNotMetException("Cannot start shift because you exceeded the 8 hours limit in last 24 hours!");
		}
		
		
		User driver = driverOpt.get();
		
		WorkingHour newWorkingHour = new WorkingHour(whDTO.getStart(), driver);
		workingHourRepo.save(newWorkingHour);
		workingHourRepo.flush();
		String startDate = formatDate(newWorkingHour.getStartTime());
		String endDate = formatDate(newWorkingHour.getEndTime());
		WorkingHourDTO wh = new WorkingHourDTO(newWorkingHour.getId(),startDate,endDate);
		return wh;
	}

	public DTOList<RideDTO> getDriverRides(Long id, int page, int size, String sort, String fromDate, String toDate) {
		DTOList<RideDTO> rides = new DTOList<RideDTO>();
		RejectionDTO rej = new RejectionDTO ("neki razlog","datummm");
		GeoCoordinateDTO gcd1 = new GeoCoordinateDTO ("adresa1",123,321);
		GeoCoordinateDTO gcd2 = new GeoCoordinateDTO ("adresa2",424,572);
		
		ArrayList<RouteDTO> routes = new ArrayList<RouteDTO>();
		routes.add(new RouteDTO(gcd1,gcd2));
		
		ArrayList<UserRef> passengers = new ArrayList<UserRef>();
		passengers.add(new UserRef(1L, "mailic@mail.com"));
		
		RideDTO r = new RideDTO(300L, "18:44", "19:30", 123,new UserRef(2L, "mailicXD@mail.com"),passengers,40,"tip",false,true,rej,routes, "PENDING");
		rides.add(r);
		return rides;
	}

	public WorkingHourDTO changeDriverWorkingHourDetails(Long id, WorkingHourPutDTO dto) {
		
		Optional<WorkingHour> hourOpt = workingHourRepo.findWorkingHourById(id);
		
		if (hourOpt.isEmpty()) 
		{
			throw new ObjectNotFoundException("Working hour does not exist!");
		}
		
		WorkingHour hour = hourOpt.get();
		hour.setEndTime(parseDate(dto.getend()));
		workingHourRepo.save(hour);
		workingHourRepo.flush();
		WorkingHourDTO wh = new WorkingHourDTO(hour.getId(),formatDate(hour.getStartTime()),formatDate(hour.getEndTime()));
		return wh;
	}

	public WorkingHourDTO getDriverWorkingHourDetails(Long workinghoursId) {
		WorkingHourDTO wh = new WorkingHourDTO(1L,"18.11.1991T19:00","19.11.1991T00:00");
		return wh;
	}
	
	public Date parseDate (String date) 
	{
		Instant instant = Instant.parse(date);
		Date parsedDate = Date.from(instant);
		return parsedDate;
	}
	
	public String formatDate (Date date) 
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		return sdf.format(date);
	}
	
	
	public Duration getDurationOfTodaysWorkByDriverId(Long driverId) {
		Date dayAgo = new Date(System.currentTimeMillis() - (24 * 60 * 60 * 1000));
	    List<WorkingHour> workingHours = workingHourRepo.findAllByDriverIdAndStartedInLast24Hours(driverId, dayAgo);
	    
	    Duration totalDuration = Duration.ZERO;
	    for (WorkingHour workingHour : workingHours) {
	        Instant startInstant = workingHour.getStartTime().toInstant();
	        Instant endInstant = workingHour.getEndTime().toInstant();
	        Duration duration = Duration.between(startInstant, endInstant);
	        totalDuration = totalDuration.plus(duration);
	    }
	    return totalDuration;
	}

	public String getDriverActiveHoursInLast24h(Long id) {
		
		//provera da li postoji driver
		Optional<User> driverOpt = userRepo.findByIdAndRole(id, Role.DRIVER);
		if (driverOpt.isEmpty()) 
		{
			throw new ObjectNotFoundException("Driver does not exist!");
		}
		
		Duration todaysDuration = getDurationOfTodaysWorkByDriverId(id);
		long totalDurationHours = todaysDuration.toHours();
		long remainingMinutes = todaysDuration.toMinutesPart();
		return totalDurationHours + " hours " + remainingMinutes +" minutes ";
	}

}
