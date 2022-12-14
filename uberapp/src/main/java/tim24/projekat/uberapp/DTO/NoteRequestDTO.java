package tim24.projekat.uberapp.DTO;

public class NoteRequestDTO {

	private String message;

	public NoteRequestDTO(String message) {
		super();
		this.message = message;
	}
	
	public NoteRequestDTO() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
