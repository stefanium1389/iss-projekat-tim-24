package tim24.projekat.uberapp.service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import tim24.projekat.uberapp.DTO.ChangePasswordRequestDTO;
import tim24.projekat.uberapp.DTO.CodeAndPasswordDTO;
import tim24.projekat.uberapp.DTO.SuccessDTO;
import tim24.projekat.uberapp.exception.ConditionNotMetException;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.model.PasswordReset;
import tim24.projekat.uberapp.model.User;
import tim24.projekat.uberapp.repo.PasswordResetRepository;
import tim24.projekat.uberapp.repo.UserRepository;

@Service
public class PasswordResetService {
	
	@Autowired
	private PasswordResetRepository passwordResetRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private UserService userService;
	@Autowired
	private MailingService mailService;
	@Autowired
	private PasswordEncoder encoder;
	
	private static int EXPIRES = 60*60*24; //1 dan
	
	
	private String generateToken() {
		UUID uuid = UUID.randomUUID();
		String token = uuid.toString();
		return token;
	}
	
	private String generatePasswordResetRequest(String email) {
		Optional<User> uOpt = userRepo.findUserByEmail(email);
		if(uOpt.isEmpty()) {
			throw new ObjectNotFoundException("Korisnik ne postoji, registrujte se!");
		}
		PasswordReset reset = new PasswordReset();
		reset.setEmail(email);
		String token = this.generateToken();
		reset.setToken(token);
		reset.setTimestamp(new Date(System.currentTimeMillis()));
		reset.setExpires(new Date(System.currentTimeMillis()+(EXPIRES*1000)));
		passwordResetRepo.save(reset);
		passwordResetRepo.flush();
		return token;
	}

	public SuccessDTO postPasswordReset(String email) {
		
		String token = generatePasswordResetRequest(email);
		try {
			mailService.sendPasswordResetMail(email, token);
		} 
		catch (MessagingException e) {
			throw new ConditionNotMetException("Problem with email sending!");
		}
		return new SuccessDTO("Successfully sent email!");
		
	}

	public SuccessDTO putPasswordReset(CodeAndPasswordDTO dto) {
		Optional<PasswordReset> prOpt = passwordResetRepo.findByToken(dto.getCode());
		if(prOpt.isEmpty()) {
			throw new ObjectNotFoundException("Code is incorrect!");
		}
		PasswordReset actual = prOpt.get();
		if(actual.getExpires().before(new Date(System.currentTimeMillis()))) {
			throw new ConditionNotMetException("Code has expired!");
		}
		Optional<User> uOpt = userRepo.findUserByEmail(actual.getEmail());
		if(uOpt.isEmpty()) {
			throw new ObjectNotFoundException("User does not exist!");
		}
		User user = uOpt.get();
		String password = encoder.encode(dto.getNewPassword());
		user.setPassword(password);
		userRepo.save(user);
		userRepo.flush();
		passwordResetRepo.delete(actual);
		passwordResetRepo.flush();
		
		return new SuccessDTO("Password successfully changed!");	
	}

	public SuccessDTO putChangePassword(Long id, ChangePasswordRequestDTO dto) {
		User actual = userService.findUserById(id);
		String encoded = encoder.encode(dto.getOldPassword());
		if(!encoded.equals(actual.getPassword())) {
			System.err.println("stara iz korisnika-"+actual.getPassword());
			System.err.println("stara iz dto-"+encoded +" neenkodirana "+dto.getOldPassword());
			System.err.println("nova-"+encoder.encode(dto.getOldPassword())+" neenkodirana "+dto.getNewPassword());
			throw new ConditionNotMetException("Current password is not matching!");
		}
		actual.setPassword(encoder.encode(dto.getNewPassword()));
		userRepo.save(actual);
		userRepo.flush();
		return new SuccessDTO("Password successfully changed!");
	}
}
