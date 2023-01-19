package tim24.projekat.uberapp.controller;

public class DriverReportDTO {

	private String reason;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public DriverReportDTO() {
		super();
	}

	public DriverReportDTO(String reason) {
		super();
		this.reason = reason;
	}
	
	
}
