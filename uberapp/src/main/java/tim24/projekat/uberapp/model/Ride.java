package tim24.projekat.uberapp.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

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
    @Column(nullable = true, updatable = true, unique = false)
    private int totalCost;
    @OneToOne
    private Route route;
    @OneToOne
    @JoinColumn(nullable = true)
    private User driver;
    @OneToOne
    @JoinColumn(nullable = true)
    private Refusal refusal;
    @ManyToMany(fetch=FetchType.EAGER)
    private List<User> passengers;
    @OneToOne
    private VehicleType vehicleType;

	public Ride()
	{
		super();
	}

	public Ride(Long id, Date startTime, Date endTime, RideStatus status, boolean panic, boolean babyInVehicle,
			boolean petInVehicle, User driver, Refusal refusal, List<User> passengers, Route route, Date scheduledTime, int totalCost, VehicleType vehicleType)
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
		this.scheduledTime = scheduledTime;
		this.totalCost = totalCost;
		this.vehicleType = vehicleType;
	}
	
	public Ride(Date startTime, Date endTime, Date scheduledTime, RideStatus status, boolean panic,
			boolean babyInVehicle, boolean petInVehicle, Route route, User driver, Refusal refusal,
			List<User> passengers , int totalCost, VehicleType vehicleType) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.scheduledTime = scheduledTime;
		this.status = status;
		this.panic = panic;
		this.babyInVehicle = babyInVehicle;
		this.petInVehicle = petInVehicle;
		this.route = route;
		this.driver = driver;
		this.refusal = refusal;
		this.passengers = passengers;
		this.totalCost = totalCost;
		this.vehicleType = vehicleType;
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
		return totalCost;
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

	public int getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(int totalCost) {
		this.totalCost = totalCost;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}
    
}
