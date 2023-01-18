package tim24.projekat.uberapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tim24.projekat.uberapp.model.Notification;

public interface NotificationRepository  extends JpaRepository<Notification, Long>
{

}
