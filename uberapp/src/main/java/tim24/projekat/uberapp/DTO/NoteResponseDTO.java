package tim24.projekat.uberapp.DTO;

import java.time.LocalDateTime;

public class NoteResponseDTO {

	private Long id;
	private LocalDateTime date;
	private String message;
	public NoteResponseDTO(Long id, LocalDateTime date, String message) {
		super();
		this.id = id;
		this.date = date;
		this.message = message;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
