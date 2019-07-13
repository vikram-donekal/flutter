package com.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class UserNotFoundException extends RuntimeException{
	
	  /**
		 * 
		 */
		private static final long serialVersionUID = 7259701646643374711L;

		public UserNotFoundException(String message) {
	        super(message);
	    }

	    public UserNotFoundException(String message, Throwable cause) {
	        super(message, cause);
	    }

}
