package tim24.projekat.uberapp.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tim24.projekat.uberapp.model.Panic;
import tim24.projekat.uberapp.model.User;
import tim24.projekat.uberapp.model.VehicleType;

public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long>
{
	Optional<VehicleType> findOneByTypeName(String typeName);

	Optional<VehicleType> findByTypeName(String vehicleType);
    
}
