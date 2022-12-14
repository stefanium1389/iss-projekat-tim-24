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
    
	public Ride(Long id, Date start, Date end, RideStatus status, boolean panic, boolean babyInVehicle,
			boolean petInVehicle, User driver, Refusal rafusal, ArrayList<User> passengers) {
		super();
		this.id = id;
		this.start = start;
		this.end = end;
		this.status = status;
		this.panic = panic;
		this.babyInVehicle = babyInVehicle;
		this.petInVehicle = petInVehicle;
		this.driver = driver;
		this.rafusal = rafusal;
		this.passengers = passengers;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public RideStatus getStatus() {
		return status;
	}
	public void setStatus(RideStatus status) {
		this.status = status;
	}
	public boolean isPanic() {
		return panic;
	}
	public void setPanic(boolean panic) {
		this.panic = panic;
	}
	public boolean isBabyInVehicle() {
		return babyInVehicle;
	}
	public void setBabyInVehicle(boolean babyInVehicle) {
		this.babyInVehicle = babyInVehicle;
	}
	public boolean isPetInVehicle() {
		return petInVehicle;
	}
	public void setPetInVehicle(boolean petInVehicle) {
		this.petInVehicle = petInVehicle;
	}
	public User getDriver() {
		return driver;
	}
	public void setDriver(User driver) {
		this.driver = driver;
	}
	public Refusal getRafusal() {
		return rafusal;
	}
	public void setRafusal(Refusal rafusal) {
		this.rafusal = rafusal;
	}
	public ArrayList<User> getPassengers() {
		return passengers;
	}
	public void setPassengers(ArrayList<User> passengers) {
		this.passengers = passengers;
	}
    
}
