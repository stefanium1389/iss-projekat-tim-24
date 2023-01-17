package tim24.projekat.uberapp.model;

import jakarta.persistence.*;

import java.util.Date;

@Table(name = "panics")
@Entity
public class Panic
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;
    private Date time;
    private String reason;
    @OneToOne
    private Ride ride;
    @OneToOne
    private User user;

    public Panic()
    {
        super();
    }
    public Panic(Date time, String reason, Ride ride, User user)
    {
        super();
        this.time = time;
        this.reason = reason;
        this.ride = ride;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
