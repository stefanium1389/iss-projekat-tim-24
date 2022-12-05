package tim24.projekat.uberapp.DTO;

import java.time.LocalDateTime;

public class NoteDTO {

	private Long id;
	private LocalDateTime date;
	private String message;
	
	
	public NoteDTO(Long id, LocalDateTime date, String message) {
		super();
		this.id = id;
		this.date = date;
		this.message = message;
	}
	
	
}