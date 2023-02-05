package tim24.projekat.uberapp.DTO;

public class MessageRequestDTO {

	private String message;
	private String type;
	private Long rideId;
	
	
	public MessageRequestDTO(String message, String type, Long rideId) {
		this.message = message;
		this.type = type;
		this.rideId = rideId;
	}
	

	public MessageRequestDTO() {
		super();
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getRideId() {
		return rideId;
	}

	public void setRideId(Long rideId) {
		this.rideId = rideId;
	}
	
}
