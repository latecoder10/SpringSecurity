package com.springJWT.exception;

public class RefreshTokenNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3373688494185849496L;

	public RefreshTokenNotFoundException(String message) {
        super(message);
    }
}
