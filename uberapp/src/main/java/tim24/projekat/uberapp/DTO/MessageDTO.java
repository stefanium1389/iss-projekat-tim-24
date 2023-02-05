package tim24.projekat.uberapp.DTO;

import java.text.SimpleDateFormat;
import java.util.Date;

import tim24.projekat.uberapp.model.Message;

public class MessageDTO {

	private Long id;
	private String timeOfSending;
	private Long senderId;
	private Long receiverId;
	private String message;
	private String type;
	private Long rideId;
	private String nameToDisplay;
	
	public MessageDTO() {
		super();
	}

	public MessageDTO(Long id, Date timeOfSending, Long senderId, Long receiverId, String message, String type,
			Long rideId, String nameToDisplay) {
		this.id = id;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		this.timeOfSending = sdf.format(timeOfSending);
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.message = message;
		this.type = type;
		this.rideId = rideId;
		this.setNameToDisplay(nameToDisplay);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTimeOfSending() {
		return timeOfSending;
	}

	public void setTimeOfSending(String timeOfSending) {
		this.timeOfSending = timeOfSending;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public Long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
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

	public String getNameToDisplay() {
		return nameToDisplay;
	}

	public void setNameToDisplay(String nameToDisplay) {
		this.nameToDisplay = nameToDisplay;
	}
	
	
}




/*"id": 10,
      "timeOfSending": "2022-11-25T17:32:28Z",
      "senderId": 123,
      "receiverId": 123,
      "message": "The driver is going on a longer route on purpose",
      "type": "RIDE",
      "rideId": 123*/