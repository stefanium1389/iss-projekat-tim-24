package tim24.projekat.uberapp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tim24.projekat.uberapp.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
	
	List<Message> findAllBySenderId(Long id);
	List<Message> findAllByRecieverId(Long id);
	List<Message> findAllByRideId(Long id);
	
	@Query("SELECT m FROM Message m WHERE (m.sender.id = :id1 AND m.reciever.id = :id2 OR m.sender.id = :id2 AND m.reciever.id = :id1) AND m.ride.id = :rideid")
    List<Message> findBySenderAndRecipientAndRide(Long id1, Long id2, Long rideid);
	
	@Query("SELECT m FROM Message m WHERE (m.sender.id = :id1 AND m.reciever.id = :id2 OR m.sender.id = :id2 AND m.reciever.id = :id1)")
    List<Message> findBySenderAndRecipient(Long id1, Long id2);

}
