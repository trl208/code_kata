package com.epam.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.dto.BookDto;
import com.epam.exception.BookAlreadyExistsException;
import com.epam.exception.BookNotFoundException;
import com.epam.model.Book;
import com.epam.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	BookRepository bookRepository;
	
	String exceptionMsg="book not found";

	public BookDto createUser(BookDto bookDto) {

		Optional<Book> optBook=bookRepository.findByName(bookDto.getName());
		if(optBook.isPresent())
			throw new BookAlreadyExistsException("book already exists");
		Book savedBook=bookRepository.save(modelMapper.map(bookDto,Book.class));
		return modelMapper.map(savedBook, BookDto.class);
		

	}

	public List<Book> displayAllBooks() {

		return (List<Book>) bookRepository.findAll();
	}

	public BookDto findBook(Long id) {
		Book optBook = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(exceptionMsg));
		return modelMapper.map(optBook, BookDto.class);

	}

	@Transactional
	public void deleteBook(Long id) {
		
		Book optBook = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(exceptionMsg));
		bookRepository.delete(optBook);
		

	}

	public void updateBook(Long id, BookDto bookDto) {
		
		Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(exceptionMsg));
		book.setAuthor(bookDto.getAuthor());
		book.setName(bookDto.getName());
		book.setPublisher(bookDto.getPublisher());
		bookRepository.save(book);
		

	}

}
