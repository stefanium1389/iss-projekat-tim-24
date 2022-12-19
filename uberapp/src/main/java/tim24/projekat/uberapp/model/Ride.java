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
    private Date startTime;
    private Date endTime;
    private RideStatus status;
    private boolean panic;
    private boolean babyInVehicle;
    private boolean petInVehicle;
    @OneToOne
    private User driver;
    @OneToOne
    private Refusal refusal;
    @OneToMany
    private ArrayList<User> passengers;    

	public Ride()
	{
		super();
	}

	public Ride(Long id, Date startTime, Date endTime, RideStatus status, boolean panic, boolean babyInVehicle,
			boolean petInVehicle, User driver, Refusal refusal, ArrayList<User> passengers)
	{
		super();
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
		this.panic = panic;
		this.babyInVehicle = babyInVehicle;
		this.petInVehicle = petInVehicle;
		this.driver = driver;
		this.refusal = refusal;
		this.passengers = passengers;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date start) {
		this.startTime = start;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date end) {
		this.endTime = end;
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
	public Refusal getRefusal() {
		return refusal;
	}
	public void setRefusal(Refusal refusal) {
		this.refusal = refusal;
	}
	public ArrayList<User> getPassengers() {
		return passengers;
	}
	public void setPassengers(ArrayList<User> passengers) {
		this.passengers = passengers;
	}
    
}
