package tim24.projekat.uberapp.repo;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tim24.projekat.uberapp.model.User;
import tim24.projekat.uberapp.model.WorkingHour;

public interface WorkingHourRepo extends JpaRepository<WorkingHour, Long> {

	Optional<WorkingHour> findWorkingHourById(Long id);
	
}
