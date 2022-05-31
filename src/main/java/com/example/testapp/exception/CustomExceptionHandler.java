package com.example.testapp.exception;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
	
	@ExceptionHandler(value = {ApiCustomException.class})
	public ResponseEntity<Object> handleApiRequestException(ApiCustomException e) {
		CustomException myException = new CustomException(e.getMessage(), e, HttpStatus.BAD_REQUEST, ZonedDateTime.now(ZoneId.of("Z")));
		
		return new ResponseEntity<>(myException,HttpStatus.BAD_REQUEST);
	}

}
