package tim24.projekat.uberapp.DTO;

import tim24.projekat.uberapp.model.DriverReport;

public class DriverReportResponseDTO {

	private Long id;
	private Long driverId;
	private String reason;
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Long getDriverId() {
		return driverId;
	}
	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}
	
	public DriverReportResponseDTO(Long id, Long driverId, String reason) {
		super();
		this.driverId = driverId;
		this.reason = reason;
		this.id = id;
	}
	public DriverReportResponseDTO(DriverReport dr) {
		this.driverId = dr.getForDriver().getId();
		this.reason = dr.getReason();
		this.id = dr.getId();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	} 
	
	
}
