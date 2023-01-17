package tim24.projekat.uberapp.service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import tim24.projekat.uberapp.DTO.DTOList;
import tim24.projekat.uberapp.DTO.RideDTO;
import tim24.projekat.uberapp.DTO.SuccessDTO;
import tim24.projekat.uberapp.DTO.UserRegistrationDTO;
import tim24.projekat.uberapp.DTO.UserResponseDTO;
import tim24.projekat.uberapp.DTO.UserUpdateRequestDTO;
import tim24.projekat.uberapp.exception.EmailAlreadyExistsException;
import tim24.projekat.uberapp.exception.InvalidArgumentException;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.model.Ride;
import tim24.projekat.uberapp.model.Role;
import tim24.projekat.uberapp.model.User;
import tim24.projekat.uberapp.repo.RideRepository;
import tim24.projekat.uberapp.repo.UserRepository;

@Service
public class PassengerService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired 
	private RideRepository rideRepo;
	
	@Autowired 
	private ActivationService activationService;
	
	@Autowired
	private MailingService mailService;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	public UserResponseDTO postPassenger(UserRegistrationDTO userRegistrationDTO) {
		
		Optional<User> existingUser = userRepo.findUserByEmail(userRegistrationDTO.getEmail());
		if(existingUser.isPresent()) {
			throw new EmailAlreadyExistsException("User with that email already exists!");
		}
		String password = passwordEncoder.encode(userRegistrationDTO.getPassword());
		User newUser = new User(userRegistrationDTO,password);
		userRepo.save(newUser);
		userRepo.flush();
		String token = activationService.generateActivation(userRegistrationDTO.getEmail());
		try {
			mailService.sendActivationEmail(userRegistrationDTO.getEmail(), token);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		UserResponseDTO response = new UserResponseDTO(newUser);
		
		return response;
	}

	public DTOList<UserResponseDTO> getPassengers(int page, int size)
	{
		DTOList<UserResponseDTO> list = new DTOList<UserResponseDTO>();
		Page<User> passengers = userRepo.findAllByRole(Role.USER, PageRequest.of(page,size));
		for(User d : passengers.getContent()) {
			list.add(new UserResponseDTO(d));
		}
		list.setTotalCount((int) passengers.getTotalElements());
        return list;
	}

	public UserResponseDTO getPassenger(Long id) {
		Optional<User> passengerOpt = userRepo.findByIdAndRole(id, Role.USER);
		if (passengerOpt.isEmpty()) 
		{
			throw new ObjectNotFoundException("Passenger does not exist!");
		}
		
		User passenger = passengerOpt.get();
		UserResponseDTO dto = new UserResponseDTO(passenger);
		return dto;
	}

	public UserResponseDTO updatePassenger(Long id, UserUpdateRequestDTO dto) {
		Optional<User> passengerOpt = userRepo.findByIdAndRole(id, Role.USER);
		if (passengerOpt.isEmpty()) 
		{
			throw new ObjectNotFoundException("Passenger does not exist!");
		}
		User passenger = passengerOpt.get();
		passenger.update(dto);
		
		userRepo.save(passenger);
		userRepo.flush();
		
		return new UserResponseDTO(passenger);
	}

	public DTOList<RideDTO> getPassengerRides(Long id, int page, int size, String sort, String fromDate, String toDate)
	{
		Optional<User> passengerOpt = userRepo.findByIdAndRole(id, Role.USER);
		if (passengerOpt.isEmpty()) 
		{
			throw new ObjectNotFoundException("Passenger does not exist!");
		}		
		Date startDate = parseDate(fromDate);
		Date endDate = parseDate(toDate);
		Page<Ride> ridesPage = null;
		try {
			Pageable pageable = PageRequest.of(page, size, Sort.by(Direction.ASC, sort));
			ridesPage = rideRepo.findByPassengerIdAndStartTimeBetween(id, startDate, endDate, pageable);
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

	public SuccessDTO resendActivation(String id) {
		
		List<String> lista = activationService.regenerateActivation(id);
		try {
			mailService.sendActivationEmail(lista.get(1), lista.get(0)); //lista[1] je mail, a lista[0] je token
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return new SuccessDTO("Uspesno poslato opet");
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
	
}
