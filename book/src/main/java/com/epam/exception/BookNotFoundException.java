package com.epam.exception;

@SuppressWarnings("serial")
public class BookNotFoundException extends RuntimeException {
		public BookNotFoundException(String message)
		{
			super(message);
		}
}
