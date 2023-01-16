package tim24.projekat.uberapp.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private Date scheduledTime;
    
    @Enumerated(EnumType.STRING)
    private RideStatus status;
    
    private boolean panic;
    private boolean babyInVehicle;
    private boolean petInVehicle;
    @OneToOne
    private Route route;
    @OneToOne
    @JoinColumn(nullable = true)
    private User driver;
    @OneToOne
    @JoinColumn(nullable = true)
    private Refusal refusal;
    @ManyToMany
    private List<User> passengers;    

	public Ride()
	{
		super();
	}

	public Ride(Long id, Date startTime, Date endTime, RideStatus status, boolean panic, boolean babyInVehicle,
			boolean petInVehicle, User driver, Refusal refusal, List<User> passengers, Route route, Date scheduledTime)
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
		this.route = route;
		this.setScheduledTime(scheduledTime);
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
	public List<User> getPassengers() {
		return passengers;
	}
	public void setPassengers(List<User> passengers) {
		this.passengers = passengers;
	}

	public int getCost() {
		return 0;
	}

	public int getEstimatedTime() {
		return this.route.getEstimatedTimeInMinutes();
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public boolean isActive() {
		if(this.status==RideStatus.PENDING || this.status==RideStatus.ACCEPTED || this.status==RideStatus.STARTED) {
			return true;
		}
		return false;
	}

	public Date getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(Date scheduledTime) {
		this.scheduledTime = scheduledTime;
	}
    
}
