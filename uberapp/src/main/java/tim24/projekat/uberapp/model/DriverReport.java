package tim24.projekat.uberapp.model;

import java.util.Base64;
import java.util.Date;

import jakarta.persistence.*;
import tim24.projekat.uberapp.DTO.GeoCoordinateDTO;

@Table(name = "driver_report")
@Entity
public class DriverReport
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;
    private String reason;
    @OneToOne
    private User forDriver;
    
	public DriverReport(String reason, User forDriver) {
		super();
		this.reason = reason;
		this.forDriver = forDriver;
	}
    
	public DriverReport() {
		super();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public User getForDriver() {
		return forDriver;
	}
	public void setForDriver(User forDriver) {
		this.forDriver = forDriver;
	}
    
	
    
}
