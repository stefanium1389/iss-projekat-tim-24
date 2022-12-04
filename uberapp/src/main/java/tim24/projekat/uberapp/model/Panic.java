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
    private long id;
    private Date time;
    private String reason;
    @OneToOne
    private Ride ride;
    @OneToOne
    private User user;
}
