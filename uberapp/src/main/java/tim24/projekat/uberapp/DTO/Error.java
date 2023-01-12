package tim24.projekat.uberapp.DTO;

public class Error {
	private String message;

	public Error(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
