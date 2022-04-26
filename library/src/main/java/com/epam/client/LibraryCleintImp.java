package com.epam.client;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.epam.dto.BookDto;
@Component
public class LibraryCleintImp implements LibraryClient{

	@Override
	public ResponseEntity<List<BookDto>> showBooks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<BookDto> findBook(Long id) {
	BookDto book=new BookDto();
	book.setName("fault_name");
	book.setPort("fault-port");
		return new ResponseEntity<>(book,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> deleteBook(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<String> updateBook(Long id, BookDto bookDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<BookDto> createBook(BookDto bookDto) {
		// TODO Auto-generated method stub
		return null;
	}

}
