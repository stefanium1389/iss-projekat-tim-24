package tim24.projekat.uberapp.DTO;

import java.time.LocalDateTime;

public class MessageSendResponseDTO {

	private Long id;
	private LocalDateTime timeOfSending;
	private Long senderId;
	private Long recieverId;
	private String message;
	private String type;
	private Long rideId;
	
	public MessageSendResponseDTO(Long id, LocalDateTime timeOfSending, Long senderId, Long recieverId, String message,
			String type, Long rideId) {
		super();
		this.id = id;
		this.timeOfSending = timeOfSending;
		this.senderId = senderId;
		this.recieverId = recieverId;
		this.message = message;
		this.type = type;
		this.rideId = rideId;
	}
	
	
	
}



/*{
  "id": 123,
  "timeOfSending": "2022-11-25T17:32:28Z",
  "senderId": 123,
  "receiverId": 123,
  "message": "The driver is going on a longer route on purpose",
  "type": "RIDE",
  "rideId": 123
}*/