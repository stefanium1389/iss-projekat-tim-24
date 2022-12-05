package tim24.projekat.uberapp.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim24.projekat.uberapp.DTO.LoginResponseDTO;
import tim24.projekat.uberapp.DTO.MessageDTO;
import tim24.projekat.uberapp.DTO.MessageSendResponseDTO;
import tim24.projekat.uberapp.DTO.NoteDTO;
import tim24.projekat.uberapp.DTO.NoteResponseDTO;
import tim24.projekat.uberapp.DTO.RideDTO;
import tim24.projekat.uberapp.DTO.UnregisteredResponseDTO;
import tim24.projekat.uberapp.DTO.UserDTO;
import tim24.projekat.uberapp.DTO.UserRef;
import tim24.projekat.uberapp.model.User;
import tim24.projekat.uberapp.service.UserService;


@RestController
@RequestMapping("/user")
public class UserController {
	private final UserService UserService;

	@Autowired
	public UserController(UserService UserService) {
		super();
		this.UserService = UserService;
	}
	
	//			GET
	
	@GetMapping ("/api/user/{id}/ride")
	public ResponseEntity<List<RideDTO>> getUserRidesById(@PathVariable("id") Long id){
		List<RideDTO> rides = new ArrayList<RideDTO>();
		RideDTO r = new RideDTO(1L,"18.11.1991T19:30","18.11.1991T20:00",420,new UserRef(1L,"email@mail.com","VOZAC"),30,"STANDARD",false,false);
		r.addPassenger(new UserRef(1L, "mailic@mail.com","PUTNIK"));
		rides.add(r);
		return new ResponseEntity<List<RideDTO>>(rides,HttpStatus.OK);
	}
	
	@GetMapping ("/api/user")
	public ResponseEntity<List<UserDTO>> getUsers ()
	{
		ArrayList<UserDTO> list = new ArrayList<UserDTO>();
		UserDTO u1 = new UserDTO (1L,"vladimir","golosin","url","123213","mail","adresa");
		UserDTO u2 = new UserDTO (2L,"hladimir","golosin","url","565656","mail","adresa");
		list.add(u1);
		list.add(u2);
		
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@GetMapping ("/api/user/{id}/message")
	public ResponseEntity<List<MessageDTO>> getUserMessagesById (@PathVariable("id") Long id)
	{
		
		ArrayList<MessageDTO> list = new ArrayList<MessageDTO>();
		MessageDTO m1 = new MessageDTO(1L, LocalDateTime.now(),2L,"asdasd","tip",100L);
		list.add(m1);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@GetMapping ("/api/user/{id}/note")
	public ResponseEntity<List<NoteDTO>> getUserNotesById (@PathVariable("id") Long id)
	{
		
		ArrayList<NoteDTO> list = new ArrayList<NoteDTO>();
		NoteDTO n1 = new NoteDTO(1L, LocalDateTime.now(),"asdasd");
		list.add(n1);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	//			POST
	
	@PostMapping ("/api/login")
	public ResponseEntity<LoginResponseDTO> postLogin (@PathVariable("id") Long id)
	{
		
		LoginResponseDTO response = new LoginResponseDTO("asdasd","ffgdfgfdgfd");
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping ("/api/user/{id}/message")
	public ResponseEntity<MessageSendResponseDTO> postMessageById (@PathVariable("id") Long id)
	{
		
		MessageSendResponseDTO m = new MessageSendResponseDTO(1L, LocalDateTime.now(),4L, 10L,"asdasdsa","tip",101L);
		return new ResponseEntity<>(m,HttpStatus.OK);
	}
	
	@PostMapping ("/api/user/{id}/note")
	public ResponseEntity<NoteResponseDTO> postNoteById (@PathVariable("id") Long id)
	{
		
		NoteResponseDTO n = new NoteResponseDTO(6L, LocalDateTime.now(), "xdd");
		return new ResponseEntity<>(n,HttpStatus.OK);
	}
	
	@PostMapping ("/api/unregisteredUser/")
	public ResponseEntity<UnregisteredResponseDTO> postUnregistered (@PathVariable("id") Long id)
	{
		
		UnregisteredResponseDTO u = new UnregisteredResponseDTO(10L, 100L);
		return new ResponseEntity<>(u,HttpStatus.OK);
	}
	
	//			PUT
	
	@PutMapping ("/api/user/{id}/block")
	public ResponseEntity<User> putBlockUserById (@PathVariable("id") Long id) //mozda ovo nije pravi nacin lol
	{
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
	}
	
	@PutMapping ("/api/user/{id}/unblock")
	public ResponseEntity<User> putUnblockUserById (@PathVariable("id") Long id) //takodjer
{
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
	}
	
	
}