package tim24.projekat.uberapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import tim24.projekat.uberapp.model.Review;


public interface ReviewRepository extends JpaRepository<Review, Long> {

}
