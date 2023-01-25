package tim24.projekat.uberapp.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import tim24.projekat.uberapp.DTO.DTOList;
import tim24.projekat.uberapp.DTO.NoteRequestDTO;
import tim24.projekat.uberapp.DTO.NoteResponseDTO;
import tim24.projekat.uberapp.DTO.RideDTO;
import tim24.projekat.uberapp.DTO.UserCardResponseDTO;
import tim24.projekat.uberapp.DTO.UserResponseDTO;
import tim24.projekat.uberapp.exception.ConditionNotMetException;
import tim24.projekat.uberapp.exception.InvalidArgumentException;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.model.Note;
import tim24.projekat.uberapp.model.Ride;
import tim24.projekat.uberapp.model.Role;
import tim24.projekat.uberapp.model.User;
import tim24.projekat.uberapp.repo.NoteRepository;
import tim24.projekat.uberapp.repo.RideRepository;
import tim24.projekat.uberapp.repo.UserRepository;


@Service
public class UserService {
	private final UserRepository userRepo;
	
	
	@Autowired
	public UserService(UserRepository UserRepo) {
		this.userRepo = UserRepo;
	}
	@Autowired
	NoteRepository noteRepo;
	@Autowired
	RideRepository rideRepo;
	@Autowired
	DateUtils du;

	public User addUser(User User) 
	{
		return userRepo.save(User);
	}
	
	public List<User> findAllUsers()
	{
		return userRepo.findAll();
	}
	
	public User updateUser(User User) 
	{
		return userRepo.save(User);
	}
	
	public void deleteUser(Long id) 
	{
		userRepo.deleteUserById(id);
	}
	
	public User findUserById (Long id) 
	{
		return userRepo.findUserById(id).orElseThrow(()-> new ObjectNotFoundException("User not found."));
	}
	
	public User findUserByEmail(String email) {
		return userRepo.findUserByEmail(email).orElseThrow(()-> new ObjectNotFoundException("User not found."));
	}
	
	public DTOList<RideDTO> getUserRidesById(Long id, int page, int size, String sort, String fromDate, String toDate) {
		Optional<User> opt = userRepo.findById(id);
		if (opt.isEmpty()) 
		{
			throw new ObjectNotFoundException("User does not exist!");
		}		
		Date startDate = du.parseDate(fromDate);
		Date endDate = du.parseDate(toDate);
		Page<Ride> ridesPage = null;
		
		try {
			Pageable pageable = PageRequest.of(page, size, Sort.by(Direction.ASC, sort));
			ridesPage = rideRepo.findAllByDriverIdOrPassengerIdAndStartTimeBetween(id, startDate, endDate, pageable);
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

	public DTOList<UserResponseDTO> getUsers(int page, int size) {
		DTOList<UserResponseDTO> list = new DTOList<UserResponseDTO>();
		Page<User> users = userRepo.findAll(PageRequest.of(page,size));
		for(User d : users.getContent()) {
			list.add(new UserResponseDTO(d));
		}
		list.setTotalCount((int) users.getTotalElements());
		return list;
	}
	
	public DTOList<UserCardResponseDTO> getUsers2() {
		
		List<User> users = userRepo.findAllUsersNotRole(Role.ADMIN);
	
		DTOList<UserCardResponseDTO> dto = new DTOList<UserCardResponseDTO>();
		for(User d : users) {
			dto.add(new UserCardResponseDTO(d));
		}
		return dto;
	}


	public DTOList<NoteResponseDTO> getUserNotesById(Long id, int page, int size) {
		Optional<User> opt = userRepo.findById(id);
		if(opt.isEmpty()) {
			throw new ObjectNotFoundException("User does not exist!");
		}
		DTOList<NoteResponseDTO> list = new DTOList<NoteResponseDTO>();
		Page<Note> notes = noteRepo.findAllByUserId(id, PageRequest.of(page,size));
		for(Note d : notes.getContent()) {
			list.add(new NoteResponseDTO(d.getId(), du.formatDate(d.getTime()), d.getNote()));
		}
		list.setTotalCount((int) notes.getTotalElements());
		return list;
	}

	public NoteResponseDTO postNoteById(Long id, NoteRequestDTO noteDTO)
	{
		User user = findUserById(id);
		Date time = new Date();
		Note note = new Note(time, noteDTO.getMessage(), user);
		noteRepo.save(note);
		noteRepo.flush();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		String timeString = sdf.format(time);
		NoteResponseDTO noteResponseDTO = new NoteResponseDTO(note.getId(), timeString, note.getNote());
		return noteResponseDTO;
	}

	
	public void putBlockUserById(Long id)
	{
		User user = findUserById(id);
		if(user.isBlocked())
			throw new ConditionNotMetException("This user is already blocked.");
		if(user.getRole() == Role.ADMIN)
			throw new ConditionNotMetException("You cannot block an admin.");
		user.setBlocked(true);
		userRepo.save(user);
		userRepo.flush();
	}

	public void putUnblockUserById(Long id)
	{
		User user = findUserById(id);
		if(! user.isBlocked())
			throw new ConditionNotMetException("This user is already unblocked.");
		if(user.getRole() == Role.ADMIN)
			throw new ConditionNotMetException("You cannot unblock an admin.");
		user.setBlocked(false);
		userRepo.save(user);
		userRepo.flush();
	}

	public DTOList<UserResponseDTO> searchUsers(String querry) {
		List<User> foundUsers = userRepo.findByKeyword(querry);
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
	
	public UserResponseDTO getAdminById(Long id) {
		
		Optional<User> adminOpt = userRepo.findByIdAndRole(id, Role.ADMIN);
		if (adminOpt.isEmpty()) 
		{
			throw new ObjectNotFoundException("Admin does not exist!");
		}
		
		User admin = adminOpt.get();
		UserResponseDTO dto = new UserResponseDTO(admin);
		return dto;
		
	}
	
	public DTOList<UserCardResponseDTO> searchUsers2(String key) {
		List<User> list = userRepo.searchByKeywordAndNotRole(key,Role.ADMIN);
		DTOList<UserCardResponseDTO> dto = new DTOList<UserCardResponseDTO>();
		for(User d : list) {
			dto.add(new UserCardResponseDTO(d));
		}
		return dto;
	}
	
	public DTOList<UserCardResponseDTO> searchUsers3(String key) {
		List<User> list = userRepo.searchByKeywordAndRole(key,Role.USER);
		DTOList<UserCardResponseDTO> dto = new DTOList<UserCardResponseDTO>();
		for(User d : list) {
			dto.add(new UserCardResponseDTO(d));
		}
		return dto;
	}
	
}
