package tim24.projekat.uberapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tim24.projekat.uberapp.model.Ride;

public interface RideRepository extends JpaRepository<Ride, Long>
{
    
}
