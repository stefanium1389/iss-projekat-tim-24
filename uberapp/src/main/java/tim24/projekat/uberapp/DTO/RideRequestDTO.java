package tim24.projekat.uberapp.DTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import tim24.projekat.uberapp.model.Ride;
import tim24.projekat.uberapp.model.User;

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

	public RideRequestDTO(Ride r) {
		
		super();
		List<RouteDTO> rideLocations = new ArrayList<RouteDTO>();
		RouteDTO route = new RouteDTO(r.getRoute());
		rideLocations.add(route);
		
		List<UserRef> ridePassengers = new ArrayList<UserRef>();
		for (User u : r.getPassengers()) 
		{
			ridePassengers.add(new UserRef(u));
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		String rideScheduledTime = sdf.format(r.getScheduledTime());
		
		this.locations = rideLocations;
		this.passengers = ridePassengers;
		this.vehicleType = r.getVehicleType().getTypeName();
		this.babyTransport = r.isBabyInVehicle();
		this.petTransport = r.isPetInVehicle();
		this.scheduledTime = rideScheduledTime;
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
