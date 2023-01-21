package tim24.projekat.uberapp.service;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

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
import tim24.projekat.uberapp.model.RideStatus;
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
	private RideRepository rideRepo;
	

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
	
	private StatisticsResponseDTO generateLargeExpensesPassenger(Long id, Date startDate, Date endDate) {
		String type = "bar";
		List<Double> data = new ArrayList<Double>();
		List<String> labels = new ArrayList<String>();
		double total = 0;
		double average = 0;
		
		List<Ride> rides = rideRepo.findAllByDriverIdOrPassengerIdAndStartTimeBetweenAsList(id, startDate, endDate);
		TreeMap<Date,Double> map = new TreeMap<Date,Double>();
		for (Ride r : rides) 
		{
			Date d = r.getStartTime();
			LocalDate date = LocalDate.ofInstant(d.toInstant(), ZoneId.systemDefault());
			LocalDate firstDay = date.withDayOfYear(1);
			Date d1 = Date.from(firstDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
			
			if (map.containsKey(d1)) 
			{
				map.put(d1, map.get(d1)+r.getCost());
			}
			else 
			{
				map.put(d1, (double)r.getCost());
			}
		}
		
		double sum = 0;
		SimpleDateFormat formatter =  new SimpleDateFormat("yyyy");
		for (Date d : map.keySet()) 
		{
			String formattedDate = formatter.format(d);
			labels.add(formattedDate);
			data.add(map.get(d));
			sum += map.get(d);
		}
		total = (double)sum;
		if (map.keySet().size()>0) 
		{
			average = (double)sum/map.keySet().size();
		}
		
		return new StatisticsResponseDTO(type,data,labels,total,average);
}

	private StatisticsResponseDTO generateMediumExpensesPassenger(Long id, Date startDate, Date endDate) {
		String type = "pie";
		List<Double> data = new ArrayList<Double>();
		List<String> labels = new ArrayList<String>();
		double total = 0;
		double average = 0;
		
		List<Ride> rides = rideRepo.findAllByDriverIdOrPassengerIdAndStartTimeBetweenAsList(id, startDate, endDate);
		TreeMap<Date,Double> map = new TreeMap<Date,Double>();
		for (Ride r : rides) 
		{
			Date d = r.getStartTime();
			LocalDate date = LocalDate.ofInstant(d.toInstant(), ZoneId.systemDefault());
			LocalDate firstDay = date.withDayOfMonth(1);
			Date d1 = Date.from(firstDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
			
			if (map.containsKey(d1)) 
			{
				map.put(d1, map.get(d1)+r.getCost());
			}
			else 
			{
				map.put(d1, (double)r.getCost());
			}
		}
		
		double sum = 0;
		SimpleDateFormat formatter =  new SimpleDateFormat("MMMM yyyy");
		for (Date d : map.keySet()) 
		{
			String formattedDate = formatter.format(d);
			labels.add(formattedDate);
			data.add(map.get(d));
			sum += map.get(d);
		}
		total = (double)sum;
		if (map.keySet().size()>0) 
		{
			average = (double)sum/map.keySet().size();
		}
		
		return new StatisticsResponseDTO(type,data,labels,total,average);
}

	private StatisticsResponseDTO generateSmallExpensesPassenger(Long id, Date startDate, Date endDate) {
		String type = "line";
		List<Double> data = new ArrayList<Double>();
		List<String> labels = new ArrayList<String>();
		double total = 0;
		double average = 0;
		
		List<Ride> rides = rideRepo.findAllByDriverIdOrPassengerIdAndStartTimeBetweenAsList(id, startDate, endDate);
		TreeMap<Date,Double> map = new TreeMap<Date,Double>();
		for (Ride r : rides) 
		{
			Date d = r.getStartTime();
			if (map.containsKey(d)) 
			{
				map.put(d, map.get(d)+r.getCost());
			}
			else 
			{
				map.put(d, (double)r.getCost());
			}
		}
		
		double sum = 0;
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");
		for (Date d : map.keySet()) 
		{
			String formattedDate = formatter.format(d);
			labels.add(formattedDate);
			data.add(map.get(d));
			sum += map.get(d);
		}
		total = (double)sum;
		if (map.keySet().size()>0) 
		{
			average = (double)sum/map.keySet().size();
		}
		
		return new StatisticsResponseDTO(type,data,labels,total,average);
}
	
	private StatisticsResponseDTO generateLargeExpensesAdmin(Date startDate, Date endDate) {
		String type = "bar";
		List<Double> data = new ArrayList<Double>();
		List<String> labels = new ArrayList<String>();
		double total = 0;
		double average = 0;
		
		List<Ride> rides = rideRepo.findRidesWithStatusBetweenDates(RideStatus.FINISHED, startDate, endDate);
		TreeMap<Date,Double> map = new TreeMap<Date,Double>();
		for (Ride r : rides) 
		{
			Date d = r.getStartTime();
			LocalDate date = LocalDate.ofInstant(d.toInstant(), ZoneId.systemDefault());
			LocalDate firstDay = date.withDayOfYear(1);
			Date d1 = Date.from(firstDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
			
			if (map.containsKey(d1)) 
			{
				map.put(d1, map.get(d1)+r.getCost());
			}
			else 
			{
				map.put(d1, (double)r.getCost());
			}
		}
		
		double sum = 0;
		SimpleDateFormat formatter =  new SimpleDateFormat("yyyy");
		for (Date d : map.keySet()) 
		{
			String formattedDate = formatter.format(d);
			labels.add(formattedDate);
			data.add(map.get(d));
			sum += map.get(d);
		}
		total = (double)sum;
		if (map.keySet().size()>0) 
		{
			average = (double)sum/map.keySet().size();
		}
		
		return new StatisticsResponseDTO(type,data,labels,total,average);
}

	private StatisticsResponseDTO generateMediumExpensesAdmin(Date startDate, Date endDate) {
		String type = "pie";
		List<Double> data = new ArrayList<Double>();
		List<String> labels = new ArrayList<String>();
		double total = 0;
		double average = 0;
		
		List<Ride> rides = rideRepo.findRidesWithStatusBetweenDates(RideStatus.FINISHED, startDate, endDate);
		TreeMap<Date,Double> map = new TreeMap<Date,Double>();
		for (Ride r : rides) 
		{
			Date d = r.getStartTime();
			LocalDate date = LocalDate.ofInstant(d.toInstant(), ZoneId.systemDefault());
			LocalDate firstDay = date.withDayOfMonth(1);
			Date d1 = Date.from(firstDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
			
			if (map.containsKey(d1)) 
			{
				map.put(d1, map.get(d1)+r.getCost());
			}
			else 
			{
				map.put(d1, (double)r.getCost());
			}
		}
		
		double sum = 0;
		SimpleDateFormat formatter =  new SimpleDateFormat("MMMM yyyy");
		for (Date d : map.keySet()) 
		{
			String formattedDate = formatter.format(d);
			labels.add(formattedDate);
			data.add(map.get(d));
			sum += map.get(d);
		}
		total = (double)sum;
		if (map.keySet().size()>0) 
		{
			average = (double)sum/map.keySet().size();
		}
		
		return new StatisticsResponseDTO(type,data,labels,total,average);
}

	private StatisticsResponseDTO generateSmallExpensesAdmin(Date startDate, Date endDate) {
		String type = "line";
		List<Double> data = new ArrayList<Double>();
		List<String> labels = new ArrayList<String>();
		double total = 0;
		double average = 0;
		
		List<Ride> rides = rideRepo.findRidesWithStatusBetweenDates(RideStatus.FINISHED, startDate, endDate);
		TreeMap<Date,Double> map = new TreeMap<Date,Double>();
		for (Ride r : rides) 
		{
			Date d = r.getStartTime();
			if (map.containsKey(d)) 
			{
				map.put(d, map.get(d)+r.getCost());
			}
			else 
			{
				map.put(d, (double)r.getCost());
			}
		}
		
		double sum = 0;
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");
		for (Date d : map.keySet()) 
		{
			String formattedDate = formatter.format(d);
			labels.add(formattedDate);
			data.add(map.get(d));
			sum += map.get(d);
		}
		total = (double)sum;
		if (map.keySet().size()>0) 
		{
			average = (double)sum/map.keySet().size();
		}
		
		return new StatisticsResponseDTO(type,data,labels,total,average);
}

	private StatisticsResponseDTO generateLargeKilometersAdmin(Date startDate, Date endDate) {
		String type = "bar";
		List<Double> data = new ArrayList<Double>();
		List<String> labels = new ArrayList<String>();
		double total = 0;
		double average = 0;
		
		List<Ride> rides = rideRepo.findRidesWithStatusBetweenDates(RideStatus.FINISHED, startDate, endDate);
		TreeMap<Date,Double> map = new TreeMap<Date,Double>();
		for (Ride r : rides) 
		{
			Date d = r.getStartTime();
			LocalDate date = LocalDate.ofInstant(d.toInstant(), ZoneId.systemDefault());
			LocalDate firstDay = date.withDayOfYear(1);
			Date d1 = Date.from(firstDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
			
			if (map.containsKey(d1)) 
			{
				map.put(d1, map.get(d1)+r.getRoute().getLenghtInKm());
			}
			else 
			{
				map.put(d1, +r.getRoute().getLenghtInKm());
			}
		}
		
		double sum = 0;
		SimpleDateFormat formatter =  new SimpleDateFormat("yyyy");
		for (Date d : map.keySet()) 
		{
			String formattedDate = formatter.format(d);
			labels.add(formattedDate);
			data.add(map.get(d));
			sum += map.get(d);
		}
		total = (double)sum;
		if (map.keySet().size()>0) 
		{
			average = (double)sum/map.keySet().size();
		}
		
		return new StatisticsResponseDTO(type,data,labels,total,average);
}

	private StatisticsResponseDTO generateMediumKilometersAdmin(Date startDate, Date endDate) {
		String type = "pie";
		List<Double> data = new ArrayList<Double>();
		List<String> labels = new ArrayList<String>();
		double total = 0;
		double average = 0;
		
		List<Ride> rides = rideRepo.findRidesWithStatusBetweenDates(RideStatus.FINISHED, startDate, endDate);
		TreeMap<Date,Double> map = new TreeMap<Date,Double>();
		for (Ride r : rides) 
		{
			Date d = r.getStartTime();
			LocalDate date = LocalDate.ofInstant(d.toInstant(), ZoneId.systemDefault());
			LocalDate firstDay = date.withDayOfMonth(1);
			Date d1 = Date.from(firstDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
			
			if (map.containsKey(d1)) 
			{
				map.put(d1, map.get(d1)+r.getRoute().getLenghtInKm());
			}
			else 
			{
				map.put(d1, (double)r.getRoute().getLenghtInKm());
			}
		}
		
		double sum = 0;
		SimpleDateFormat formatter =  new SimpleDateFormat("MMMM yyyy");
		for (Date d : map.keySet()) 
		{
			String formattedDate = formatter.format(d);
			labels.add(formattedDate);
			data.add(map.get(d));
			sum += map.get(d);
		}
		total = (double)sum;
		if (map.keySet().size()>0) 
		{
			average = (double)sum/map.keySet().size();
		}
		
		return new StatisticsResponseDTO(type,data,labels,total,average);
}

	private StatisticsResponseDTO generateSmallKilometersAdmin(Date startDate, Date endDate) {
		String type = "line";
		List<Double> data = new ArrayList<Double>();
		List<String> labels = new ArrayList<String>();
		double total = 0;
		double average = 0;
		
		List<Ride> rides = rideRepo.findRidesWithStatusBetweenDates(RideStatus.FINISHED, startDate, endDate);
		TreeMap<Date,Double> map = new TreeMap<Date,Double>();
		for (Ride r : rides) 
		{
			Date d = r.getStartTime();
			if (map.containsKey(d)) 
			{
				map.put(d, map.get(d)+r.getRoute().getLenghtInKm());
			}
			else 
			{
				map.put(d, r.getRoute().getLenghtInKm());
			}
		}
		
		double sum = 0;
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");
		for (Date d : map.keySet()) 
		{
			String formattedDate = formatter.format(d);
			labels.add(formattedDate);
			data.add(map.get(d));
			sum += map.get(d);
		}
		total = (double)sum;
		if (map.keySet().size()>0) 
		{
			average = (double)sum/map.keySet().size();
		}
		
		return new StatisticsResponseDTO(type,data,labels,total,average);
}

	private StatisticsResponseDTO generateLargeRidesAdmin(Date startDate, Date endDate) {
		String type = "bar";
		List<Double> data = new ArrayList<Double>();
		List<String> labels = new ArrayList<String>();
		double total = 0;
		double average = 0;
		
		List<Ride> rides = rideRepo.findRidesWithStatusBetweenDates(RideStatus.FINISHED, startDate, endDate);
		TreeMap<Date,Double> map = new TreeMap<Date,Double>();
		for (Ride r : rides) 
		{
			Date d = r.getStartTime();
			LocalDate date = LocalDate.ofInstant(d.toInstant(), ZoneId.systemDefault());
			LocalDate firstDay = date.withDayOfYear(1);
			Date d1 = Date.from(firstDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
			
			if (map.containsKey(d1)) 
			{
				map.put(d1, map.get(d1)+1);
			}
			else 
			{
				map.put(d1, 1.0);
			}
		}
		
		double sum = 0;
		SimpleDateFormat formatter =  new SimpleDateFormat("yyyy");
		for (Date d : map.keySet()) 
		{
			String formattedDate = formatter.format(d);
			labels.add(formattedDate);
			data.add(map.get(d));
			sum += map.get(d);
		}
		total = (double)sum;
		if (map.keySet().size()>0) 
		{
			average = (double)sum/map.keySet().size();
		}
		
		return new StatisticsResponseDTO(type,data,labels,total,average);
}

	private StatisticsResponseDTO generateMediumRidesAdmin(Date startDate, Date endDate) {
		String type = "pie";
		List<Double> data = new ArrayList<Double>();
		List<String> labels = new ArrayList<String>();
		double total = 0;
		double average = 0;
		
		List<Ride> rides = rideRepo.findRidesWithStatusBetweenDates(RideStatus.FINISHED, startDate, endDate);
		TreeMap<Date,Double> map = new TreeMap<Date,Double>();
		for (Ride r : rides) 
		{
			Date d = r.getStartTime();
			LocalDate date = LocalDate.ofInstant(d.toInstant(), ZoneId.systemDefault());
			LocalDate firstDay = date.withDayOfMonth(1);
			Date d1 = Date.from(firstDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
			
			if (map.containsKey(d1)) 
			{
				map.put(d1, map.get(d1)+1);
			}
			else 
			{
				map.put(d1, 1.0);
			}
		}
		
		double sum = 0;
		SimpleDateFormat formatter =  new SimpleDateFormat("MMMM yyyy");
		for (Date d : map.keySet()) 
		{
			String formattedDate = formatter.format(d);
			labels.add(formattedDate);
			data.add(map.get(d));
			sum += map.get(d);
		}
		total = (double)sum;
		if (map.keySet().size()>0) 
		{
			average = (double)sum/map.keySet().size();
		}
		
		return new StatisticsResponseDTO(type,data,labels,total,average);
}

	private StatisticsResponseDTO generateSmallRidesAdmin(Date startDate, Date endDate) {
		
		String type = "line";
		List<Double> data = new ArrayList<Double>();
		List<String> labels = new ArrayList<String>();
		double total = 0;
		double average = 0;
		
		List<Ride> rides = rideRepo.findRidesWithStatusBetweenDates(RideStatus.FINISHED, startDate, endDate);
		TreeMap<Date,Integer> map = new TreeMap<Date,Integer>();
		for (Ride r : rides) 
		{
			Date d = r.getStartTime();
			if (map.containsKey(d)) 
			{
				map.put(d, map.get(d)+1);
			}
			else 
			{
				map.put(d, 1);
			}
		}
		
		double sum = 0;
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");
		for (Date d : map.keySet()) 
		{
			String formattedDate = formatter.format(d);
			labels.add(formattedDate);
			data.add((double)map.get(d));
			sum += map.get(d);
		}
		total = sum;
		if (map.keySet().size()>0) 
		{
			average = (double)sum/map.keySet().size();
		}
		
		
		return new StatisticsResponseDTO(type,data,labels,total,average);
	}

	private StatisticsResponseDTO generateLargeRidesDriver(Long id, Date startDate, Date endDate) {
		String type = "bar";
		List<Double> data = new ArrayList<Double>();
		List<String> labels = new ArrayList<String>();
		double total = 0;
		double average = 0;
		
		List<Ride> rides = rideRepo.findAllByDriverIdOrPassengerIdAndStartTimeBetweenAsList(id, startDate, endDate);
		TreeMap<Date,Double> map = new TreeMap<Date,Double>();
		for (Ride r : rides) 
		{
			Date d = r.getStartTime();
			LocalDate date = LocalDate.ofInstant(d.toInstant(), ZoneId.systemDefault());
			LocalDate firstDay = date.withDayOfYear(1);
			Date d1 = Date.from(firstDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
			
			if (map.containsKey(d1)) 
			{
				map.put(d1, map.get(d1)+1);
			}
			else 
			{
				map.put(d1, 1.0);
			}
		}
		
		double sum = 0;
		SimpleDateFormat formatter =  new SimpleDateFormat("yyyy");
		for (Date d : map.keySet()) 
		{
			String formattedDate = formatter.format(d);
			labels.add(formattedDate);
			data.add(map.get(d));
			sum += map.get(d);
		}
		total = (double)sum;
		if (map.keySet().size()>0) 
		{
			average = (double)sum/map.keySet().size();
		}
		
		return new StatisticsResponseDTO(type,data,labels,total,average);
}

	private StatisticsResponseDTO generateMediumRidesDriver(Long id, Date startDate, Date endDate) {
		String type = "pie";
		List<Double> data = new ArrayList<Double>();
		List<String> labels = new ArrayList<String>();
		double total = 0;
		double average = 0;
		
		List<Ride> rides = rideRepo.findAllByDriverIdOrPassengerIdAndStartTimeBetweenAsList(id, startDate, endDate);
		TreeMap<Date,Double> map = new TreeMap<Date,Double>();
		for (Ride r : rides) 
		{
			Date d = r.getStartTime();
			LocalDate date = LocalDate.ofInstant(d.toInstant(), ZoneId.systemDefault());
			LocalDate firstDay = date.withDayOfMonth(1);
			Date d1 = Date.from(firstDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
			
			if (map.containsKey(d1)) 
			{
				map.put(d1, map.get(d1)+1);
			}
			else 
			{
				map.put(d1, 1.0);
			}
		}
		
		double sum = 0;
		SimpleDateFormat formatter =  new SimpleDateFormat("MMMM yyyy");
		for (Date d : map.keySet()) 
		{
			String formattedDate = formatter.format(d);
			labels.add(formattedDate);
			data.add(map.get(d));
			sum += map.get(d);
		}
		total = (double)sum;
		if (map.keySet().size()>0) 
		{
			average = (double)sum/map.keySet().size();
		}
		
		return new StatisticsResponseDTO(type,data,labels,total,average);
}

	private StatisticsResponseDTO generateSmallRidesDriver(Long id, Date startDate, Date endDate) {
		
		String type = "line";
		List<Double> data = new ArrayList<Double>();
		List<String> labels = new ArrayList<String>();
		double total = 0;
		double average = 0;
		
		List<Ride> rides = rideRepo.findAllByDriverIdOrPassengerIdAndStartTimeBetweenAsList(id, startDate, endDate);
		TreeMap<Date,Integer> map = new TreeMap<Date,Integer>();
		for (Ride r : rides) 
		{
			Date d = r.getStartTime();
			if (map.containsKey(d)) 
			{
				map.put(d, map.get(d)+1);
			}
			else 
			{
				map.put(d, 1);
			}
		}
		
		double sum = 0;
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");
		for (Date d : map.keySet()) 
		{
			String formattedDate = formatter.format(d);
			labels.add(formattedDate);
			data.add((double)map.get(d));
			sum += map.get(d);
		}
		total = sum;
		if (map.keySet().size()>0) 
		{
			average = (double)sum/map.keySet().size();
		}
		
		
		return new StatisticsResponseDTO(type,data,labels,total,average);
	}

	private StatisticsResponseDTO generateLargeRidesPassenger(Long id, Date startDate, Date endDate) {
		String type = "bar";
		List<Double> data = new ArrayList<Double>();
		List<String> labels = new ArrayList<String>();
		double total = 0;
		double average = 0;
		
		List<Ride> rides = rideRepo.findAllByDriverIdOrPassengerIdAndStartTimeBetweenAsList(id, startDate, endDate);
		TreeMap<Date,Double> map = new TreeMap<Date,Double>();
		for (Ride r : rides) 
		{
			Date d = r.getStartTime();
			LocalDate date = LocalDate.ofInstant(d.toInstant(), ZoneId.systemDefault());
			LocalDate firstDay = date.withDayOfYear(1);
			Date d1 = Date.from(firstDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
			
			if (map.containsKey(d1)) 
			{
				map.put(d1, map.get(d1)+1);
			}
			else 
			{
				map.put(d1, 1.0);
			}
		}
		
		double sum = 0;
		SimpleDateFormat formatter =  new SimpleDateFormat("yyyy");
		for (Date d : map.keySet()) 
		{
			String formattedDate = formatter.format(d);
			labels.add(formattedDate);
			data.add(map.get(d));
			sum += map.get(d);
		}
		total = (double)sum;
		if (map.keySet().size()>0) 
		{
			average = (double)sum/map.keySet().size();
		}
		
		return new StatisticsResponseDTO(type,data,labels,total,average);
}

	private StatisticsResponseDTO generateMediumRidesPassenger(Long id, Date startDate, Date endDate) {
		String type = "pie";
		List<Double> data = new ArrayList<Double>();
		List<String> labels = new ArrayList<String>();
		double total = 0;
		double average = 0;
		
		List<Ride> rides = rideRepo.findAllByDriverIdOrPassengerIdAndStartTimeBetweenAsList(id, startDate, endDate);
		TreeMap<Date,Double> map = new TreeMap<Date,Double>();
		for (Ride r : rides) 
		{
			Date d = r.getStartTime();
			LocalDate date = LocalDate.ofInstant(d.toInstant(), ZoneId.systemDefault());
			LocalDate firstDay = date.withDayOfMonth(1);
			Date d1 = Date.from(firstDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
			
			if (map.containsKey(d1)) 
			{
				map.put(d1, map.get(d1)+1);
			}
			else 
			{
				map.put(d1, 1.0);
			}
		}
		
		double sum = 0;
		SimpleDateFormat formatter =  new SimpleDateFormat("MMMM yyyy");
		for (Date d : map.keySet()) 
		{
			String formattedDate = formatter.format(d);
			labels.add(formattedDate);
			data.add(map.get(d));
			sum += map.get(d);
		}
		total = (double)sum;
		if (map.keySet().size()>0) 
		{
			average = (double)sum/map.keySet().size();
		}
		
		return new StatisticsResponseDTO(type,data,labels,total,average);
}

	private StatisticsResponseDTO generateSmallRidesPassenger(Long id, Date startDate, Date endDate) {
		
		String type = "line";
		List<Double> data = new ArrayList<Double>();
		List<String> labels = new ArrayList<String>();
		double total = 0;
		double average = 0;
		
		List<Ride> rides = rideRepo.findAllByDriverIdOrPassengerIdAndStartTimeBetweenAsList(id, startDate, endDate);
		TreeMap<Date,Integer> map = new TreeMap<Date,Integer>();
		for (Ride r : rides) 
		{
			Date d = r.getStartTime();
			if (map.containsKey(d)) 
			{
				map.put(d, map.get(d)+1);
			}
			else 
			{
				map.put(d, 1);
			}
		}
		
		double sum = 0;
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");
		for (Date d : map.keySet()) 
		{
			String formattedDate = formatter.format(d);
			labels.add(formattedDate);
			data.add((double)map.get(d));
			sum += map.get(d);
		}
		total = sum;
		if (map.keySet().size()>0) 
		{
			average = (double)sum/map.keySet().size();
		}
		
		
		return new StatisticsResponseDTO(type,data,labels,total,average);
	}
	
	private StatisticsResponseDTO generateLargeKilometersDriver(Long id, Date startDate, Date endDate) {
		String type = "bar";
		List<Double> data = new ArrayList<Double>();
		List<String> labels = new ArrayList<String>();
		double total = 0;
		double average = 0;
		
		List<Ride> rides = rideRepo.findAllByDriverIdOrPassengerIdAndStartTimeBetweenAsList(id, startDate, endDate);
		TreeMap<Date,Double> map = new TreeMap<Date,Double>();
		for (Ride r : rides) 
		{
			Date d = r.getStartTime();
			LocalDate date = LocalDate.ofInstant(d.toInstant(), ZoneId.systemDefault());
			LocalDate firstDay = date.withDayOfYear(1);
			Date d1 = Date.from(firstDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
			
			if (map.containsKey(d1)) 
			{
				map.put(d1, map.get(d1)+r.getRoute().getLenghtInKm());
			}
			else 
			{
				map.put(d1, +r.getRoute().getLenghtInKm());
			}
		}
		
		double sum = 0;
		SimpleDateFormat formatter =  new SimpleDateFormat("yyyy");
		for (Date d : map.keySet()) 
		{
			String formattedDate = formatter.format(d);
			labels.add(formattedDate);
			data.add(map.get(d));
			sum += map.get(d);
		}
		total = (double)sum;
		if (map.keySet().size()>0) 
		{
			average = (double)sum/map.keySet().size();
		}
		
		return new StatisticsResponseDTO(type,data,labels,total,average);
}

	private StatisticsResponseDTO generateMediumKilometersDriver(Long id, Date startDate, Date endDate) {
		String type = "pie";
		List<Double> data = new ArrayList<Double>();
		List<String> labels = new ArrayList<String>();
		double total = 0;
		double average = 0;
		
		List<Ride> rides = rideRepo.findAllByDriverIdOrPassengerIdAndStartTimeBetweenAsList(id, startDate, endDate);
		TreeMap<Date,Double> map = new TreeMap<Date,Double>();
		for (Ride r : rides) 
		{
			Date d = r.getStartTime();
			LocalDate date = LocalDate.ofInstant(d.toInstant(), ZoneId.systemDefault());
			LocalDate firstDay = date.withDayOfMonth(1);
			Date d1 = Date.from(firstDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
			
			if (map.containsKey(d1)) 
			{
				map.put(d1, map.get(d1)+r.getRoute().getLenghtInKm());
			}
			else 
			{
				map.put(d1, +r.getRoute().getLenghtInKm());
			}
		}
		
		double sum = 0;
		SimpleDateFormat formatter =  new SimpleDateFormat("MMMM yyyy");
		for (Date d : map.keySet()) 
		{
			String formattedDate = formatter.format(d);
			labels.add(formattedDate);
			data.add(map.get(d));
			sum += map.get(d);
		}
		total = (double)sum;
		if (map.keySet().size()>0) 
		{
			average = (double)sum/map.keySet().size();
		}
		
		return new StatisticsResponseDTO(type,data,labels,total,average);
}

	private StatisticsResponseDTO generateSmallKilometersDriver(Long id, Date startDate, Date endDate) {
		String type = "line";
		List<Double> data = new ArrayList<Double>();
		List<String> labels = new ArrayList<String>();
		double total = 0;
		double average = 0;
		
		List<Ride> rides = rideRepo.findAllByDriverIdOrPassengerIdAndStartTimeBetweenAsList(id, startDate, endDate);
		TreeMap<Date,Double> map = new TreeMap<Date,Double>();
		for (Ride r : rides) 
		{
			Date d = r.getStartTime();
			if (map.containsKey(d)) 
			{
				map.put(d, map.get(d)+r.getRoute().getLenghtInKm());
			}
			else 
			{
				map.put(d, r.getRoute().getLenghtInKm());
			}
		}
		
		double sum = 0;
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");
		for (Date d : map.keySet()) 
		{
			String formattedDate = formatter.format(d);
			labels.add(formattedDate);
			data.add(map.get(d));
			sum += map.get(d);
		}
		total = (double)sum;
		if (map.keySet().size()>0) 
		{
			average = (double)sum/map.keySet().size();
		}
		
		return new StatisticsResponseDTO(type,data,labels,total,average);
}
	
	private StatisticsResponseDTO generateLargeKilometersPassenger(Long id, Date startDate, Date endDate) {
		String type = "bar";
		List<Double> data = new ArrayList<Double>();
		List<String> labels = new ArrayList<String>();
		double total = 0;
		double average = 0;
		
		List<Ride> rides = rideRepo.findAllByDriverIdOrPassengerIdAndStartTimeBetweenAsList(id, startDate, endDate);
		TreeMap<Date,Double> map = new TreeMap<Date,Double>();
		for (Ride r : rides) 
		{
			Date d = r.getStartTime();
			LocalDate date = LocalDate.ofInstant(d.toInstant(), ZoneId.systemDefault());
			LocalDate firstDay = date.withDayOfYear(1);
			Date d1 = Date.from(firstDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
			
			if (map.containsKey(d1)) 
			{
				map.put(d1, map.get(d1)+r.getRoute().getLenghtInKm());
			}
			else 
			{
				map.put(d1, +r.getRoute().getLenghtInKm());
			}
		}
		
		double sum = 0;
		SimpleDateFormat formatter =  new SimpleDateFormat("yyyy");
		for (Date d : map.keySet()) 
		{
			String formattedDate = formatter.format(d);
			labels.add(formattedDate);
			data.add(map.get(d));
			sum += map.get(d);
		}
		total = (double)sum;
		if (map.keySet().size()>0) 
		{
			average = (double)sum/map.keySet().size();
		}
		
		return new StatisticsResponseDTO(type,data,labels,total,average);
}

	private StatisticsResponseDTO generateMediumKilometersPassenger(Long id, Date startDate, Date endDate) {
		String type = "pie";
		List<Double> data = new ArrayList<Double>();
		List<String> labels = new ArrayList<String>();
		double total = 0;
		double average = 0;
		
		List<Ride> rides = rideRepo.findAllByDriverIdOrPassengerIdAndStartTimeBetweenAsList(id, startDate, endDate);
		TreeMap<Date,Double> map = new TreeMap<Date,Double>();
		for (Ride r : rides) 
		{
			Date d = r.getStartTime();
			LocalDate date = LocalDate.ofInstant(d.toInstant(), ZoneId.systemDefault());
			LocalDate firstDay = date.withDayOfMonth(1);
			Date d1 = Date.from(firstDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
			
			if (map.containsKey(d1)) 
			{
				map.put(d1, map.get(d1)+r.getRoute().getLenghtInKm());
			}
			else 
			{
				map.put(d1, +r.getRoute().getLenghtInKm());
			}
		}
		
		double sum = 0;
		SimpleDateFormat formatter =  new SimpleDateFormat("MMMM yyyy");
		for (Date d : map.keySet()) 
		{
			String formattedDate = formatter.format(d);
			labels.add(formattedDate);
			data.add(map.get(d));
			sum += map.get(d);
		}
		total = (double)sum;
		if (map.keySet().size()>0) 
		{
			average = (double)sum/map.keySet().size();
		}
		
		return new StatisticsResponseDTO(type,data,labels,total,average);
}

	private StatisticsResponseDTO generateSmallKilometersPassenger(Long id, Date startDate, Date endDate) {
		String type = "line";
		List<Double> data = new ArrayList<Double>();
		List<String> labels = new ArrayList<String>();
		double total = 0;
		double average = 0;
		
		List<Ride> rides = rideRepo.findAllByDriverIdOrPassengerIdAndStartTimeBetweenAsList(id, startDate, endDate);
		TreeMap<Date,Double> map = new TreeMap<Date,Double>();
		for (Ride r : rides) 
		{
			Date d = r.getStartTime();
			if (map.containsKey(d)) 
			{
				map.put(d, map.get(d)+r.getRoute().getLenghtInKm());
			}
			else 
			{
				map.put(d, r.getRoute().getLenghtInKm());
			}
		}
		
		double sum = 0;
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");
		for (Date d : map.keySet()) 
		{
			String formattedDate = formatter.format(d);
			labels.add(formattedDate);
			data.add(map.get(d));
			sum += map.get(d);
		}
		total = (double)sum;
		if (map.keySet().size()>0) 
		{
			average = (double)sum/map.keySet().size();
		}
		
		return new StatisticsResponseDTO(type,data,labels,total,average);
}

	public Date parseDate (String date) 
	{
		Instant instant = Instant.parse(date);
		Date parsedDate = Date.from(instant);
		return parsedDate;
	}

	

}
