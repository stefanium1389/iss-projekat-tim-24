package tim24.projekat.uberapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim24.projekat.uberapp.DTO.DTOList;
import tim24.projekat.uberapp.DTO.ErrorDTO;
import tim24.projekat.uberapp.DTO.ReviewDTO;
import tim24.projekat.uberapp.DTO.ReviewRequestDTO;
import tim24.projekat.uberapp.exception.InvalidArgumentException;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.security.JwtTokenUtil;
import tim24.projekat.uberapp.service.ReviewService;


@RestController
@RequestMapping("api/review")
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	//			GET
	
	@GetMapping ("/vehicle/{id}")
	public ResponseEntity<?> getVehicleReviewsById(@PathVariable("id") Long id){
		try {
			DTOList<ReviewDTO> dtoList = reviewService.getVehicleReviewsById(id);
			return new ResponseEntity<DTOList<ReviewDTO>>(dtoList,HttpStatus.OK);
		}
		catch(ObjectNotFoundException e) {
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error,HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping ("/driver/{id}")
	public ResponseEntity<?> getDriverReviewsById(@PathVariable("id") Long id){
		try {
			DTOList<ReviewDTO> dtoList = reviewService.getDriverReviewsById(id);
			return new ResponseEntity<DTOList<ReviewDTO>>(dtoList,HttpStatus.OK);
		}
		catch(ObjectNotFoundException e) {
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error,HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping ("/{rideId}")
	public ResponseEntity<?> GetReviewsByRide(@PathVariable("rideId") Long rideId){
		try {
		DTOList<ReviewDTO> dtoList = reviewService.getReviewsByRide(rideId);
		return new ResponseEntity<DTOList<ReviewDTO>>(dtoList,HttpStatus.OK);
		}
		catch(ObjectNotFoundException e) {
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error,HttpStatus.NOT_FOUND);
		}
	}
	
	
	//			POST
	
	@PostMapping ("/{rideId}/vehicle")
	public ResponseEntity<?> postVehicleReview (@RequestHeader("Authorization") String auth, @PathVariable("rideId") Long rideId, @RequestBody ReviewRequestDTO reviewRequestDTO )
	{
		try {
		String userMail = jwtTokenUtil.getUsernameFromToken(auth.substring(7)); //"Bearer "
		ReviewDTO response = reviewService.postVehicleReview(userMail, rideId, reviewRequestDTO);
		return new ResponseEntity<>(response,HttpStatus.OK);
		}
		catch(ObjectNotFoundException e) {
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error,HttpStatus.NOT_FOUND);
		}
		catch(InvalidArgumentException e) {
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping ("/{rideId}/driver")
	public ResponseEntity<?> postDriverReview (@RequestHeader("Authorization") String auth, @PathVariable("rideId") Long rideId, @RequestBody ReviewRequestDTO reviewRequestDTO)
	{
		try {
		String userMail = jwtTokenUtil.getUsernameFromToken(auth.substring(7)); //"Bearer "
		ReviewDTO response = reviewService.postDriverReview(userMail,rideId,reviewRequestDTO);
		return new ResponseEntity<>(response,HttpStatus.OK);
		}
		catch(ObjectNotFoundException e) {
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error,HttpStatus.NOT_FOUND);
		}
		catch(InvalidArgumentException e) {
			ErrorDTO error = new ErrorDTO(e.getMessage());
			return new ResponseEntity<ErrorDTO>(error,HttpStatus.BAD_REQUEST);
		}
	}
	
	
}