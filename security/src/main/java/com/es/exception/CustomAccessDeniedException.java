package com.es.exception;

public class CustomAccessDeniedException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5907171026296282416L;
	private String errorCode;

    public CustomAccessDeniedException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

