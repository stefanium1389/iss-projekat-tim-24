package tim24.projekat.uberapp.model;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import tim24.projekat.uberapp.DTO.VehicleRequestDTO;
import tim24.projekat.uberapp.repo.UserRepository;
import tim24.projekat.uberapp.repo.VehicleTypeRepository;

@Table(name = "vehicles")
@Entity
public class Vehicle
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;
    private String regPlates;
    @OneToOne
    private User driver;
    @ManyToOne
    private VehicleType vehicleType;
    private int numberOfSeats;
    private boolean allowedBabyInVehicle;
    private boolean allowedPetInVehicle;
    @OneToOne
    private Location location;

    private String model;
    public Vehicle()
    {
        super();
    }

	public Vehicle(Long id, String regPlates, User driver, VehicleType vehicleType, int numberOfSeats, 
			boolean allowedBabyInVehicle, boolean allowedPetInVehicle, Location location, String model) {

		super();
		this.id = id;
		this.regPlates = regPlates;
		this.driver = driver;
		this.vehicleType = vehicleType;
		this.numberOfSeats = numberOfSeats;
		this.allowedBabyInVehicle = allowedBabyInVehicle;
		this.allowedPetInVehicle = allowedPetInVehicle;
		this.setLocation(location);
		this.setModel(model);
	}

	public Vehicle(VehicleRequestDTO newV, User driver, VehicleType vehicleType) {
		this.regPlates = newV.getLicenseNumber();
		this.driver = driver;
		this.vehicleType = vehicleType;
		this.numberOfSeats = newV.getPassengerSeats();
		this.allowedBabyInVehicle = newV.isBabyTransport();
		this.allowedPetInVehicle = newV.isPetTransport();
		this.setModel(newV.getModel());	
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegPlates() {
		return regPlates;
	}

	public void setRegPlates(String regPlates) {
		this.regPlates = regPlates;
	}

	public User getDriver() {
		return driver;
	}

	public void setDriver(User driver) {
		this.driver = driver;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	public int getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
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


	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
    
    
}
