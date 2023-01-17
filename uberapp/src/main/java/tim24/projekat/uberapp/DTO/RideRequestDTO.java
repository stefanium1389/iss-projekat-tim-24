package tim24.projekat.uberapp.DTO;

import java.util.List;

public class RideRequestDTO {
	
	private List<RouteDTO> locations;
	private List<UserRef> passengers;
	private String vehicleType;
	private boolean babyTransport;
	private boolean petTransport;
	private String scheduledTime;
	

	public RideRequestDTO() {
		super();
	}


	public RideRequestDTO(List<RouteDTO> locations, List<UserRef> passengers, String vehicleType, boolean babyTransport,
			boolean petTransport, String scheduledTime) {
		super();
		this.locations = locations;
		this.passengers = passengers;
		this.vehicleType = vehicleType;
		this.babyTransport = babyTransport;
		this.petTransport = petTransport;
		this.scheduledTime = scheduledTime;
	}


	public List<RouteDTO> getLocations() {
		return locations;
	}


	public void setLocations(List<RouteDTO> locations) {
		this.locations = locations;
	}


	public List<UserRef> getPassengers() {
		return passengers;
	}


	public void setPassengers(List<UserRef> passengers) {
		this.passengers = passengers;
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


	public String getScheduledTime() {
		return scheduledTime;
	}


	public void setScheduledTime(String scheduledTime) {
		this.scheduledTime = scheduledTime;
	}
	

}
