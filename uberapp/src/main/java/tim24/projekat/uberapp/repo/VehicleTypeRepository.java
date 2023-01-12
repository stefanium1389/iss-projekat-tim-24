package tim24.projekat.uberapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import tim24.projekat.uberapp.model.Panic;
import tim24.projekat.uberapp.model.VehicleType;

public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long>
{
    
}
