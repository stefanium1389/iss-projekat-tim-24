package tim24.projekat.uberapp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tim24.projekat.uberapp.model.Review;
import tim24.projekat.uberapp.model.ReviewVehicle;
import tim24.projekat.uberapp.model.ReviewDriver;


public interface ReviewRepository extends JpaRepository<Review, Long> {
	
	List<ReviewVehicle> findByVehicleId(Long vehicleId);

	List<ReviewDriver> findByDriverId(Long driverId);

	List<Review> findByRideId(Long id);
}
