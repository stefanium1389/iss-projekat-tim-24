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
import tim24.projekat.uberapp.DTO.DriverDocumentDTO;
import tim24.projekat.uberapp.DTO.DriverDocumentRequestDTO;
import tim24.projekat.uberapp.DTO.RideDTO;
import tim24.projekat.uberapp.DTO.UserRegistrationDTO;
import tim24.projekat.uberapp.DTO.UserResponseDTO;
import tim24.projekat.uberapp.DTO.UserUpdateRequestDTO;
import tim24.projekat.uberapp.DTO.VehicleDTO;
import tim24.projekat.uberapp.DTO.VehicleRequestDTO;
import tim24.projekat.uberapp.DTO.WorkingHourDTO;
import tim24.projekat.uberapp.DTO.WorkingHourPostDTO;
import tim24.projekat.uberapp.DTO.WorkingHourPutDTO;
import tim24.projekat.uberapp.exception.ConditionNotMetException;
import tim24.projekat.uberapp.exception.InvalidArgumentException;
import tim24.projekat.uberapp.exception.ObjectAlreadyPresentException;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.model.DriverDocument;
import tim24.projekat.uberapp.model.Ride;
import tim24.projekat.uberapp.model.Role;
import tim24.projekat.uberapp.model.User;
import tim24.projekat.uberapp.model.Vehicle;
import tim24.projekat.uberapp.model.VehicleType;
import tim24.projekat.uberapp.model.WorkingHour;
import tim24.projekat.uberapp.repo.DriverDocumentRepository;
import tim24.projekat.uberapp.repo.RideRepository;
import tim24.projekat.uberapp.repo.UserRepository;
import tim24.projekat.uberapp.repo.VehicleRepository;
import tim24.projekat.uberapp.repo.VehicleTypeRepository;
import tim24.projekat.uberapp.repo.WorkingHourRepo;

