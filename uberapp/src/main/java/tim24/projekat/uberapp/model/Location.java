package tim24.projekat.uberapp.model;

import jakarta.persistence.*;

@Table(name = "locations")
@Entity
public class Location
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;
    @Column(precision=15)
    private double latitude;
    @Column(precision=15)
    private double longitude;
    private String address;
    
	public Location() {
		super();
	}
	
	public Location(Long id, double latitude, double longitude, String address) {
		super();
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.setAddress(address);
	}
	
	public Location(double latitude, double longitude, String address) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.setAddress(address);
	}

	public double getLatitude() {
		return latitude;
	}


	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}


	public double getLongitude() {
		return longitude;
	}


	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
    
}
