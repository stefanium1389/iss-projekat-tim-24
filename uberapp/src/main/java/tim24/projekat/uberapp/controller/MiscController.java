package tim24.projekat.uberapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim24.projekat.uberapp.DTO.ChangePasswordRequestDTO;
import tim24.projekat.uberapp.DTO.CodeAndPasswordDTO;
import tim24.projekat.uberapp.DTO.ErrorDTO;
import tim24.projekat.uberapp.DTO.LoginResponseDTO;
import tim24.projekat.uberapp.DTO.PasswordResetRequestDTO;
import tim24.projekat.uberapp.DTO.RefreshDTO;
import tim24.projekat.uberapp.DTO.SuccessDTO;
import tim24.projekat.uberapp.exception.ConditionNotMetException;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.security.JwtTokenUtil;
import tim24.projekat.uberapp.service.PasswordResetService;

@RestController
@RequestMapping("api/")
public class MiscController { 	
	@Autowired
	private PasswordResetService passwordResetService;//iz nekog Bogu nepoznatog razloga, nije hteo da injektuje ove dve komponente u UserController, pa sam ovde prebacio sta ima

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
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

}
