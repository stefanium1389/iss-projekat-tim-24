package tim24.projekat.uberapp.DTO;

import tim24.projekat.uberapp.model.Vehicle;

public class VehicleDTO {
	private Long id;
	private Long driverId;
	private String vehicleType;
	private String model;
	private String licenseNumber;
	private GeoCoordinateDTO location;
	private int passengerSeats;
	private boolean babyTransport;
	private boolean petTransport;
	
	public VehicleDTO(Long id, Long driverId, String vehicleType, String model, String licenseNumber, GeoCoordinateDTO location,
			int passengerSeats, boolean babyTransport, boolean petTransport) {
		super();
		this.id = id;
		this.driverId = driverId;
		this.vehicleType = vehicleType;
		this.model = model;
		this.licenseNumber = licenseNumber;
		this.location = location;
		this.passengerSeats = passengerSeats;
		this.babyTransport = babyTransport;
		this.petTransport = petTransport;
	}
	
	public VehicleDTO(Vehicle v) {
		this.id = v.getId();
		this.driverId = v.getDriver().getId();
		this.vehicleType = v.getVehicleType().getTypeName();
		this.model = v.getModel();
		this.licenseNumber = v.getRegPlates();
		this.location = new GeoCoordinateDTO(v.getLocation());
		this.passengerSeats = v.getNumberOfSeats();
		this.babyTransport = v.isAllowedBabyInVehicle();
		this.petTransport = v.isAllowedPetInVehicle();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDriverId() {
		return driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
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

	public GeoCoordinateDTO getLocation() {
		return location;
	}

	public void setLocation(GeoCoordinateDTO currentLocation) {
		this.location = currentLocation;
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

	@Override
	public String toString() {
		return "VehicleDTO [id=" + id + ", driverId=" + driverId + ", vehicleType=" + vehicleType + ", model=" + model
				+ ", licenseNumber=" + licenseNumber + ", currentLocation=" + location + ", passengerSeats="
				+ passengerSeats + ", babyTransport=" + babyTransport + ", petTransport=" + petTransport + "]";
	}
	
	
	
}
