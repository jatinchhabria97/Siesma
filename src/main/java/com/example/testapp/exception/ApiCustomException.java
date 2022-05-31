package com.example.testapp.exception;

public class ApiCustomException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ApiCustomException(String message) {
		super(message);
	}

	public ApiCustomException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
	
	
}
