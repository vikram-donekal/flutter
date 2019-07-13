package com.exception;


public class ImageStorageException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7259701646643374711L;

	public ImageStorageException(String message) {
        super(message);
    }

    public ImageStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}