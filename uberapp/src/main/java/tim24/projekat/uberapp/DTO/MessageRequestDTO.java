package tim24.projekat.uberapp.DTO;

public class MessageRequestDTO {

	private Long recieverId;
	private String message;
	private String type;
	private Long rideId;
	
	public MessageRequestDTO(Long recieverId, String message, String type, Long rideId) {
		super();
		this.recieverId = recieverId;
		this.message = message;
		this.type = type;
		this.rideId = rideId;
	}

	public Long getRecieverId() {
		return recieverId;
	}

	public void setRecieverId(Long recieverId) {
		this.recieverId = recieverId;
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
