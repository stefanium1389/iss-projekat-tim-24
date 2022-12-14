package tim24.projekat.uberapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
import tim24.projekat.uberapp.DTO.UserRef;
import tim24.projekat.uberapp.DTO.UserResponseDTO;
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
	
	@GetMapping ("/all")
	public ResponseEntity<List<User>> getAllUsers ()
	{
		List<User> list = UserService.findAllUsers();
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@GetMapping ("/find/{id}")
	public ResponseEntity<User> getUserById (@PathVariable("id") Long id)
	{
		
		User emp = UserService.findUserById(id);
		return new ResponseEntity<>(emp,HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<User> addUser (@RequestBody User User)
	{
		User User2 = UserService.addUser(User);
		return new ResponseEntity<>(User2,HttpStatus.CREATED);
	}
	
}