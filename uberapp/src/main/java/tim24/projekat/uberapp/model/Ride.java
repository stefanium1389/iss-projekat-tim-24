package tim24.projekat.uberapp.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;

enum RideStatus
{
    ACTIVE, FINISHED, ACCEPTED, REJECTED, WAITING
}

@Table(name = "rides")
@Entity
public class Ride
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;
    private Date start;
    private Date end;
    private RideStatus status;
    private boolean panic;
    private boolean babyInVehicle;
    private boolean petInVehicle;
    @OneToOne
    private User driver;
    @OneToOne
    private Refusal rafusal;
    @OneToMany
    private ArrayList<User> passengers;
}
