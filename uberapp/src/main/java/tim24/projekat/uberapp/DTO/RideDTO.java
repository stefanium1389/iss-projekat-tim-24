package tim24.projekat.uberapp.DTO;

import java.util.ArrayList;
import java.util.List;

public class RideDTO {
	
	private Long id;
	private String startTime;
	private String endTime;
	private int totalCost;
	private UserRef driver;
	private List<UserRef> passengers;
	private int estimatedTimeInMinutes;
	private String vehicleType;
	private boolean babyTransport;
	private boolean petTransport;
	
	public RideDTO(Long id, String startTime, String endTime, int totalCost, UserRef driver, int estimatedTimeInMinutes,
			String vehicleType, boolean babyTransport, boolean petTransport) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.totalCost = totalCost;
		this.driver = driver;
		this.estimatedTimeInMinutes = estimatedTimeInMinutes;
		this.vehicleType = vehicleType;
		this.babyTransport = babyTransport;
		this.petTransport = petTransport;
		this.passengers = new ArrayList<UserRef>();
	}
	
	public void addPassenger(UserRef passenger) {
		this.passengers.add(passenger);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(int totalCost) {
		this.totalCost = totalCost;
	}

	public UserRef getDriver() {
		return driver;
	}

	public void setDriver(UserRef driver) {
		this.driver = driver;
	}

	public List<UserRef> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<UserRef> passengers) {
		this.passengers = passengers;
	}

	public int getEstimatedTimeInMinutes() {
		return estimatedTimeInMinutes;
	}

	public void setEstimatedTimeInMinutes(int estimatedTimeInMinutes) {
		this.estimatedTimeInMinutes = estimatedTimeInMinutes;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public boolean isBabyTransport() {
		return babyTransport;
	}

	public void setBabyTransport(boolean babyTransport) {
		this.babyTransport = babyTransport;
	}

	public boolean isPetTransport() {
		return petTransport;
	}

	public void setPetTransport(boolean petTransport) {
		this.petTransport = petTransport;
	}
	
	
}
