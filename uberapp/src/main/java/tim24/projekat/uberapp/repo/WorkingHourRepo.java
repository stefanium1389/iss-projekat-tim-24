package tim24.projekat.uberapp.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import tim24.projekat.uberapp.model.WorkingHour;

public interface WorkingHourRepo extends JpaRepository<WorkingHour, Long> {

}
