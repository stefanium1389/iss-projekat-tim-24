package tim24.projekat.uberapp.DTO;

public class VehicleRequestDTO {
	private String vehicleType;
	private String model;
	private String licenseNumber;
	private GeoCoordinateDTO currentLocation;
	private int passengerSeats;
	private boolean babyTransport;
	private boolean petTransport;
	
	public VehicleRequestDTO(String vehicleType, String model, String licenseNumber, GeoCoordinateDTO currentLocation,
			int passengerSeats, boolean babyTransport, boolean petTransport) {
		super();
		this.vehicleType = vehicleType;
		this.model = model;
		this.licenseNumber = licenseNumber;
		this.currentLocation = currentLocation;
		this.passengerSeats = passengerSeats;
		this.babyTransport = babyTransport;
		this.petTransport = petTransport;
	}
	
	public VehicleRequestDTO() {
		super();
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public GeoCoordinateDTO getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(GeoCoordinateDTO currentLocation) {
		this.currentLocation = currentLocation;
	}

	public int getPassengerSeats() {
		return passengerSeats;
	}

	public void setPassengerSeats(int passengerSeats) {
		this.passengerSeats = passengerSeats;
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
