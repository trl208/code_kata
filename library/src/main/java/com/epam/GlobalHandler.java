package com.epam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import com.epam.exception.AssociationAlreadyExistsException;
import com.epam.exception.NoAssociationException;
import com.epam.exception.NoUserException;
@RestControllerAdvice
public class GlobalHandler {
	

	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<String> handleHttpClientErrorException(HttpClientErrorException exception)
	{
		return new ResponseEntity<>(exception.getMessage(),exception.getStatusCode());
		
	}
	@ExceptionHandler(feign.FeignException.class)
	public ResponseEntity<String> handleFeignException(feign.FeignException exception)
	{
		
		return new ResponseEntity<>(exception.getLocalizedMessage(),HttpStatus.NOT_FOUND);
	
	}
	@ExceptionHandler(NoUserException.class)
	public ResponseEntity<String> handleNoUserException(NoUserException exception)
	{
		return new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_FOUND);
		
	}
	@ExceptionHandler(NoAssociationException.class)
	public ResponseEntity<String> handleNoAssociationException(NoAssociationException exception)
	{
		return new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_FOUND);
		
	}
	@ExceptionHandler(AssociationAlreadyExistsException.class)
	public ResponseEntity<String> handleAssociationAlreadyExistsException(AssociationAlreadyExistsException exception)
	{
		return new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_FOUND);
		
	}
	
	

}
