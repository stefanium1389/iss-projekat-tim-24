package tim24.projekat.uberapp.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tim24.projekat.uberapp.model.User;
import tim24.projekat.uberapp.model.VehicleDetails;


public interface VehicleDetailsRepository extends JpaRepository<VehicleDetails, Long> {

}
