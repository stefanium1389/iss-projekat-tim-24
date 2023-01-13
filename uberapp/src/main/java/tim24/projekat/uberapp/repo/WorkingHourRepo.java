package tim24.projekat.uberapp.repo;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tim24.projekat.uberapp.model.User;
import tim24.projekat.uberapp.model.WorkingHour;

public interface WorkingHourRepo extends JpaRepository<WorkingHour, Long> {

	Optional<WorkingHour> findWorkingHourById(Long id);

	@Query(value = "SELECT * FROM working_hours WHERE driver_id = :id and start_time = end_time ORDER BY start_time DESC limit 1", nativeQuery = true)
	Optional<WorkingHour> findLastWorkingHourByDriverId(Long id);



	
}
