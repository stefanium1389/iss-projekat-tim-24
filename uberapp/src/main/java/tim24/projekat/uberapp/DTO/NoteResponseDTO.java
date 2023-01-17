package tim24.projekat.uberapp.DTO;

import tim24.projekat.uberapp.model.Note;

import java.util.Date;

public class NoteResponseDTO {

	private Long id;
	private Date date;
	private String message;
	public NoteResponseDTO(Long id, Date date, String message)
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
		this.date = note.getTime();
		this.message = note.getNote();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
