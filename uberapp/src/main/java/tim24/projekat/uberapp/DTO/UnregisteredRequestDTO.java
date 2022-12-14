package tim24.projekat.uberapp.DTO;

import java.util.List;

public class UnregisteredRequestDTO {

	private List<RouteDTO> locations;
	private String vehicleType;
	private boolean babyTransport;
	private boolean petTransport;
	
	public UnregisteredRequestDTO(List<RouteDTO> locations, String vehicleType, boolean babyTransport,
			boolean petTransport) {
		super();
		this.locations = locations;
		this.vehicleType = vehicleType;
		this.babyTransport = babyTransport;
		this.petTransport = petTransport;
	}

	public UnregisteredRequestDTO() {
		super();
	}

	public List<RouteDTO> getLocations() {
		return locations;
	}

	public void setLocations(List<RouteDTO> locations) {
		this.locations = locations;
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
