package tim24.projekat.uberapp.DTO;

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
