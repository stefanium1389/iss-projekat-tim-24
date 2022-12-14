package tim24.projekat.uberapp.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import tim24.projekat.uberapp.model.Vehicle;


public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

}
