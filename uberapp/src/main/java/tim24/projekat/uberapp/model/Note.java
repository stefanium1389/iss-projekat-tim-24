package tim24.projekat.uberapp.model;

import jakarta.persistence.*;

import java.util.Date;

@Table(name = "notes")
@Entity
public class Note
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;
    private Date time;
    private String note;
    @OneToOne
    private User user;

    public Note()
    {
        super();
    }
    public Note(Date time, String note, User user)
    {
        super();
        this.time = time;
        this.note = note;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