@Service
public class DriverService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private WorkingHourRepo workingHourRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private DriverDocumentRepository driverDocumentRepo;
	
	@Autowired
	private VehicleRepository vehicleRepo;
	
	@Autowired
	private RideRepository rideRepo;
	
	@Autowired
	private VehicleTypeRepository viehicleTypeRepo;
	
	public UserResponseDTO createDriver(UserRegistrationDTO newDriver) {
		Optional<User> existing = userRepo.findUserByEmail(newDriver.getEmail());
		if(existing.isPresent()) {
			throw new ObjectAlreadyPresentException("User with that email already exists!");
		}
		String password = passwordEncoder.encode(newDriver.getPassword());
		User driver = new User(newDriver, password);
		driver.setRole(Role.DRIVER); //defaultno role je USER a activated je false!
		driver.setActivated(true);
		userRepo.save(driver);
		userRepo.flush();
		
		UserResponseDTO dto = new UserResponseDTO(driver);
		
		return dto;
	}

	public DTOList<UserResponseDTO> GetAllDrivers(int page, int size) {
		DTOList<UserResponseDTO> list = new DTOList<UserResponseDTO>();
		Page<User> drivers = userRepo.findAllByRole(Role.DRIVER, PageRequest.of(page,size));
		for(User d : drivers.getContent()) {
			list.add(new UserResponseDTO(d));
		}
		list.setTotalCount((int) drivers.getTotalElements());
		return list;
	}

	public UserResponseDTO getDriverById(Long id) {
		
		Optional<User> driverOpt = userRepo.findByIdAndRole(id, Role.DRIVER);
		if (driverOpt.isEmpty()) 
		{
			throw new ObjectNotFoundException("Driver does not exist!");
		}
		
		User driver = driverOpt.get();
		UserResponseDTO dto = new UserResponseDTO(driver);
		return dto;
		
	}

	public UserResponseDTO updateDriver(Long id, UserUpdateRequestDTO updatedDriver) {
		Optional<User> driverOpt = userRepo.findByIdAndRole(id, Role.DRIVER);
		if (driverOpt.isEmpty()) 
		{
			throw new ObjectNotFoundException("Driver does not exist!");
		}
		User driver = driverOpt.get();
		driver.update(updatedDriver);
		
		userRepo.save(driver);
		userRepo.flush();
		
		return new UserResponseDTO(driver);
	}

	public ArrayList<DriverDocumentDTO> getDriverDocuments(Long id) {
		Optional<User> driverOpt = userRepo.findByIdAndRole(id, Role.DRIVER);
		if (driverOpt.isEmpty()) 
		{
			throw new ObjectNotFoundException("Driver does not exist!");
		}
		List<DriverDocument> documents = driverDocumentRepo.findAllByDriverId(id);
		ArrayList<DriverDocumentDTO> list = new ArrayList<>();
		for(DriverDocument d : documents) {
			list.add(new DriverDocumentDTO(d));
		}
		return list;
	}

	public DriverDocumentDTO createDriverDocuments(Long id, DriverDocumentRequestDTO ddrq) {
		Optional<User> driverOpt = userRepo.findByIdAndRole(id, Role.DRIVER);
		if (driverOpt.isEmpty()) 
		{
			throw new ObjectNotFoundException("Driver does not exist!");
		}
		DriverDocument dd = new DriverDocument(ddrq, id);
		driverDocumentRepo.save(dd);
		driverDocumentRepo.flush();
		DriverDocumentDTO dddto = new DriverDocumentDTO(dd);
		return dddto;
	}

	public void DeleteDriverDocument(Long id) {
		Optional<DriverDocument> ddOpt = driverDocumentRepo.findById(id);
		if (ddOpt.isEmpty()) 
		{
			throw new ObjectNotFoundException("Document does not exist!");
		}
		driverDocumentRepo.deleteById(id);		
		
	}

	public VehicleDTO getDriverVehicle(Long id) {
		Optional<User> driverOpt = userRepo.findByIdAndRole(id, Role.DRIVER);
		if (driverOpt.isEmpty()) 
		{
			throw new ObjectNotFoundException("Driver does not exist!");
		}
		Optional<Vehicle> vehicle = vehicleRepo.findVehicleByDriverId(id);
		if (vehicle.isEmpty()) {
			throw new ObjectNotFoundException("Vehicle does not exist!");
		}
		VehicleDTO v = new VehicleDTO(vehicle.get());
		return v;
	}

	public VehicleDTO updateVehicle(Long id, VehicleRequestDTO newV) {
		Optional<User> driverOpt = userRepo.findByIdAndRole(id, Role.DRIVER);
		if (driverOpt.isEmpty()) 
		{
			throw new ObjectNotFoundException("Driver does not exist!");
		}
		Optional<VehicleType> type = viehicleTypeRepo.findByTypeName(newV.getVehicleType());
		if (type.isEmpty()) 
		{
			throw new InvalidArgumentException("Invalid vehicle type");
		}
		Vehicle vehicle = new Vehicle(newV, driverOpt.get(), type.get());
		vehicleRepo.save(vehicle);
		vehicleRepo.flush();
		
		VehicleDTO v = new VehicleDTO(vehicle);
		return v;
	}	

	public VehicleDTO addDriverVehicle(Long id, VehicleRequestDTO newV) {
		Optional<User> driverOpt = userRepo.findByIdAndRole(id, Role.DRIVER);
		if (driverOpt.isEmpty()) 
		{
			throw new ObjectNotFoundException("Driver does not exist!");
		}
		Optional<Vehicle> vehicleOpt = vehicleRepo.findVehicleByDriverId(id);
		if(vehicleOpt.isPresent()) {
			throw new ObjectAlreadyPresentException("Driver already has vehicle");
		}
		Optional<VehicleType> type = viehicleTypeRepo.findByTypeName(newV.getVehicleType());
		if (type.isEmpty()) 
		{
			throw new InvalidArgumentException("Invalid vehicle type");
		}
		Vehicle vehicle = new Vehicle(newV, driverOpt.get(), type.get());
		vehicleRepo.save(vehicle);
		vehicleRepo.flush();
		
		VehicleDTO v = new VehicleDTO(vehicle);
		return v;
	}

	public DTOList<WorkingHourDTO> getDriverWorkinghour(Long id, int page, int size, String fromDate, String toDate) {
		Optional<User> driverOpt = userRepo.findByIdAndRole(id, Role.DRIVER);
		if (driverOpt.isEmpty()) 
		{
			throw new ObjectNotFoundException("Driver does not exist!");
		}		
		Date startDate = parseDate(fromDate);
		Date endDate = parseDate(toDate);
		Page<WorkingHour> whPage;
		Pageable pageable = PageRequest.of(page, size, Sort.by(Direction.DESC, "endTime"));
		whPage = workingHourRepo.findByDriverIdAndRideDateBetween(id, startDate, endDate, pageable);
		
		DTOList<WorkingHourDTO> whs = new DTOList<WorkingHourDTO>();
		for(WorkingHour wh : whPage.getContent()) {
			whs.add(new WorkingHourDTO(wh.getId(),formatDate(wh.getStartTime()),formatDate(wh.getEndTime())));
		}
		whs.setTotalCount((int) whPage.getTotalElements());
		return whs;
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
		Optional<User> driverOpt = userRepo.findByIdAndRole(id, Role.DRIVER);
		if (driverOpt.isEmpty()) 
		{
			throw new ObjectNotFoundException("Driver does not exist!");
		}		
		Date startDate = parseDate(fromDate);
		Date endDate = parseDate(toDate);
		Page<Ride> ridesPage = null;
		
		try {
			Pageable pageable = PageRequest.of(page, size, Sort.by(Direction.ASC, sort));
			ridesPage = rideRepo.findByDriverIdAndRideDateBetween(id, startDate, endDate, pageable);
		}
		catch(RuntimeException e) {
			throw new InvalidArgumentException(sort+ " is not a valid argument!");
		}
        
		DTOList<RideDTO> rides = new DTOList<RideDTO>();
		for(Ride r : ridesPage.getContent()) {
			rides.add(new RideDTO(r));
		}
		rides.setTotalCount((int) ridesPage.getTotalElements());
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
		Optional<WorkingHour> realWH = workingHourRepo.findById(workinghoursId);
		if(realWH.isEmpty()) {
			throw new ObjectNotFoundException("Working hour does not exist!");
		}
		WorkingHourDTO wh = new WorkingHourDTO(realWH.get().getId(),formatDate(realWH.get().getStartTime()),formatDate(realWH.get().getEndTime()));
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
