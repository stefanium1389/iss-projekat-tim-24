package tim24.projekat.uberapp.model;

import jakarta.persistence.*;

@Table(name = "vehicles")
@Entity
public class Vehicle
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;
    private String vehicleType;
    private String model;
    private String licenseNumber;
    private GeoCoordinate currentLocation;
    private int passengerSeats;
    private boolean allowedBabyInVehicle;
    private boolean allowedPetInVehicle;
    
	public Vehicle(Long id, String vehicleType, String model, String licenseNumber, GeoCoordinate currentLocation,
			int passengerSeats, boolean allowedBabyInVehicle, boolean allowedPetInVehicle) {
		super();
		this.id = id;
		this.vehicleType = vehicleType;
		this.model = model;
		this.licenseNumber = licenseNumber;
		this.currentLocation = currentLocation;
		this.passengerSeats = passengerSeats;
		this.allowedBabyInVehicle = allowedBabyInVehicle;
		this.allowedPetInVehicle = allowedPetInVehicle;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public GeoCoordinate getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(GeoCoordinate currentLocation) {
		this.currentLocation = currentLocation;
	}

	public int getPassengerSeats() {
		return passengerSeats;
	}

	public void setPassengerSeats(int passengerSeats) {
		this.passengerSeats = passengerSeats;
	}

	public boolean isAllowedBabyInVehicle() {
		return allowedBabyInVehicle;
	}

	public void setAllowedBabyInVehicle(boolean allowedBabyInVehicle) {
		this.allowedBabyInVehicle = allowedBabyInVehicle;
	}

	public boolean isAllowedPetInVehicle() {
		return allowedPetInVehicle;
	}

	public void setAllowedPetInVehicle(boolean allowedPetInVehicle) {
		this.allowedPetInVehicle = allowedPetInVehicle;
	}

	@Override
	public String toString() {
		return "Vehicle [id=" + id + ", vehicleType=" + vehicleType + ", model=" + model + ", licenseNumber="
				+ licenseNumber + ", currentLocation=" + currentLocation + ", passengerSeats=" + passengerSeats
				+ ", allowedBabyInVehicle=" + allowedBabyInVehicle + ", allowedPetInVehicle=" + allowedPetInVehicle
				+ "]";
	}
    
    
}
