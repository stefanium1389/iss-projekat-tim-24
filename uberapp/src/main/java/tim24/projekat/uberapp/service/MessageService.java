package tim24.projekat.uberapp.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim24.projekat.uberapp.DTO.ContactDTO;
import tim24.projekat.uberapp.DTO.DTOList;
import tim24.projekat.uberapp.DTO.MessageDTO;
import tim24.projekat.uberapp.DTO.MessageRequestDTO;
import tim24.projekat.uberapp.DTO.SpecificMessagesRequestIDsDTO;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.model.Message;
import tim24.projekat.uberapp.model.MessageType;
import tim24.projekat.uberapp.model.Ride;
import tim24.projekat.uberapp.model.Role;
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
	
	public DTOList<ContactDTO> getUserContacts(Long id, String sort, String filter, String keyword) {
		Optional<User> opt = userRepo.findById(id);
		if(opt.isEmpty()) {
			throw new ObjectNotFoundException("User does not exsist");
		}
		
		User user = opt.get();
		boolean isDriver = false;
		if (user.getRole()== Role.DRIVER) 
		{
			isDriver=true;
		}
		
		List<Ride> rides;
		if (isDriver == false)
		{
			rides = rideRepo.findAllRideByPassengersId(id);
		}
		else 
		{
			rides = rideRepo.findAllRideByDriverId(id);
		}
		
		List <ContactDTO> contacts = new ArrayList<ContactDTO>();
		
		for (Ride r: rides) 
		{
			List<Message> messages = messageRepo.findAllByRideId(r.getId());
			
			if (isDriver) 
			{
				List<Long> ids = new ArrayList<>();
				for (Message m: messages) 
				{
					if (!filter(m,filter)) 
					{
						continue;
					}
					Long otherPersonId = getOtherPerson(id,m);
					if (!ids.contains(otherPersonId)) 
					{
						User otherUser = userRepo.findById(otherPersonId).get();
						ContactDTO contact = new ContactDTO(otherUser.getProfilePictureAsString(),r.getRoute().getStartLocation().getAddress(),r.getRoute().getEndLocation().getAddress()
								,r.getId(),otherPersonId,r.getStartTime());
						contacts.add(contact);
						ids.add(otherPersonId);
					}
				}
			}
			if (!isDriver) 
			{
				if (!filterMultiple(messages,filter) || messages.isEmpty()) 
				{
					continue;
				}
				
				ContactDTO contact = new ContactDTO(r.getDriver().getProfilePictureAsString(),r.getRoute().getStartLocation().getAddress(),r.getRoute().getEndLocation().getAddress()
						,r.getId(),r.getDriver().getId(),r.getStartTime());
				contacts.add(contact);
			}
			
		}
		
		System.out.println("kontakti pre sortiranja"+ contacts.size());
		
		contacts.sort(Comparator.comparing(contact -> {
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		    try {
		        return sdf.parse(contact.getRideStartDate());
		    } catch (ParseException e) {
		        return null;
		    }
		}));

	
		if (!sort.toLowerCase().trim().equals("ascending")) 
 
		{
		 Collections.reverse(contacts);
		}
		
		contacts = search(contacts,keyword);
		
		DTOList<ContactDTO> dtos = new DTOList<ContactDTO>(); 
		for (ContactDTO c : contacts) 
		{
			dtos.add(c);
		}
		
		return dtos;
	}
	
	private List<ContactDTO> search(List<ContactDTO> contacts, String keyword) {
		
		if (keyword.equals("")) 
		{
			return contacts;
		}
		
		List<ContactDTO> suitable = new ArrayList<>();
		
		for (ContactDTO c : contacts) 
		{
			if (c.getStartAddress().contains(keyword) || c.getEndAddress().contains(keyword)) 
			{
				suitable.add(c);
			}
		}
		return suitable;
	}

	private Long getOtherPerson (Long me, Message m) 
	{
		if (me == m.getSender().getId()) 
		{
			return m.getReciever().getId();
		}
		return  m.getSender().getId();
	}
	
	private boolean filter (Message m, String filter) 
	{
		
		filter = filter.toUpperCase().trim();
		
		
		if (filter.equals("ANY")) 
		{
			return true;
		}
			
		
		if (filter.equals("SUPPORT") && m.getType().equals(MessageType.SUPPORT)) 
		{
			return true;
		}
		else if (filter.equals("PANIC") && m.getType().equals(MessageType.PANIC)) 
		{
			return true;
		}
		else if (filter.equals("RIDE") && m.getType().equals(MessageType.RIDE)) 
		{
			return true;
		}
		else return false;
		
	}
	
	private boolean filterMultiple (List<Message> messages, String filter) 
	{
		for (Message m: messages) 
		{
			if (filter(m,filter)) 
			{
				return true;
			}
		}
		return false;
	}
	
	public DTOList<MessageDTO> getSpecificMessages(SpecificMessagesRequestIDsDTO dto) {
		Long userId = dto.getUserId();
		Long otherId = dto.getOtherId();
		Long rideId = dto.getRideId();
		boolean adminPresent = false;
		
		Optional<User> opt = userRepo.findById(userId);
		if(opt.isEmpty()) {
			throw new ObjectNotFoundException("User does not exsist");
		}
		if (opt.get().getRole().equals(Role.ADMIN))
			adminPresent = true;
		
		Optional<User> otheropt = userRepo.findById(otherId);
		if(otheropt.isEmpty()) {
			throw new ObjectNotFoundException("User does not exsist");
		}
		if (otheropt.get().getRole().equals(Role.ADMIN))
			adminPresent = true;
		
		if (rideId!=null && rideId!=-1) 
		{
			Optional<Ride> rideOpt = rideRepo.findById(rideId);
			if (rideOpt.isEmpty()) 
			{
				throw new ObjectNotFoundException("Ride does not exsist");
			}
		}
		
		
		
		List<Message> messages = new ArrayList<>();
		if (adminPresent) 
		{
			messages = messageRepo.findBySenderAndRecipient(userId, otherId);
		}
		else 
		{
			messages = messageRepo.findBySenderAndRecipientAndRide(userId, otherId, rideId);
		}
		
		DTOList<MessageDTO> dtos = new DTOList<MessageDTO>(); 
		for (Message m : messages) 
		{
			
			/* String timeOfSending, Long senderId, Long receiverId, String message, String type,
			Long rideId, String nameToDisplay*/
			String nameToDisplay = "Me";
			if (userId == m.getReciever().getId())
			{
				nameToDisplay = otheropt.get().getName()+" "+otheropt.get().getSurname();
			}
			Long rId = null;
			if (m.getRide()!=null) 
			{
				rId = m.getRide().getId();
			}
			dtos.add(new MessageDTO(m.getId(),m.getTimestamp(),m.getSender().getId(),m.getReciever().getId(),m.getMessage(),m.getType().toString(),rId,nameToDisplay));
		}
		
		System.out.println("Uspeo sam sa dobivanjem poruka");
		return dtos;
	}
	
	

	public void postUserMessage(Long id, MessageRequestDTO dto, String sender) {
		Optional<User> recOpt = userRepo.findById(id);
		if(recOpt.isEmpty()) {
			throw new ObjectNotFoundException("User does not exist");
		}
		Ride ride = null;
		if (dto.getRideId()!=null) 
		{
			Optional<Ride> rOpt = rideRepo.findById(dto.getRideId());
			
			if (rOpt.isPresent()) 
			{
				ride = rOpt.get();
			}
		}
		
		Optional<User> sOpt = userRepo.findUserByEmail(sender);
		if(sOpt.isEmpty()) {
			throw new ObjectNotFoundException("User does not exist");
		}
		Message m = new Message(new Date(System.currentTimeMillis()),sOpt.get(),recOpt.get(),dto.getMessage(),dto.getType(),ride);
		
		messageRepo.save(m);
		messageRepo.flush();
		
		
		
		
		
		
	}
	
}
