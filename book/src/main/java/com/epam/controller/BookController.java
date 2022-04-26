package com.epam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.dto.BookDto;
import com.epam.model.Book;
import com.epam.service.BookService;

@RestController
@RequestMapping("books")
public class BookController {
	
	@Autowired
	BookService bookService;
	
	@Autowired
	Environment env;
	
	@PostMapping()
	public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) 	{
		BookDto savedBook=bookService.createUser(bookDto);
		return new ResponseEntity<>(savedBook,HttpStatus.CREATED);
	}
	@GetMapping()
	public ResponseEntity<List<Book>> showBooks()
	{
		return new ResponseEntity<>(bookService.displayAllBooks(),HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<BookDto> findBook(@PathVariable Long id) 
	{
		
		BookDto bookDto=bookService.findBook(id);
		bookDto.setPort(env.getProperty("local.server.port"));
		return new ResponseEntity<>(bookDto,HttpStatus.OK);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteBook(@PathVariable Long id)
	{
		
		bookService.deleteBook(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}
	@PutMapping("/{id}")
	public ResponseEntity<String> updateBook(@PathVariable Long id,@RequestBody BookDto bookDto)
	{
		
		bookService.updateBook(id,bookDto);
		return new ResponseEntity<>("book updated",HttpStatus.OK);
		
	}
	
	

}
