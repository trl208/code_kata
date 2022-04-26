package com.epam.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookDto {
	
	
	private Long id;
	
	private String name;
	

	private String publisher;
	
	private String author;
	
	String port;

}
