package tim24.projekat.uberapp.DTO;

public class MessageRequestDTO {

	private String message;
	private String type;
	private Long rideId;
	
	public MessageRequestDTO(Long recieverId, String message, String type, Long rideId) {
		super();
		this.message = message;
		this.type = type;
		this.rideId = rideId;
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
