package tim24.projekat.uberapp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tim24.projekat.uberapp.model.DriverDocument;


public interface DriverDocumentRepository extends JpaRepository<DriverDocument, Long> {
	
	List<DriverDocument> findAllByDriverId(Long id);
}
