package tim24.projekat.uberapp.repo;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tim24.projekat.uberapp.model.Vehicle;


public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
	
	Optional<Vehicle> findVehicleByDriverId(long id);
	
	@Query("SELECT DISTINCT v FROM Vehicle v JOIN v.driver d JOIN WorkingHour wh ON wh.driver = d WHERE wh.startTime = wh.endTime")
	List<Vehicle> findDistinctVehiclesWithActiveWorkingHours();
	
}
