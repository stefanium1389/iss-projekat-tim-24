package tim24.projekat.uberapp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tim24.projekat.uberapp.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
	
	List<Message> findAllBySenderId(Long id);
	List<Message> findAllByRecieverId(Long id);

}
