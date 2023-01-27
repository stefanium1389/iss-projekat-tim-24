package tim24.projekat.uberapp.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Table(name = "notifications")
@Entity
public class Notification
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;
    private String note;
    private Date date;
    private boolean read;
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;
    @OneToOne
    private User receiver;

    public Notification()
    {
        super();
    }

    public Notification(String note, Date date, boolean read, NotificationType notificationType, User receiver)
    {
        this.note = note;
        this.date = date;
        this.read = read;
        this.notificationType = notificationType;
        this.receiver = receiver;
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

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
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

    public NotificationType getNotificationType()
    {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType)
    {
        this.notificationType = notificationType;
    }

    public User getReceiver()
    {
        return receiver;
    }

    public void setReceiver(User receiver)
    {
        this.receiver = receiver;
    }
}
