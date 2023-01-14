package tim24.projekat.uberapp.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import tim24.projekat.uberapp.DTO.DTOList;
import tim24.projekat.uberapp.DTO.RideDTO;
import tim24.projekat.uberapp.DTO.UserRegistrationDTO;
import tim24.projekat.uberapp.DTO.UserResponseDTO;
import tim24.projekat.uberapp.exception.EmailAlreadyExistsException;
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

	public UserResponseDTO postPassenger(UserRegistrationDTO userRegistrationDTO) {
		
		Optional<User> existingUser = userRepo.findUserByEmail(userRegistrationDTO.getEmail());
		if(existingUser.isPresent()) {
			throw new EmailAlreadyExistsException("User with that email already exists!");
		}
		User newUser = new User();
		newUser.setName(userRegistrationDTO.getName());
		newUser.setEmail(userRegistrationDTO.getEmail());
		newUser.setAddress(userRegistrationDTO.getAddress());
		newUser.setSurname(userRegistrationDTO.getSurname());
		newUser.setPassword(userRegistrationDTO.getPassword());
		newUser.setTelephoneNumber(userRegistrationDTO.getTelephoneNumber());
		newUser.setProfilePicture(userRegistrationDTO.getProfilePicture());
		newUser.setRole(Role.USER);
		newUser.setActivated(false);
		newUser.setBlocked(false);
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

	public DTOList<UserResponseDTO> getPassengers()
	{
		DTOList<UserResponseDTO> list = new DTOList<UserResponseDTO>();
        UserResponseDTO user = new UserResponseDTO();
        list.add(user);
        return list;
	}

	public UserResponseDTO getPassenger(Long id) {
		  return new UserResponseDTO();
	}

	public UserResponseDTO updatePassenger(Long id) {
		return new UserResponseDTO();
	}

	public DTOList<RideDTO> getPassengerRides(Long id, int page, int size, String sort, String from, String to)
	{
		DTOList<RideDTO> list = new DTOList<>();
        RideDTO ride = new RideDTO();
        list.add(ride);
        return list;
	}
	

}
