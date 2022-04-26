package com.epam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.epam.exception.BookAlreadyExistsException;
import com.epam.exception.BookNotFoundException;
@RestControllerAdvice
public class GlobalHandler {
	
	
	@ExceptionHandler(BookNotFoundException.class)
	public ResponseEntity<String> handleBookNotFoundException(BookNotFoundException exception)
	{
		return new ResponseEntity<>("book not found",HttpStatus.NOT_FOUND);
		
	}
	@ExceptionHandler(BookAlreadyExistsException.class)
	public ResponseEntity<String> handleBookAlreadyExistsFoundException(BookAlreadyExistsException exception)
	{
		return new ResponseEntity<>("book already exists",HttpStatus.BAD_REQUEST);
		
	}

}
