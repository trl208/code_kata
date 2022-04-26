package com.epam.exception;

@SuppressWarnings("serial")
public class NoUserException extends RuntimeException {
	
	public NoUserException(String message)
	{
		super(message);
	}

}
