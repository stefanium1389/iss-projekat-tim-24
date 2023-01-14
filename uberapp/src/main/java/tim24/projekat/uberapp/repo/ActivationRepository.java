package tim24.projekat.uberapp.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tim24.projekat.uberapp.model.Activation;

public interface ActivationRepository extends JpaRepository<Activation, Long>{

	Optional<Activation> findActivationByToken(String token);

}
