package tim24.projekat.uberapp.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim24.projekat.uberapp.DTO.DTOList;
import tim24.projekat.uberapp.DTO.MessageDTO;
import tim24.projekat.uberapp.DTO.MessageRequestDTO;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.model.Message;
import tim24.projekat.uberapp.model.Ride;
import tim24.projekat.uberapp.model.User;
import tim24.projekat.uberapp.repo.MessageRepository;
import tim24.projekat.uberapp.repo.RideRepository;
import tim24.projekat.uberapp.repo.UserRepository;

@Service
public class MessageService {
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RideRepository rideRepo;
	
	@Autowired
	private MessageRepository messageRepo;

	public DTOList<MessageDTO> getUserMessages(Long id) {
		Optional<User> opt = userRepo.findById(id);
		if(opt.isEmpty()) {
			throw new ObjectNotFoundException("User does not exist");
		}
		List<Message> list = messageRepo.findAllByRecieverId(id);
		DTOList<MessageDTO> dtos = new DTOList<MessageDTO>(); 
		for(Message m : list) {
			MessageDTO dto = new MessageDTO();
			dto.setId(m.getId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			String timeString = sdf.format(m.getTimestamp());
			dto.setTimeOfSending(timeString);
			dto.setSenderId(m.getSender().getId());
			dto.setReceiverId(m.getReciever().getId());
			dto.setType(m.getType().toString());
			dto.setRideId(m.getRide().getId());
			dtos.add(dto);
		}
		return dtos;
	}

	public void postUserMessage(Long id, MessageRequestDTO dto, String sender) {
		Optional<User> recOpt = userRepo.findById(id);
		if(recOpt.isEmpty()) {
			throw new ObjectNotFoundException("User does not exist");
		}
		Optional<Ride> rOpt = rideRepo.findById(dto.getRideId());
		if(rOpt.isEmpty()) {
			throw new ObjectNotFoundException("Ride does not exist");
		}
		Optional<User> sOpt = userRepo.findUserByEmail(sender);
		if(sOpt.isEmpty()) {
			throw new ObjectNotFoundException("User does not exist");
		}
		Message m = new Message(new Date(System.currentTimeMillis()),sOpt.get(),recOpt.get(),dto.getMessage(),dto.getType(),rOpt.get());
		
		messageRepo.save(m);
		messageRepo.flush();
		
		
		
		
		
		
	}
	
}
