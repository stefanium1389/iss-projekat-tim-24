package tim24.projekat.uberapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import tim24.projekat.uberapp.DTO.ChangePasswordRequestDTO;
import tim24.projekat.uberapp.DTO.CodeAndPasswordDTO;
import tim24.projekat.uberapp.DTO.ContactDTO;
import tim24.projekat.uberapp.DTO.DTOList;
import tim24.projekat.uberapp.DTO.ErrorDTO;
import tim24.projekat.uberapp.DTO.LoginResponseDTO;
import tim24.projekat.uberapp.DTO.MessageDTO;
import tim24.projekat.uberapp.DTO.MessageRequestDTO;
import tim24.projekat.uberapp.DTO.PasswordResetRequestDTO;
import tim24.projekat.uberapp.DTO.RefreshDTO;
import tim24.projekat.uberapp.DTO.SpecificMessagesRequestIDsDTO;
import tim24.projekat.uberapp.DTO.SuccessDTO;
import tim24.projekat.uberapp.exception.ConditionNotMetException;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.security.JwtTokenUtil;
import tim24.projekat.uberapp.service.MessageService;
import tim24.projekat.uberapp.service.PasswordResetService;

@RestController
@RequestMapping("api/")
public class MiscController { 	
	@Autowired
	private PasswordResetService passwordResetService;//iz nekog Bogu nepoznatog razloga, nije hteo da injektuje ove dve komponente u UserController, pa sam ovde prebacio sta ima

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private MessageService messageService;
	
	@PostMapping("user/resetPassword")
	private ResponseEntity<?> sendResetPasswordEmail(@RequestBody PasswordResetRequestDTO dto){
		
		try {
			SuccessDTO success = passwordResetService.postPasswordReset(dto.getEmail());
			return new ResponseEntity<SuccessDTO>(success,HttpStatus.NO_CONTENT);
		}
		catch(ObjectNotFoundException e){
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error,HttpStatus.NOT_FOUND);
		}
		catch(ConditionNotMetException e) {
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error,HttpStatus.BAD_REQUEST);
		}		
	}
	
	@PutMapping("user/resetPassword")
	private ResponseEntity<?> resetPassword(@RequestBody CodeAndPasswordDTO dto){
		try {
			SuccessDTO success = passwordResetService.putPasswordReset(dto);
			return new ResponseEntity<SuccessDTO>(success,HttpStatus.NO_CONTENT);
		}
		catch(ObjectNotFoundException e){
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error,HttpStatus.NOT_FOUND);
		}
		catch(ConditionNotMetException e) {
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error,HttpStatus.BAD_REQUEST);
		}	
	}
	@PutMapping("user/{id}/changePassword")
	private ResponseEntity<?> changePassword(@PathVariable("id") Long id, @RequestBody ChangePasswordRequestDTO dto){
		
		try {
			SuccessDTO success = passwordResetService.putChangePassword(id, dto);
			return new ResponseEntity<SuccessDTO>(success,HttpStatus.NO_CONTENT);
		}
		catch(ObjectNotFoundException e){
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error,HttpStatus.NOT_FOUND);
		}
		catch(ConditionNotMetException e) {
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error,HttpStatus.BAD_REQUEST);
		}	
	}
	
	@PostMapping("refreshAccessToken")
	private ResponseEntity<?> refreshAccessToken(@RequestHeader("Authorization") String auth, @RequestBody RefreshDTO refreshDTO) {
			
		String oldAccessToken = auth.substring(7); //sklonimo "Bearer " da dobijemo samo token
		String email = jwtTokenUtil.getUsernameFromToken(oldAccessToken);
		//System.err.println(email);
		String refreshToken = refreshDTO.getRefreshToken();
		//System.err.println(jwtTokenUtil.getUsernameFromToken(refreshToken));
		if(!jwtTokenUtil.validateRefreshToken(refreshToken,email)) {
			ErrorDTO error = new ErrorDTO("Invalid refreshToken!");
			return new ResponseEntity<ErrorDTO>(error,HttpStatus.BAD_REQUEST);
		}
		String newAccessToken = jwtTokenUtil.generateToken(email);
		String newRefreshToken = jwtTokenUtil.generateRefrshToken(email);
		LoginResponseDTO dto = new LoginResponseDTO(newAccessToken,newRefreshToken);
		return new ResponseEntity<LoginResponseDTO>(dto ,HttpStatus.OK);
	}
	
	@GetMapping("user/{id}/message")
	private ResponseEntity<?> getUserMessages(@PathVariable("id") Long id)
	{
		try {
			DTOList<MessageDTO> list = messageService.getUserMessages(id);
			return new ResponseEntity<DTOList<MessageDTO>>(list, HttpStatus.OK);
		}
		catch(ObjectNotFoundException e){
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error,HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping("contacts/{id}")
	private ResponseEntity<?> getUserContacts(@PathVariable("id") Long id, @RequestParam("sort") String sort, @RequestParam("filter") String filter, @RequestParam("keyword") String keyword)
	{
		try {
			DTOList<ContactDTO> list = messageService.getUserContacts(id,sort,filter, keyword);
			return new ResponseEntity<DTOList<ContactDTO>>(list, HttpStatus.OK);
		}
		catch(ObjectNotFoundException e){
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error,HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PostMapping("user/{id}/message")
	private ResponseEntity<?> postUserMessages(@RequestHeader("Authorization") String auth, @RequestBody MessageRequestDTO dto, @PathVariable("id") Long id)
	{
		System.out.println("NOVA PORUKA!!");
		try {
			String sender = jwtTokenUtil.getUsernameFromToken(auth.substring(7));
			messageService.postUserMessage(id,dto, sender);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(ObjectNotFoundException e){
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error,HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PostMapping("user/messages/specific")
	private ResponseEntity<?> getMessagesForSpecificIds(@RequestBody SpecificMessagesRequestIDsDTO dto)
	{
		
		try {
			DTOList<MessageDTO> list = messageService.getSpecificMessages(dto);
			return new ResponseEntity<DTOList<MessageDTO>>(list, HttpStatus.OK);
		}
		catch(ObjectNotFoundException e){
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error,HttpStatus.NOT_FOUND);
		}
		
	}
	
	
}
