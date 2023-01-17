package tim24.projekat.uberapp.DTO;

import java.util.ArrayList;
import java.util.List;

import tim24.projekat.uberapp.model.FavoriteRide;

public class FavoriteRideResponseDTO {
	
	private Long id;
	private String favoriteName;
	private List<RouteDTO> locations;
	private List<UserRef> passengers;
	private String vehicleType;
	private boolean babyTransport;
	private boolean petTransport;
	
	public FavoriteRideResponseDTO() {
		super();
	}
	public FavoriteRideResponseDTO(Long id, String favoriteName, List<RouteDTO> locations, List<UserRef> passengers, String vehicleType,
			boolean babyTransport, boolean petTransport) {
		super();
		this.id = id;
		this.favoriteName = favoriteName;
		this.locations = locations;
		this.passengers = passengers;
		this.vehicleType = vehicleType;
		this.babyTransport = babyTransport;
		this.petTransport = petTransport;
	}
	public FavoriteRideResponseDTO(FavoriteRide favRide) {
		this.id = favRide.getId();
		this.favoriteName = favRide.getFavoriteName();
		this.locations = new ArrayList<RouteDTO>();
		this.locations.add(new RouteDTO(favRide.getRoute()));
		this.passengers = new ArrayList<UserRef>();
		this.passengers.add(new UserRef(favRide.getPassenger()));
		this.vehicleType = favRide.getVehicleType();
		this.babyTransport = favRide.isBabyInVehicle();
		this.petTransport = favRide.isPetInVehicle();
	}
	public String getFavoriteName() {
		return favoriteName;
	}
	public void setFavoriteName(String favoriteName) {
		this.favoriteName = favoriteName;
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	

}
