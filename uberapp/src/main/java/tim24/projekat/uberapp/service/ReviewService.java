package tim24.projekat.uberapp.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import tim24.projekat.uberapp.DTO.DTOList;
import tim24.projekat.uberapp.DTO.ReviewDTO;
import tim24.projekat.uberapp.DTO.ReviewRequestDTO;
import tim24.projekat.uberapp.exception.InvalidArgumentException;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.model.Review;
import tim24.projekat.uberapp.model.ReviewDriver;
import tim24.projekat.uberapp.model.ReviewVehicle;
import tim24.projekat.uberapp.model.Ride;
import tim24.projekat.uberapp.model.Role;
import tim24.projekat.uberapp.model.User;
import tim24.projekat.uberapp.model.Vehicle;
import tim24.projekat.uberapp.repo.ReviewRepository;
import tim24.projekat.uberapp.repo.RideRepository;
import tim24.projekat.uberapp.repo.UserRepository;
import tim24.projekat.uberapp.repo.VehicleRepository;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepo;
	@Autowired
	private VehicleRepository vehicleRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RideRepository rideRepo;

	public DTOList<ReviewDTO> getVehicleReviewsById(Long id) {
		
		Optional<Vehicle> vOpt = vehicleRepo.findById(id);
		if(vOpt.isEmpty()) {
			throw new ObjectNotFoundException("Vehicle does not exist!");
		}
		List<ReviewVehicle> rvList= reviewRepo.findByVehicleId(id);
		DTOList<ReviewDTO> dto = new DTOList<ReviewDTO>();
		for(ReviewVehicle rv : rvList) {
			dto.add(new ReviewDTO(rv));
		}
		return dto;
	}

	public DTOList<ReviewDTO> getDriverReviewsById(Long id) {
		Optional<User> dOpt = userRepo.findByIdAndRole(id, Role.DRIVER);
		if(dOpt.isEmpty()) {
			throw new ObjectNotFoundException("Driver does not exist!");
		}
		List<ReviewDriver> rdList = reviewRepo.findByDriverId(id);
		DTOList<ReviewDTO> dto = new DTOList<ReviewDTO>();
		for(ReviewDriver rd : rdList) {
			dto.add(new ReviewDTO(rd));
		}
		return dto;
	}

	public DTOList<ReviewDTO> getReviewsByRide(Long id) {
		Optional<Ride> rOpt = rideRepo.findById(id);
		if(rOpt.isEmpty()) {
			throw new ObjectNotFoundException("Vehicle does not exist!");
		}
		List<Review> rList= reviewRepo.findByRideId(id);
		DTOList<ReviewDTO> dto = new DTOList<ReviewDTO>();
		for(Review r : rList) {
			dto.add(new ReviewDTO(r));
		}
		return dto;
	}

	public ReviewDTO postVehicleReview(String userMail, Long rideId, ReviewRequestDTO reviewRequestDTO) {
		User commenter = userRepo.findUserByEmail(userMail).get();
		Optional<Ride> rOpt = rideRepo.findById(rideId);
		if(rOpt.isEmpty()) {
			throw new ObjectNotFoundException("Ride does not exist!");
		}
		Optional<Vehicle> v = vehicleRepo.findVehicleByDriverId(rOpt.get().getDriver().getId());
		ReviewVehicle rv = new ReviewVehicle();
		rv.setRide(rOpt.get());
		rv.setComment(reviewRequestDTO.getComment());
		rv.setGrade(reviewRequestDTO.getRating());
		rv.setVehicle(v.get());
		rv.setDate(new Date(System.currentTimeMillis()));
		rv.setCommenter(commenter);
		
		try {
			reviewRepo.save(rv);
			reviewRepo.flush();
		}
		catch(DataIntegrityViolationException e) {
			throw new InvalidArgumentException("Only one comment per user per ride!");
		}
		
		return new ReviewDTO(rv);
	
	}

	public ReviewDTO postDriverReview(String userMail, Long rideId, ReviewRequestDTO reviewRequestDTO) {
		User commenter = userRepo.findUserByEmail(userMail).get();
		Optional<Ride> rOpt = rideRepo.findById(rideId);
		if(rOpt.isEmpty()) {
			throw new ObjectNotFoundException("Ride does not exist!");
		}
		User d = rOpt.get().getDriver();
		ReviewDriver rv = new ReviewDriver();
		rv.setRide(rOpt.get());
		rv.setComment(reviewRequestDTO.getComment());
		rv.setGrade(reviewRequestDTO.getRating());
		rv.setDriver(d);
		rv.setDate(new Date(System.currentTimeMillis()));
		rv.setCommenter(commenter);
		
		try {
			reviewRepo.save(rv);
			reviewRepo.flush();
		}
		catch(DataIntegrityViolationException e) {
			throw new InvalidArgumentException("Only one comment per user per ride!");
		}
		
		return new ReviewDTO(rv);
	}
	
	
	
}
