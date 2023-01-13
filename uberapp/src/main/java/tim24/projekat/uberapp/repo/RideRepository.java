package tim24.projekat.uberapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tim24.projekat.uberapp.model.Ride;

import java.util.Optional;

public interface RideRepository extends JpaRepository<Ride, Long>
{
    Optional<Ride> findRideById(Long id);

	Optional<Ride> findRideByDriverId(Long id);

	
	
	Optional<Ride> findRideByPassengersId(Long id);
	
	@Query("SELECT r FROM Ride r JOIN r.passengers p WHERE p.id = :passengerId AND (r.status = 'PENDING' OR r.status = 'STARTED' OR r.status = 'ACCEPTED')")
    Optional<Ride> findActiveRideByPassengerId(@Param("passengerId") Long passengerId);
	
	@Query("SELECT r FROM Ride r WHERE r.driver.id = :driverId AND (r.status = 'PENDING' OR r.status = 'STARTED' OR r.status = 'ACCEPTED')")
    Optional<Ride> findActiveRideByDriverId(@Param("driverId") Long driverId);
}
