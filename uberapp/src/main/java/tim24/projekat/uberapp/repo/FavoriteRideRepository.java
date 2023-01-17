package tim24.projekat.uberapp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tim24.projekat.uberapp.model.FavoriteRide;

public interface FavoriteRideRepository extends JpaRepository<FavoriteRide, Long> {
	
	List<FavoriteRide> findAllFavoriteRideByPassengerId(Long id);
	
}
