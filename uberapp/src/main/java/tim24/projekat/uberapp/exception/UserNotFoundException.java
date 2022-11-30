package tim24.projekat.uberapp.exception;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(String message) 
	{
		super(message);
	}
}