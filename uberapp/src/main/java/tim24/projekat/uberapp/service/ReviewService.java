package tim24.projekat.uberapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim24.projekat.uberapp.DTO.DTOList;
import tim24.projekat.uberapp.DTO.ReviewDTO;
import tim24.projekat.uberapp.DTO.ReviewRequestDTO;
import tim24.projekat.uberapp.DTO.UserRef;
import tim24.projekat.uberapp.repo.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepo;

	public DTOList<ReviewDTO> getVehicleReviewsById(Long id) {
		List<ReviewDTO> reviews = new ArrayList<ReviewDTO>();
		ReviewDTO r = new ReviewDTO(3L,4L,"superrr", new UserRef(123L,"mojmail@xd.com"));
		ReviewDTO r1 = new ReviewDTO(4L,2L,"nije superrr", new UserRef(321L,"mojmail2@xd.com"));
		reviews.add(r);
		reviews.add(r1);
		DTOList<ReviewDTO> dtoList = new DTOList<ReviewDTO>(reviews.size(), reviews);
		return dtoList;
	}

	public DTOList<ReviewDTO> getDriverReviewsById(Long id) {
		List<ReviewDTO> reviews = new ArrayList<ReviewDTO>();
		ReviewDTO r = new ReviewDTO(3L,4L,"vozi super",new UserRef(123L,"mojmail@xd.com"));
		ReviewDTO r1 = new ReviewDTO(4L,2L,"umalo nastradali",new UserRef(222L,"mojmaillmao@xd.com"));
		reviews.add(r);
		reviews.add(r1);
		DTOList<ReviewDTO> dtoList = new DTOList<ReviewDTO>(reviews.size(), reviews);
		return dtoList;
	}

	public DTOList<ReviewDTO> getReviewsByRide(Long rideId) {
		List<ReviewDTO> reviews = new ArrayList<ReviewDTO>();
		ReviewDTO r = new ReviewDTO(3L,4L,"iksde", new UserRef(444L,"angulumail@xd.com"));
		ReviewDTO r1 = new ReviewDTO(8L,3L,"moze i bolje braleee",new UserRef(666L,"zlimail@xd.com"));
		reviews.add(r);
		reviews.add(r1);
		DTOList<ReviewDTO> dtoList = new DTOList<ReviewDTO>(reviews.size(), reviews);
		return dtoList;
	}

	public ReviewDTO postVehicleReview(Long id, Long rideId, ReviewRequestDTO reviewRequestDTO) {
		ReviewDTO response = new ReviewDTO(50L, 4L,reviewRequestDTO.getComment(),new UserRef(123L,"mojmail@xd.com"));
		return response;
	}

	public ReviewDTO postDriverReview(Long id, Long rideId, ReviewRequestDTO reviewRequestDTO) {
		ReviewDTO response = new ReviewDTO(50L, 4L,reviewRequestDTO.getComment(),new UserRef(123L,"mojmail@xd.com"));
		return response;
	}
	
	
	
}
