package com.epam.exception;

@SuppressWarnings("serial")
public class NoAssociationException extends RuntimeException{
	
	public NoAssociationException(String message)
	{
		super(message);
	}

}
