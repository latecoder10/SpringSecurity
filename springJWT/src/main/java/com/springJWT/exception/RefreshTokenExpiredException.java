package com.springJWT.exception;

public class RefreshTokenExpiredException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1236777126190958458L;

	public RefreshTokenExpiredException(String message) {
        super(message);
    }
}
