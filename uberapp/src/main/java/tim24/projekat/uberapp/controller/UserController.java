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
import tim24.projekat.uberapp.DTO.UserResponseDTO;
import tim24.projekat.uberapp.DTO.UserRef;
import tim24.projekat.uberapp.DTO.UserRequestDTO;
import tim24.projekat.uberapp.model.User;
import tim24.projekat.uberapp.service.UserService;


@RestController
@RequestMapping("api/")
public class UserController {
	private final UserService UserService;

	@Autowired
	public UserController(UserService UserService) {
		super();
		this.UserService = UserService;
	}
	
	//			GET
	
	@GetMapping ("user/{id}/ride")
	public ResponseEntity<DTOList<RideDTO>> getUserRidesById(@PathVariable("id") Long id){
		List<RideDTO> rides = new ArrayList<RideDTO>();
		RejectionDTO rej = new RejectionDTO ("neki razlog","datummm");
		GeoCoordinateDTO gcd1 = new GeoCoordinateDTO ("adresa1",123,321);
		GeoCoordinateDTO gcd2 = new GeoCoordinateDTO ("adresa2",424,572);
		
		ArrayList<RouteDTO> routes = new ArrayList<RouteDTO>();
		routes.add(new RouteDTO(gcd1,gcd2));
		
		ArrayList<UserRef> passengers = new ArrayList<UserRef>();
		passengers.add(new UserRef(1L, "mailic@mail.com"));
		
		RideDTO r = new RideDTO(300L, "18:44", "19:30", 123,new UserRef(2L, "mailicXD@mail.com"),passengers,40,"tip",false,true,rej,routes);
		rides.add(r);
		DTOList<RideDTO> dtoList = new DTOList<RideDTO>(rides.size(), rides);
		return new ResponseEntity<DTOList<RideDTO>>(dtoList,HttpStatus.OK);
	}
	
	@GetMapping ("user")
	public ResponseEntity<DTOList<UserResponseDTO>> getUsers ()
	{
		ArrayList<UserResponseDTO> list = new ArrayList<UserResponseDTO>();
		UserResponseDTO u1 = new UserResponseDTO (1L,"vladimir","golosin","url","123213","mail","adresa");
		UserResponseDTO u2 = new UserResponseDTO (2L,"hladimir","golosin","url","565656","mail","adresa");
		list.add(u1);
		list.add(u2);
		DTOList<UserResponseDTO> dtoList = new DTOList<UserResponseDTO>(list.size(),list);
		
		return new ResponseEntity<>(dtoList,HttpStatus.OK);
	}
	
	@GetMapping ("user/{id}/message")
	public ResponseEntity<DTOList<MessageDTO>> getUserMessagesById (@PathVariable("id") Long id)
	{
		
		ArrayList<MessageDTO> list = new ArrayList<MessageDTO>();
		MessageDTO m1 = new MessageDTO(1L, LocalDateTime.now(),2L,"asdasd","tip",100L);
		list.add(m1);
		DTOList<MessageDTO> dtoList = new DTOList<MessageDTO>(list.size(),list);
		return new ResponseEntity<>(dtoList,HttpStatus.OK);
	}
	
	@GetMapping ("user/{id}/note")
	public ResponseEntity<DTOList<NoteDTO>> getUserNotesById (@PathVariable("id") Long id)
	{
		
		ArrayList<NoteDTO> list = new ArrayList<NoteDTO>();
		NoteDTO n1 = new NoteDTO(1L, LocalDateTime.now(),"asdasd");
		list.add(n1);
		DTOList<NoteDTO> dtoList = new DTOList<NoteDTO>(list.size(),list);
		return new ResponseEntity<>(dtoList,HttpStatus.OK);
	}
	
	//			POST
	
	@PostMapping ("user/login")
	public ResponseEntity<LoginResponseDTO> postLogin (@RequestBody LoginRequestDTO loginRequestDTO)
	{
		
		LoginResponseDTO response = new LoginResponseDTO("asdasd","ffgdfgfdgfd");
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping ("user/{id}/message")
	public ResponseEntity<MessageSendResponseDTO> postMessageById (@PathVariable("id") Long id, @RequestBody MessageRequestDTO messageRequestDTO)
	{
		
		MessageSendResponseDTO m = new MessageSendResponseDTO(1L, LocalDateTime.now(),4L, 10L,"asdasdsa","tip",101L);
		return new ResponseEntity<>(m,HttpStatus.OK);
	}
	
	@PostMapping ("user/{id}/note")
	public ResponseEntity<NoteResponseDTO> postNoteById (@PathVariable("id") Long id, @RequestBody NoteRequestDTO nrd)
	{
		
		NoteResponseDTO response = new NoteResponseDTO (101L, LocalDateTime.now(), nrd.getMessage());
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping ("unregisteredUser")
	public ResponseEntity<UnregisteredResponseDTO> postUnregistered ( @RequestBody UnregisteredRequestDTO urd)
	{
		
		UnregisteredResponseDTO u = new UnregisteredResponseDTO(10L, 100L);
		return new ResponseEntity<>(u,HttpStatus.OK);
	}
	
	//			PUT
	
	@PutMapping ("user/{id}/block")
	public ResponseEntity<User> putBlockUserById (@PathVariable("id") Long id) 
	{
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
	}
	
	@PutMapping ("user/{id}/unblock")
	public ResponseEntity<User> putUnblockUserById (@PathVariable("id") Long id)
{
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
	}
	
	
}