package tim24.projekat.uberapp.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tim24.projekat.uberapp.model.User;


public interface UserRepository extends JpaRepository<User, Long> {

	void deleteUserById(Long id);

	Optional<User> findUserById(Long id);

}
