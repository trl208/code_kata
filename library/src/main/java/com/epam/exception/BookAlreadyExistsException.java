	package com.epam.exception;

@SuppressWarnings("serial")
public class BookAlreadyExistsException extends RuntimeException {
	public BookAlreadyExistsException(String message)
	{
		super(message);
	}
}
