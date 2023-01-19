package tim24.projekat.uberapp.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tim24.projekat.uberapp.model.Ride;
import tim24.projekat.uberapp.model.User;

import java.util.Date;
import java.util.List;
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
	
	@Query("SELECT DISTINCT r.driver FROM Ride r WHERE r.status NOT IN ('PENDING', 'ACCEPTED', 'STARTED')")
    List<User> findDriversWithRidesNotActive();
	
	@Query("SELECT r FROM Ride r WHERE r.driver.id = ?1 AND r.scheduledTime IS NOT NULL AND r.scheduledTime > CURRENT_TIMESTAMP ORDER BY r.scheduledTime ASC")
	Optional<List<Ride>> findPendingScheduledRidesByDriverId(@Param("driverId") Long driverId);

	@Query(value = "SELECT r FROM Ride r JOIN r.driver d WHERE d.id = :driverId AND r.startTime BETWEEN :startDate AND :endDate")
	Page<Ride> findByDriverIdAndRideDateBetween(@Param("driverId") Long driverId, @Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);

	@Query("SELECT r FROM Ride r JOIN r.passengers p WHERE p.id = :passengerId AND r.startTime BETWEEN :startDate AND :endDate")
	Page<Ride> findByPassengerIdAndStartTimeBetween(@Param("passengerId") Long passengerId, @Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);
	
	@Query("SELECT r FROM Ride r WHERE (r.driver.id = :id OR :id MEMBER OF r.passengers) AND r.startTime BETWEEN :startDate AND :endDate")
    Page<Ride> findAllByDriverIdOrPassengerIdAndStartTimeBetween(@Param("id") Long id, @Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);
	
	@Query(value = "SELECT r FROM Ride r WHERE r.status != 'CANCELED' AND r.scheduledTime BETWEEN :startDate AND :endDate AND r.driver IS NULL")
	List<Ride> findScheduledRidesWithoutDriverInTimePeriod(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
