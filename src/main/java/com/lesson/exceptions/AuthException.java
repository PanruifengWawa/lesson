package com.lesson.exceptions;

public class AuthException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public  AuthException(String message) {
		super(message);
	}

}
