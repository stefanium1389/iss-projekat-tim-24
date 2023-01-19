package tim24.projekat.uberapp.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "messages")
public class Message {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;
	private Date timestamp;
	@ManyToOne
	private User sender;
	@ManyToOne
	private User reciever;
	private String message;
	@Enumerated(EnumType.STRING)
	private MessageType type;
	@ManyToOne
	private Ride ride;
	public Message() {
		super();
	}
	public Message(Long id, Date timestamp, User sender, User reciever, String message, MessageType type, Ride ride) {
		super();
		this.id = id;
		this.timestamp = timestamp;
		this.sender = sender;
		this.reciever = reciever;
		this.message = message;
		this.type = type;
		this.ride = ride;
	}
	public Message(Date timestamp, User sender, User reciever, String message, String type, Ride ride) {
		super();
		
		this.timestamp = timestamp;
		this.sender = sender;
		this.reciever = reciever;
		this.message = message;
		this.type = MessageType.valueOf(type);
		this.ride = ride;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	public User getReciever() {
		return reciever;
	}
	public void setReciever(User reciever) {
		this.reciever = reciever;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public MessageType getType() {
		return type;
	}
	public void setType(MessageType type) {
		this.type = type;
	}
	public Ride getRide() {
		return ride;
	}
	public void setRide(Ride ride) {
		this.ride = ride;
	}
	
	
}
