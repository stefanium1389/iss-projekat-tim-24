package tim24.projekat.uberapp.DTO;

import tim24.projekat.uberapp.model.Note;

import java.text.SimpleDateFormat;

public class NoteResponseDTO {

	private Long id;
	private String date;
	private String message;
	public NoteResponseDTO(Long id, String date, String message)
	{
		super();
		this.id = id;
		this.date = date;
		this.message = message;
	}
	public NoteResponseDTO(Note note)
	{
		super();
		this.id = note.getId();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		String dateString = sdf.format(note.getTime());
		this.date = dateString;
		this.message = note.getNote();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
