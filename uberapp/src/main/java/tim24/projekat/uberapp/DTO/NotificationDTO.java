package tim24.projekat.uberapp.DTO;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import tim24.projekat.uberapp.model.Notification;
import tim24.projekat.uberapp.model.NotificationType;
import tim24.projekat.uberapp.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationDTO
{
    private Long id;
    private String note;
    private String date;
    private boolean read;
    private String notificationType;
    private UserRef receiver;

    public NotificationDTO()
    {
        super();
    }

    public NotificationDTO(String note, String date, boolean read, String notificationType, User receiver)
    {
        super();
        this.note = note;
        this.date = date;
        this.read = read;
        this.notificationType = notificationType;
        this.receiver = new UserRef(receiver);
    }

    public NotificationDTO(Notification notification)
    {
        super();
        this.id = notification.getId();
        this.note = notification.getNote();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String dateString = sdf.format(notification.getDate());
        this.date = dateString;
        this.read = notification.isRead();
        this.notificationType = notification.getNotificationType().toString();
        this.receiver = new UserRef(notification.getReceiver());
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public boolean isRead()
    {
        return read;
    }

    public void setRead(boolean read)
    {
        this.read = read;
    }

    public String getNotificationType()
    {
        return notificationType;
    }

    public void setNotificationType(String notificationType)
    {
        this.notificationType = notificationType;
    }

    public UserRef getReceiver()
    {
        return receiver;
    }

    public void setReceiver(UserRef receiver)
    {
        this.receiver = receiver;
    }
}
