package tim24.projekat.uberapp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim24.projekat.uberapp.DTO.SuccessDTO;
import tim24.projekat.uberapp.exception.ActionExpiredException;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.model.Activation;
import tim24.projekat.uberapp.model.User;
import tim24.projekat.uberapp.repo.ActivationRepository;
import tim24.projekat.uberapp.repo.UserRepository;

@Service
public class ActivationService {
	
	@Autowired
	private ActivationRepository activationRepo;
	@Autowired
	private UserRepository userRepo;
	
	private static int EXPIRES = 60*60*24; //1 dan
	
	
	private String generateToken() {
		UUID uuid = UUID.randomUUID();
		String token = uuid.toString();
		return token;
	}

	public String generateActivation(String email) {
		Activation activation = new Activation();
		activation.setEmail(email);
		String token = this.generateToken();
		activation.setToken(token);
		activation.setTimestamp(new Date(System.currentTimeMillis()));
		activation.setExpires(new Date(System.currentTimeMillis()+(EXPIRES*1000)));
		activationRepo.save(activation);
		activationRepo.flush();
		return token;
	}
	
	public SuccessDTO activatePassenger(String token)
	{
		Optional<Activation> activation = activationRepo.findActivationByToken(token);
		if(activation.isEmpty()) {
			throw new ObjectNotFoundException("Nema te aktivacije");
		}
		Activation actual = activation.get();
		if(actual.getExpires().before(new Date(System.currentTimeMillis()))) {
			throw new ActionExpiredException("Aktivacija istekla");
		}
		Optional<User> user = userRepo.findUserByEmail(actual.getEmail());
		if(user.isEmpty()) {
			activationRepo.delete(actual);
			activationRepo.flush();
			throw new ObjectNotFoundException("Email ne postoji!");
		}
		user.get().setActivated(true);
		userRepo.save(user.get());
		activationRepo.delete(actual);
		userRepo.flush();
		activationRepo.flush();
		SuccessDTO dto = new SuccessDTO("Successful account activation!");
		return dto;
	}
	public List<String> regenerateActivation(String token) {
		Optional<Activation> activation = activationRepo.findActivationByToken(token);
		if(activation.isEmpty()) {
			throw new ObjectNotFoundException("Nema te aktivacije");
		}
		List<String> lista = new ArrayList<String>();
		String newToken = this.generateActivation(activation.get().getEmail());
		lista.add(newToken);
		lista.add(activation.get().getEmail());
		return lista;
		
	}
	
}
