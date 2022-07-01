package com.thanthu.orgservice.exceptions;

public class NotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -722728127996703955L;

	public NotFoundException(String message) {
        super(message);
    }
	
	public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
