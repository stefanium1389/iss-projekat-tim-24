package tim24.projekat.uberapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim24.projekat.uberapp.DTO.DTOList;
import tim24.projekat.uberapp.DTO.ReviewDTO;


@RestController
@RequestMapping("api/review/")
public class ReviewController {
	
	//			GET
	
	@GetMapping ("vehicle/{id}")
	public ResponseEntity<DTOList<ReviewDTO>> GetVehicleReviewsById(@PathVariable("id") Long id){
		List<ReviewDTO> reviews = new ArrayList<ReviewDTO>();
		ReviewDTO r = new ReviewDTO(3L,4L,"superrr");
		ReviewDTO r1 = new ReviewDTO(4L,2L,"nije superrr");
		reviews.add(r);
		reviews.add(r1);
		DTOList<ReviewDTO> dtoList = new DTOList<ReviewDTO>(reviews.size(), reviews);
		return new ResponseEntity<DTOList<ReviewDTO>>(dtoList,HttpStatus.OK);
	}
	
	@GetMapping ("driver/{id}")
	public ResponseEntity<DTOList<ReviewDTO>> GetDriverReviewsById(@PathVariable("id") Long id){
		List<ReviewDTO> reviews = new ArrayList<ReviewDTO>();
		ReviewDTO r = new ReviewDTO(3L,4L,"vozi super");
		ReviewDTO r1 = new ReviewDTO(4L,2L,"umalo nastradali");
		reviews.add(r);
		reviews.add(r1);
		DTOList<ReviewDTO> dtoList = new DTOList<ReviewDTO>(reviews.size(), reviews);
		return new ResponseEntity<DTOList<ReviewDTO>>(dtoList,HttpStatus.OK);
	}
	
	@GetMapping ("review/{rideId}")
	public ResponseEntity<DTOList<ReviewDTO>> GetReviewsByRide(@PathVariable("rideId") Long rideId){
		List<ReviewDTO> reviews = new ArrayList<ReviewDTO>();
		ReviewDTO r = new ReviewDTO(3L,4L,"iksde");
		ReviewDTO r1 = new ReviewDTO(8L,3L,"moze i bolje braleee");
		reviews.add(r);
		reviews.add(r1);
		DTOList<ReviewDTO> dtoList = new DTOList<ReviewDTO>(reviews.size(), reviews);
		return new ResponseEntity<DTOList<ReviewDTO>>(dtoList,HttpStatus.OK);
	}
	
	
	//			POST
	
	@PostMapping ("vehicle/{id}")
	public ResponseEntity<ReviewDTO> postVehicleReview (@PathVariable("id") Long id)
	{
		
		ReviewDTO response = new ReviewDTO(50L, 4L,"brm brm");
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping ("driver/{id}")
	public ResponseEntity<ReviewDTO> postDriverReview (@PathVariable("id") Long id)
	{
		
		ReviewDTO response = new ReviewDTO(50L, 4L,"hahaha");
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	
}