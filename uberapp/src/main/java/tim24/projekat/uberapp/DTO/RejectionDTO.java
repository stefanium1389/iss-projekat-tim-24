package tim24.projekat.uberapp.DTO;

import tim24.projekat.uberapp.model.Refusal;

public class RejectionDTO {

	private String reason;
	private String timeOfRejection;
	
	public RejectionDTO() {
		super();
	}

	public RejectionDTO(String reason, String timeOfRejection) {
		super();
		this.reason = reason;
		this.timeOfRejection = timeOfRejection;
	}

	public RejectionDTO(Refusal refusal) {
		super();
		this.reason = refusal.getReason();
		this.timeOfRejection = refusal.getTime().toString();
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getTimeOfRejection() {
		return timeOfRejection;
	}

	public void setTimeOfRejection(String timeOfRejection) {
		this.timeOfRejection = timeOfRejection;
	}
	
	
	
	
}
