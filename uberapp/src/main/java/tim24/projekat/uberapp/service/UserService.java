package tim24.projekat.uberapp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim24.projekat.uberapp.DTO.DTOList;
import tim24.projekat.uberapp.DTO.GeoCoordinateDTO;
import tim24.projekat.uberapp.DTO.LoginRequestDTO;
import tim24.projekat.uberapp.DTO.LoginResponseDTO;
import tim24.projekat.uberapp.DTO.MessageDTO;
import tim24.projekat.uberapp.DTO.MessageRequestDTO;
import tim24.projekat.uberapp.DTO.MessageSendResponseDTO;
import tim24.projekat.uberapp.DTO.NoteDTO;
import tim24.projekat.uberapp.DTO.NoteRequestDTO;
import tim24.projekat.uberapp.DTO.NoteResponseDTO;
import tim24.projekat.uberapp.DTO.RejectionDTO;
import tim24.projekat.uberapp.DTO.RideDTO;
import tim24.projekat.uberapp.DTO.RouteDTO;
import tim24.projekat.uberapp.DTO.UnregisteredRequestDTO;
import tim24.projekat.uberapp.DTO.UnregisteredResponseDTO;
import tim24.projekat.uberapp.DTO.UserRef;
import tim24.projekat.uberapp.DTO.UserResponseDTO;
import tim24.projekat.uberapp.exception.ConditionNotMetException;
import tim24.projekat.uberapp.exception.InvalidArgumentException;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.model.DurationDistance;
import tim24.projekat.uberapp.model.Role;
import tim24.projekat.uberapp.model.User;
import tim24.projekat.uberapp.model.VehicleType;
import tim24.projekat.uberapp.repo.UserRepository;
import tim24.projekat.uberapp.repo.VehicleTypeRepository;


@Service
public class UserService {
	
	private final UserRepository UserRepo;
	
	@Autowired
	public UserService(UserRepository UserRepo) {
		this.UserRepo = UserRepo;
	}

	public User addUser(User User) 
	{
		return UserRepo.save(User);
	}
	
	public List<User> findAllUsers()
	{
		return UserRepo.findAll();
	}
	
	public User updateUser(User User) 
	{
		return UserRepo.save(User);
	}
	
	public void deleteUser(Long id) 
	{
		UserRepo.deleteUserById(id);
	}
	
	public User findUserById (Long id) 
	{
		return UserRepo.findUserById(id).orElseThrow(()-> new ObjectNotFoundException("User not found."));
	}
	
	public User findUserByEmail(String email) {
		return UserRepo.findUserByEmail(email).orElseThrow(()-> new ObjectNotFoundException("User not found."));
	}
	
	public DTOList<RideDTO> getUserRidesById(Long id, int page, int size, String sort, String from, String to) {
		List<RideDTO> rides = new ArrayList<RideDTO>();
		RejectionDTO rej = new RejectionDTO ("neki razlog","datummm");
		GeoCoordinateDTO gcd1 = new GeoCoordinateDTO ("adresa1",123,321);
		GeoCoordinateDTO gcd2 = new GeoCoordinateDTO ("adresa2",424,572);
		
		ArrayList<RouteDTO> routes = new ArrayList<RouteDTO>();
		routes.add(new RouteDTO(gcd1,gcd2));
		
		ArrayList<UserRef> passengers = new ArrayList<UserRef>();
		passengers.add(new UserRef(1L, "mailic@mail.com"));
		
		RideDTO r = new RideDTO(300L, "18:44", "19:30", 123,new UserRef(2L, "mailicXD@mail.com"),passengers,40,"tip",false,true,rej,routes, "PENDING");
		rides.add(r);
		DTOList<RideDTO> dtoList = new DTOList<RideDTO>(rides.size(), rides);
		return dtoList;
	}

	public DTOList<UserResponseDTO> getUsers(int page, int size) {
		ArrayList<UserResponseDTO> list = new ArrayList<UserResponseDTO>();
		UserResponseDTO u1 = new UserResponseDTO (1L,"vladimir","golosin","url","123213","mail","adresa");
		UserResponseDTO u2 = new UserResponseDTO (2L,"hladimir","golosin","url","565656","mail","adresa");
		list.add(u1);
		list.add(u2);
		DTOList<UserResponseDTO> dtoList = new DTOList<UserResponseDTO>(list.size(),list);
		return dtoList;
	}

	public DTOList<MessageDTO> getUserMessagesById(Long id) {
		ArrayList<MessageDTO> list = new ArrayList<MessageDTO>();
		MessageDTO m1 = new MessageDTO(1L, LocalDateTime.now(),2L,44L,"asdasd","tip",100L);
		list.add(m1);
		DTOList<MessageDTO> dtoList = new DTOList<MessageDTO>(list.size(),list);
		return dtoList;
	}

	public DTOList<NoteDTO> getUserNotesById(Long id, int page, int size) {
		ArrayList<NoteDTO> list = new ArrayList<NoteDTO>();
		NoteDTO n1 = new NoteDTO(1L, LocalDateTime.now(),"asdasd");
		list.add(n1);
		DTOList<NoteDTO> dtoList = new DTOList<NoteDTO>(list.size(),list);
		return dtoList;
	}

	public LoginResponseDTO postLogin(LoginRequestDTO loginRequestDTO) {
		LoginResponseDTO response = new LoginResponseDTO("asdasd","ffgdfgfdgfd");
		return response;
	}

	public MessageSendResponseDTO postMessageById(Long id, MessageRequestDTO messageRequestDTO) {
		MessageSendResponseDTO m = new MessageSendResponseDTO(1L, LocalDateTime.now(),4L, 10L,"asdasdsa","tip",101L);
		return m;
	}

	public NoteResponseDTO postNoteById(Long id, NoteRequestDTO nrd) {
		NoteResponseDTO response = new NoteResponseDTO (101L, LocalDateTime.now(), nrd.getMessage());
		return response;
	}

	
	public void putBlockUserById(Long id)
	{
		User user = findUserById(id);
		if(user.isBlocked())
			throw new ConditionNotMetException("This user is already blocked.");
		if(user.getRole() == Role.ADMIN)
			throw new ConditionNotMetException("You cannot block an admin.");
		user.setBlocked(true);
		UserRepo.save(user);
	}

	public void putUnblockUserById(Long id)
	{
		User user = findUserById(id);
		if(! user.isBlocked())
			throw new ConditionNotMetException("This user is already unblocked.");
		if(user.getRole() == Role.ADMIN)
			throw new ConditionNotMetException("You cannot unblock an admin.");
		user.setBlocked(false);
		UserRepo.save(user);
	}

	public DTOList<UserResponseDTO> searchUsers(String querry) {
		List<User> allUsers = UserRepo.findAll();
		List<User> foundUsers = new ArrayList<User>();
		for (User user : allUsers) {
			if(user.getName().contains(querry) || user.getEmail().contains(querry)) {
				foundUsers.add(user);
			}
		}
		DTOList<UserResponseDTO> dtoList = this.repackUsersToDTO(foundUsers);
		return dtoList;
	}
	private DTOList<UserResponseDTO> repackUsersToDTO(List<User> usersList){
		DTOList<UserResponseDTO> returnList = new DTOList<UserResponseDTO>();
		for(User user: usersList) {
			UserResponseDTO dto = new UserResponseDTO(user);
			returnList.add(dto);
		}
		return returnList;
		
	}
}
