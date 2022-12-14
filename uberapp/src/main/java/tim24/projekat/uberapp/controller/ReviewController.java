package tim24.projekat.uberapp.controller;

import java.util.ArrayList;
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
import tim24.projekat.uberapp.DTO.LoginRequestDTO;
import tim24.projekat.uberapp.DTO.ReviewDTO;
import tim24.projekat.uberapp.DTO.ReviewRequestDTO;
import tim24.projekat.uberapp.DTO.UserRef;
import tim24.projekat.uberapp.service.ReviewService;


@RestController
@RequestMapping("api/review")
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	//			GET
	
	@GetMapping ("/vehicle/{id}")
	public ResponseEntity<DTOList<ReviewDTO>> getVehicleReviewsById(@PathVariable("id") Long id){
		
		DTOList<ReviewDTO> dtoList = reviewService.getVehicleReviewsById(id);
		return new ResponseEntity<DTOList<ReviewDTO>>(dtoList,HttpStatus.OK);
	}
	
	@GetMapping ("/driver/{id}")
	public ResponseEntity<DTOList<ReviewDTO>> getDriverReviewsById(@PathVariable("id") Long id){
		
		DTOList<ReviewDTO> dtoList = reviewService.getDriverReviewsById(id);
		return new ResponseEntity<DTOList<ReviewDTO>>(dtoList,HttpStatus.OK);
	}
	
	@GetMapping ("/{rideId}")
	public ResponseEntity<DTOList<ReviewDTO>> GetReviewsByRide(@PathVariable("rideId") Long rideId){
	
		DTOList<ReviewDTO> dtoList = reviewService.getReviewsByRide(rideId);
		return new ResponseEntity<DTOList<ReviewDTO>>(dtoList,HttpStatus.OK);
	}
	
	
	//			POST
	
	@PostMapping ("/{rideId}/vehicle/{id}")
	public ResponseEntity<ReviewDTO> postVehicleReview (@PathVariable("id") Long id,@PathVariable("rideId") Long rideId, @RequestBody ReviewRequestDTO reviewRequestDTO )
	{
		
		
		ReviewDTO response = reviewService.postVehicleReview(id,rideId, reviewRequestDTO);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping ("/{rideId}/driver/{id}")
	public ResponseEntity<ReviewDTO> postDriverReview (@PathVariable("id") Long id,@PathVariable("rideId") Long rideId, @RequestBody ReviewRequestDTO reviewRequestDTO)
	{
		
		
		ReviewDTO response = reviewService.postDriverReview(id,rideId,reviewRequestDTO);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	
}