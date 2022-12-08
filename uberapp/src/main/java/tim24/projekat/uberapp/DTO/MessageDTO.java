package tim24.projekat.uberapp.DTO;

import java.time.LocalDateTime;

public class MessageDTO {

	private Long id;
	private LocalDateTime timeOfSending;
	private Long senderId;
	private Long recieverId;
	private String message;
	private String type;
	private Long rideId;
	

	public MessageDTO() {
		super();
	}

	public MessageDTO(Long id, LocalDateTime timeOfSending, Long senderId, Long recieverId, String message, String type,
			Long rideId) {
		super();
		this.id = id;
		this.timeOfSending = timeOfSending;
		this.senderId = senderId;
		this.recieverId = recieverId;
		this.message = message;
		this.type = type;
		this.rideId = rideId;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public LocalDateTime getTimeOfSending() {
		return timeOfSending;
	}


	public void setTimeOfSending(LocalDateTime timeOfSending) {
		this.timeOfSending = timeOfSending;
	}


	public Long getSenderId() {
		return senderId;
	}


	public void setSenderId(Long senderId) {
		this.senderId = senderId;
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

	public Long getRecieverId() {
		return recieverId;
	}

	public void setRecieverId(Long recieverId) {
		this.recieverId = recieverId;
	}
	
	
	
}




/*"id": 10,
      "timeOfSending": "2022-11-25T17:32:28Z",
      "senderId": 123,
      "receiverId": 123,
      "message": "The driver is going on a longer route on purpose",
      "type": "RIDE",
      "rideId": 123*/