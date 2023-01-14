package tim24.projekat.uberapp.exception;

public class EmailAlreadyExistsException extends RuntimeException {
	
	public EmailAlreadyExistsException(String message) 
	{
		super(message);
	}
}
