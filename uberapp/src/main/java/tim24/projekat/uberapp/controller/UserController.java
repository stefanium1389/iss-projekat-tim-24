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
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("api/")
public class UserController {

	@Autowired
	private UserService userService;
	
	//			GET
	
	@GetMapping ("user/{id}/ride")
	public ResponseEntity<DTOList<RideDTO>> getUserRidesById(
			@PathVariable("id") Long id,
			@RequestParam("page") int page, 
			@RequestParam("size") int size,
			@RequestParam("sort") String sort, 
			@RequestParam("from") String from,
			@RequestParam("to") String to)
	{
		
		DTOList<RideDTO> dtoList = userService.getUserRidesById(id,page,size,sort,from,to);
		return new ResponseEntity<DTOList<RideDTO>>(dtoList,HttpStatus.OK);
	}
	
	@GetMapping ("user")
	public ResponseEntity<DTOList<UserResponseDTO>> getUsers (
			@RequestParam("page") int page, 
			@RequestParam("size") int size)
	{
		
		DTOList<UserResponseDTO> dtoList = userService.getUsers(page,size);
		return new ResponseEntity<>(dtoList,HttpStatus.OK);
	}
	
	@GetMapping ("user/{id}/message")
	public ResponseEntity<DTOList<MessageDTO>> getUserMessagesById (@PathVariable("id") Long id)
	{
		
		
		DTOList<MessageDTO> dtoList = userService.getUserMessagesById(id);
		return new ResponseEntity<>(dtoList,HttpStatus.OK);
	}
	
	@GetMapping ("user/{id}/note")
	public ResponseEntity<DTOList<NoteDTO>> getUserNotesById (
			@PathVariable("id") Long id,
			@RequestParam("page") int page, 
			@RequestParam("size") int size)
	{
		
		DTOList<NoteDTO> dtoList = userService.getUserNotesById(id,page,size);
		return new ResponseEntity<>(dtoList,HttpStatus.OK);
	}
	
	//			POST
	
	@PostMapping ("user/login")
	public ResponseEntity<LoginResponseDTO> postLogin (@RequestBody LoginRequestDTO loginRequestDTO)
	{
		
		
		LoginResponseDTO response = userService.postLogin(loginRequestDTO);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping ("user/{id}/message")
	public ResponseEntity<MessageSendResponseDTO> postMessageById (@PathVariable("id") Long id, @RequestBody MessageRequestDTO messageRequestDTO)
	{
		
		
		MessageSendResponseDTO m = userService.postMessageById(id, messageRequestDTO);
		return new ResponseEntity<>(m,HttpStatus.OK);
	}
	
	@PostMapping ("user/{id}/note")
	public ResponseEntity<NoteResponseDTO> postNoteById (@PathVariable("id") Long id, @RequestBody NoteRequestDTO nrd)
	{
		
		
		NoteResponseDTO response = userService.postNoteById(id, nrd);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping ("unregisteredUser/")
	public ResponseEntity<UnregisteredResponseDTO> postUnregistered ( @RequestBody UnregisteredRequestDTO urd)
	{
		
		
		UnregisteredResponseDTO u = userService.postUnregistered(urd);
		return new ResponseEntity<>(u,HttpStatus.OK);
	}
	
	//			PUT
	
	@PutMapping ("user/{id}/block")
	public ResponseEntity<User> putBlockUserById (@PathVariable("id") Long id) 
	{
		
		userService.putBlockUserById(id); // izmeni ovo
		return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
	}
	
	@PutMapping ("user/{id}/unblock")
	public ResponseEntity<User> putUnblockUserById (@PathVariable("id") Long id)
	{
		userService.putUnblockUserById(id); //izmeni ovo
		return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
	}
	
	
}