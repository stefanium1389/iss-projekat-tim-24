package tim24.projekat.uberapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tim24.projekat.uberapp.model.Ride;

import java.util.Optional;

public interface RideRepository extends JpaRepository<Ride, Long>
{
    Optional<Ride> findRideById(Long id);

	Optional<Ride> findRideByDriverId(Long id);
}
