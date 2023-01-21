package tim24.projekat.uberapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim24.projekat.uberapp.DTO.DTOList;
import tim24.projekat.uberapp.DTO.NotificationDTO;
import tim24.projekat.uberapp.DTO.NotificationRequestDTO;
import tim24.projekat.uberapp.controller.WebSocketController;
import tim24.projekat.uberapp.exception.ConditionNotMetException;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.model.Notification;
import tim24.projekat.uberapp.model.NotificationType;
import tim24.projekat.uberapp.model.User;
import tim24.projekat.uberapp.repo.NotificationRepository;

import java.util.Date;
import java.util.List;

@Service
public class NotificationService
{
    @Autowired
    private NotificationRepository notificationRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private WebSocketController webSocketController;

    public Notification findNotificationById(Long id)
    {
        return notificationRepo.findById(id).orElseThrow(()-> new ObjectNotFoundException("Notification not found."));
    }

    public void postNotification(NotificationRequestDTO notificationRequestDTO)
    {
        String note = notificationRequestDTO.getNotification();
        Date date = new Date();
        boolean read = false;
        NotificationType notificationType = NotificationType.valueOf(notificationRequestDTO.getNotificationType());
        User receiver = userService.findUserById(notificationRequestDTO.getUserId());
        Notification notification = new Notification(note, date, read, notificationType, receiver);
        notificationRepo.save(notification);
        webSocketController.simpMessagingTemplate.convertAndSend("/notification/" + receiver.getId(), new NotificationDTO(notification));
    }

    public boolean getHasUnread(Long id)
    {
        List<Notification> notifications = notificationRepo.findAll();
        for(Notification n: notifications)
        {
            if(n.getId() == id && ! n.isRead())
                return true;
        }
        return false;
    }

    public DTOList<NotificationDTO> getNotificationsById(Long id)
    {
        DTOList<NotificationDTO> list = new DTOList<NotificationDTO>();
        List<Notification> notifications = notificationRepo.findAll();
        for(Notification n: notifications)
        {
            if(n.getId() == id)
                list.add(new NotificationDTO(n));
        }
        return list;
    }

    public void readNotification(Long id)
    {
        Notification notification = findNotificationById(id);
        if(notification.isRead())
            throw new ConditionNotMetException("Notification is already read.");
        notification.setRead(true);
        notificationRepo.save(notification);
    }
}
