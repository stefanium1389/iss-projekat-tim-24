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
	public ResponseEntity<DTOList<ReviewDTO>> GetVehicleReviewsById(@PathVariable("id") Long id){
		List<ReviewDTO> reviews = new ArrayList<ReviewDTO>();
		ReviewDTO r = new ReviewDTO(3L,4L,"superrr", new UserRef(123L,"mojmail@xd.com"));
		ReviewDTO r1 = new ReviewDTO(4L,2L,"nije superrr", new UserRef(321L,"mojmail2@xd.com"));
		reviews.add(r);
		reviews.add(r1);
		DTOList<ReviewDTO> dtoList = new DTOList<ReviewDTO>(reviews.size(), reviews);
		return new ResponseEntity<DTOList<ReviewDTO>>(dtoList,HttpStatus.OK);
	}
	
	@GetMapping ("/driver/{id}")
	public ResponseEntity<DTOList<ReviewDTO>> GetDriverReviewsById(@PathVariable("id") Long id){
		List<ReviewDTO> reviews = new ArrayList<ReviewDTO>();
		ReviewDTO r = new ReviewDTO(3L,4L,"vozi super",new UserRef(123L,"mojmail@xd.com"));
		ReviewDTO r1 = new ReviewDTO(4L,2L,"umalo nastradali",new UserRef(222L,"mojmaillmao@xd.com"));
		reviews.add(r);
		reviews.add(r1);
		DTOList<ReviewDTO> dtoList = new DTOList<ReviewDTO>(reviews.size(), reviews);
		return new ResponseEntity<DTOList<ReviewDTO>>(dtoList,HttpStatus.OK);
	}
	
	@GetMapping ("/{rideId}")
	public ResponseEntity<DTOList<ReviewDTO>> GetReviewsByRide(@PathVariable("rideId") Long rideId){
		List<ReviewDTO> reviews = new ArrayList<ReviewDTO>();
		ReviewDTO r = new ReviewDTO(3L,4L,"iksde", new UserRef(444L,"angulumail@xd.com"));
		ReviewDTO r1 = new ReviewDTO(8L,3L,"moze i bolje braleee",new UserRef(666L,"zlimail@xd.com"));
		reviews.add(r);
		reviews.add(r1);
		DTOList<ReviewDTO> dtoList = new DTOList<ReviewDTO>(reviews.size(), reviews);
		return new ResponseEntity<DTOList<ReviewDTO>>(dtoList,HttpStatus.OK);
	}
	
	
	//			POST
	
	@PostMapping ("/{rideId}/vehicle/{id}")
	public ResponseEntity<ReviewDTO> postVehicleReview (@PathVariable("id") Long id,@PathVariable("rideId") Long rideId, @RequestBody ReviewRequestDTO reviewRequestDTO )
	{
		
		ReviewDTO response = new ReviewDTO(50L, 4L,reviewRequestDTO.getComment(),new UserRef(123L,"mojmail@xd.com"));
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping ("/{rideId}/driver/{id}")
	public ResponseEntity<ReviewDTO> postDriverReview (@PathVariable("id") Long id,@PathVariable("rideId") Long rideId, @RequestBody ReviewRequestDTO reviewRequestDTO)
	{
		
		ReviewDTO response = new ReviewDTO(50L, 4L,reviewRequestDTO.getComment(),new UserRef(123L,"mojmail@xd.com"));
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	
}