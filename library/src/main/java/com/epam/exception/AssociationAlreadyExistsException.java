package com.epam.exception;

@SuppressWarnings("serial")
public class AssociationAlreadyExistsException extends RuntimeException {
	
	public  AssociationAlreadyExistsException(String message)
	{
		super(message);
	}

}
