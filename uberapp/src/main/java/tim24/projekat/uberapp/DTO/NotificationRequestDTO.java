package tim24.projekat.uberapp.DTO;

public class NotificationRequestDTO
{
    private Long userId;
    private String notification;
    private String notificationType;

    public NotificationRequestDTO()
    {
        super();
    }

    public NotificationRequestDTO(Long userId, String notification, String notificationType)
    {
        super();
        this.userId = userId;
        this.notification = notification;
        this.notificationType = notificationType;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public String getNotification()
    {
        return notification;
    }

    public void setNotification(String notification)
    {
        this.notification = notification;
    }

    public String getNotificationType()
    {
        return notificationType;
    }

    public void setNotificationType(String notificationType)
    {
        this.notificationType = notificationType;
    }
}
