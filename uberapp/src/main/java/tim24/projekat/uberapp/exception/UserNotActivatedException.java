package tim24.projekat.uberapp.exception;

import org.springframework.security.core.AuthenticationException;

public class UserNotActivatedException extends AuthenticationException {

	public UserNotActivatedException(String msg) {
		super(msg);
	}

}
