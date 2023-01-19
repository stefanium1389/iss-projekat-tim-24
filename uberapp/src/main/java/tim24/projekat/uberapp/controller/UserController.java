package tim24.projekat.uberapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tim24.projekat.uberapp.DTO.DTOList;
import tim24.projekat.uberapp.DTO.ErrorDTO;
import tim24.projekat.uberapp.DTO.LoginRequestDTO;
import tim24.projekat.uberapp.DTO.LoginResponseDTO;
import tim24.projekat.uberapp.DTO.NoteDTO;
import tim24.projekat.uberapp.DTO.NoteRequestDTO;
import tim24.projekat.uberapp.DTO.NoteResponseDTO;
import tim24.projekat.uberapp.DTO.RideDTO;
import tim24.projekat.uberapp.DTO.UnregisteredRequestDTO;
import tim24.projekat.uberapp.DTO.UnregisteredResponseDTO;
import tim24.projekat.uberapp.DTO.UserResponseDTO;
import tim24.projekat.uberapp.exception.ConditionNotMetException;
import tim24.projekat.uberapp.exception.InvalidArgumentException;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.security.JwtTokenUtil;
import tim24.projekat.uberapp.service.RideService;
import tim24.projekat.uberapp.service.UserService;


@RestController
@RequestMapping("api/")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private RideService rideService;
	
	@PostMapping ("user/login")
	public ResponseEntity<?> postLogin (@RequestBody LoginRequestDTO loginRequestDTO)
	{
		try
		{
		UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(),
				loginRequestDTO.getPassword());
		Authentication auth = authenticationManager.authenticate(authReq);

		SecurityContext sc = SecurityContextHolder.getContext();
		sc.setAuthentication(auth);

		String token = jwtTokenUtil.generateToken(loginRequestDTO.getEmail());
		String refreshToken = jwtTokenUtil.generateRefrshToken(loginRequestDTO.getEmail());
		LoginResponseDTO response = new LoginResponseDTO(token, refreshToken);
				
		return new ResponseEntity<LoginResponseDTO>(response,HttpStatus.OK);
		}
		catch(AuthenticationException e)
		{
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error,HttpStatus.BAD_REQUEST);
		}
	}
	
	
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
	@GetMapping("user/search") //Nije po swaggeru, ovaj smo sami dodali jer nam je trebao, pretraga korisnika po imenu i e-mailu
	public ResponseEntity<DTOList<UserResponseDTO>> findUsers (
			@RequestParam("querry") String querry)
	{
		DTOList<UserResponseDTO> dtoList = userService.searchUsers(querry);
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

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping ("user/{id}/note")
	public ResponseEntity<?> postNoteById (@PathVariable("id") Long id, @RequestBody NoteRequestDTO noteDTO)
	{
		try
		{
			NoteResponseDTO response = userService.postNoteById(id, noteDTO);
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		catch(ObjectNotFoundException e)
		{
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping ("unregisteredUser/")
	public ResponseEntity<?> postUnregistered ( @RequestBody UnregisteredRequestDTO urd)
	{
		try {
			UnregisteredResponseDTO u = rideService.postUnregistered(urd);
			return new ResponseEntity<UnregisteredResponseDTO>(u,HttpStatus.OK);
		}
		catch(InvalidArgumentException e)
		{
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
		}
	}
	
	//			PUT

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping ("user/{id}/block")
	public ResponseEntity<?> putBlockUserById (@PathVariable("id") Long id)
	{
		try
		{
			userService.putBlockUserById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch(ConditionNotMetException e)
		{
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
		catch(ObjectNotFoundException e)
		{
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping ("user/{id}/unblock")
	public ResponseEntity<?> putUnblockUserById (@PathVariable("id") Long id)
	{
		try
		{
			userService.putUnblockUserById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch(ConditionNotMetException e)
		{
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
		catch(ObjectNotFoundException e)
		{
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		}
	}
}