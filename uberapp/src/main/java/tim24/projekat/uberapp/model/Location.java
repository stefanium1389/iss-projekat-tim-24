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
    private double geoWidth;
    private double geoHeight;
	public Location() {
		super();
	}
	public Location(Long id, double geoWidth, double geoHeight) {
		super();
		this.id = id;
		this.geoWidth = geoWidth;
		this.geoHeight = geoHeight;
	}
	
	
	public Location(double geoWidth, double geoHeight) {
		super();
		this.geoWidth = geoWidth;
		this.geoHeight = geoHeight;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public double getGeoWidth() {
		return geoWidth;
	}
	public void setGeoWidth(double geoWidth) {
		this.geoWidth = geoWidth;
	}
	public double getGeoHeight() {
		return geoHeight;
	}
	public void setGeoHeight(double geoHeight) {
		this.geoHeight = geoHeight;
	}
	public String getAddress() {
		// TODO Auto-generated method stub
		return "djole";
	}
    
}
