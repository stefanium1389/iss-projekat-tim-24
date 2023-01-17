package tim24.projekat.uberapp.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import tim24.projekat.uberapp.DTO.FavoriteRideDTO;

@Table(name="favorites")
@Entity
public class FavoriteRide {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;
	private String favoriteName;
	private boolean babyInVehicle;
    private boolean petInVehicle;
    @OneToOne
    private Route route;
    @OneToOne(cascade = CascadeType.PERSIST)
    private User passenger;
    private String vehicleType;
    
	public FavoriteRide() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public FavoriteRide(Long id, String favoriteName, boolean babyInVehicle, boolean petInVehicle, Route route,
			User passenger, String vehicleType) {
		super();
		this.id = id;
		this.favoriteName = favoriteName;
		this.babyInVehicle = babyInVehicle;
		this.petInVehicle = petInVehicle;
		this.route = route;
		this.passenger = passenger;
		this.setVehicleType(vehicleType);
	}
	/**
	 * Mora se dodati Route naknadno
	 * 
	**/
	public FavoriteRide(FavoriteRideDTO dto, User user) {
		this.passenger = user;
		this.favoriteName = dto.getFavoriteName();
		this.babyInVehicle = dto.isBabyTransport();
		this.petInVehicle = dto.isPetTransport();
		this.route = null;
		this.vehicleType = dto.getVehicleType();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFavoriteName() {
		return favoriteName;
	}
	public void setFavoriteName(String favoriteName) {
		this.favoriteName = favoriteName;
	}
	public boolean isBabyInVehicle() {
		return babyInVehicle;
	}
	public void setBabyInVehicle(boolean babyInVehicle) {
		this.babyInVehicle = babyInVehicle;
	}
	public boolean isPetInVehicle() {
		return petInVehicle;
	}
	public void setPetInVehicle(boolean petInVehicle) {
		this.petInVehicle = petInVehicle;
	}
	public Route getRoute() {
		return route;
	}
	public void setRoute(Route route) {
		this.route = route;
	}
	public User getPassenger() {
		return passenger;
	}
	public void setPassenger(User passenger) {
		this.passenger = passenger;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
}
