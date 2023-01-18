package tim24.projekat.uberapp.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tim24.projekat.uberapp.model.PasswordReset;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long> {

	Optional<PasswordReset> findByToken(String token);

}
