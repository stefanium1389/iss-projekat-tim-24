package tim24.projekat.uberapp.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tim24.projekat.uberapp.model.DriverUpdateDetails;
import tim24.projekat.uberapp.model.Role;
import tim24.projekat.uberapp.model.User;


public interface DriveruUpdateDetailsRepo extends JpaRepository<DriverUpdateDetails, Long> {

	
	@Query("SELECT dud FROM DriverUpdateDetails dud WHERE dud.updateState = 'PENDING' AND dud.forDriver.id = :driverId ORDER BY dud.dateOfSubmission DESC")
    Optional<DriverUpdateDetails> getLatestPendingUpdateRequest(@Param("driverId") Long driverId);
	
	Optional<DriverUpdateDetails> findDriverUpdateDetailsById(Long id);
	

}
