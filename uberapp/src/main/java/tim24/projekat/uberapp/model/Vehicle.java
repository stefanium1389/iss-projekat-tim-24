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
    private String regPlates;
    @OneToOne
    private User driver;
    @ManyToOne
    private VehicleType vehicleType;
    private int numberOfSeats;
    private boolean allowedBabyInVehicle;
    private boolean allowedPetInVehicle;

    public Vehicle()
    {
        super();
    }

	public Vehicle(Long id, String regPlates, User driver, VehicleType vehicleType, int numberOfSeats,
			boolean allowedBabyInVehicle, boolean allowedPetInVehicle) {
		super();
		this.id = id;
		this.regPlates = regPlates;
		this.driver = driver;
		this.vehicleType = vehicleType;
		this.numberOfSeats = numberOfSeats;
		this.allowedBabyInVehicle = allowedBabyInVehicle;
		this.allowedPetInVehicle = allowedPetInVehicle;
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
    
    
}
