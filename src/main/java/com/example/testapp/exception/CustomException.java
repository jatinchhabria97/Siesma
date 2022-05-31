package com.example.testapp.exception;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

public class CustomException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String message;
	private final Throwable throwable;
	private final HttpStatus httpStatus;
	private final ZonedDateTime timeStamp;
	
	public CustomException(String message, Throwable throwable, HttpStatus httpStatus, ZonedDateTime timeStamp) {
		this.message = message;
		this.throwable = throwable;
		this.httpStatus = httpStatus;
		this.timeStamp = timeStamp;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMessage() {
		return message;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public ZonedDateTime getTimeStamp() {
		return timeStamp;
	}
	
	
	
}