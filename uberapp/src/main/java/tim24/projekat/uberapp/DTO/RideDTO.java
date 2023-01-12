package tim24.projekat.uberapp.DTO;

import java.util.ArrayList;
import java.util.List;

import tim24.projekat.uberapp.model.Location;
import tim24.projekat.uberapp.model.Ride;
import tim24.projekat.uberapp.model.User;

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
	private RejectionDTO rejection;
	private List<RouteDTO> locations;
	private String status;

	public RideDTO() {
		super();
	}

	public RideDTO(Long id, String startTime, String endTime, int totalCost, UserRef driver, List<UserRef> passengers,
			int estimatedTimeInMinutes, String vehicleType, boolean babyTransport, boolean petTransport,
			RejectionDTO rejection, List<RouteDTO> locations, String status) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.totalCost = totalCost;
		this.driver = driver;
		this.passengers = passengers;
		this.estimatedTimeInMinutes = estimatedTimeInMinutes;
		this.vehicleType = vehicleType;
		this.babyTransport = babyTransport;
		this.petTransport = petTransport;
		this.rejection = rejection;
		this.locations = locations;
		this.status = status;
	}

	public RideDTO(Ride ride) {
		super();
		this.id = ride.getId();
		this.startTime = ride.getStartTime().toString();
		this.endTime = ride.getEndTime().toString();
		this.totalCost = ride.getCost();
		this.driver = new UserRef(ride.getDriver());
		ArrayList<UserRef> dtoPassengers = new ArrayList<UserRef>();
		for(User passenger : ride.getPassengers()) {
			dtoPassengers.add(new UserRef(passenger));
		}
		this.passengers = dtoPassengers;
		this.estimatedTimeInMinutes = ride.getEstimatedTime();
		this.vehicleType = ride.getVehicleType();
		this.babyTransport = ride.isBabyInVehicle();
		this.petTransport = ride.isPetInVehicle();
		this.rejection = new RejectionDTO (ride.getRefusal());
		List<RouteDTO> routes = new ArrayList<RouteDTO>();
		routes.add(new RouteDTO(ride.getRoute()));
		this.locations = routes;
		this.status = ride.getStatus().toString();
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

	public void setPassengers(List<UserRef> passengers) {
		this.passengers = passengers;
	}

	public List<UserRef> getPassengers() {
		return passengers;
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

	public RejectionDTO getRejection() {
		return rejection;
	}

	public void setRejection(RejectionDTO rejection) {
		this.rejection = rejection;
	}

	public List<RouteDTO> getLocations() {
		return locations;
	}

	public void setLocations(List<RouteDTO> locations) {
		this.locations = locations;
	}

	public void addPassenger(UserRef passenger) {
		this.passengers.add(passenger);
	}

	public String getStatus()
	{
		return this.status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}
}
