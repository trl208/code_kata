package com.epam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.epam.exception.UserAlreadyExistsException;
import com.epam.exception.UserNotFoundException;
@RestControllerAdvice
public class GlobalHandler {
	
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handleBookNotFoundException(UserNotFoundException exception)
	{
		return new ResponseEntity<>("user not found",HttpStatus.NOT_FOUND);
		
	}
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException exception)
	{
		return new ResponseEntity<>("user already exists",HttpStatus.BAD_REQUEST);
		
	}

}
