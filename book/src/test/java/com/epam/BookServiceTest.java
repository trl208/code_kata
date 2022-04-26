package com.epam;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.epam.dto.BookDto;
import com.epam.exception.BookAlreadyExistsException;
import com.epam.exception.BookNotFoundException;
import com.epam.model.Book;
import com.epam.repository.BookRepository;
import com.epam.service.BookService;

@ExtendWith({ SpringExtension.class, MockitoExtension.class })
@SpringBootTest
class BookServiceTest {

	@Mock
	BookRepository bookRepo;

	@InjectMocks
	BookService bookService;

	BookDto bookDto;

	Book book;
	List<Book> books;

	@Mock
	ModelMapper modelMapper;

	@BeforeEach
	void setUp() {
		book = new Book();
		book.setName("book");
		book.setId(1L);
		bookDto = new BookDto();
		bookDto.setName("book");
		books = new ArrayList<>();
		books.add(book);
	}

	@Test
	void createTest() throws Exception {
		BookDto bookDto1 = new BookDto();
		bookDto1.setName("book");
		bookDto1.setId(1L);
		bookDto1.setName("name");
		bookDto1.setPublisher("publisher");
		when(bookRepo.findByName(bookDto1.getName())).thenReturn(Optional.empty());
		when(modelMapper.map(bookDto1, Book.class)).thenReturn(book);
		when(bookRepo.save(any())).thenReturn(book);
		when(modelMapper.map(book, BookDto.class)).thenReturn(bookDto1);
		BookDto res = bookService.createUser(bookDto1);
		assertEquals(res.getName(),bookDto1.getName());
	}
	@Test
	void createFailTest() throws Exception {
		BookDto bookDto1 = new BookDto();
		bookDto1.setName("book");
		bookDto1.setId(1L);
		bookDto1.setName("name");
		bookDto1.setPublisher("publisher");
		Book book=new Book();
		book.setName("name");
		when(bookRepo.findByName(bookDto1.getName())).thenReturn(Optional.of(book));
		Exception exception = assertThrows(BookAlreadyExistsException.class, () -> {
			bookService.createUser(bookDto1);});
			String expectedMessage ="book already exists"; 
			String actualMessage =exception.getMessage();
			assertEquals(expectedMessage,actualMessage);
		
	}

	@Test
	void displayAllUserTest() {
		when(bookRepo.findAll()).thenReturn(books);
		
		assertTrue(bookService.displayAllBooks().size() > 0);
	}

	@Test
	void deleteUserTest() {
		Optional<Book> optBook = Optional.of(book);
		when(bookRepo.findById(1L)).thenReturn(optBook);
		bookService.deleteBook(1L);
		verify(bookRepo).delete(optBook.get());
	}
	@Test
	void deleteUserExceptionTest() {
		when(bookRepo.findById(1L)).thenReturn(Optional.empty());
		Exception exception = assertThrows(BookNotFoundException.class, () -> {
			bookService.deleteBook(1L);});
			String expectedMessage ="book not found"; 
			String actualMessage =exception.getMessage();
			assertEquals(expectedMessage,actualMessage);
	}
	
	

	@Test
	void updateBookTest() {
		Optional<Book> optBook = Optional.of(book);
		when(bookRepo.findById(1L)).thenReturn(optBook);
		bookService.updateBook(1L, bookDto);
		verify(bookRepo).save(book);

	}
	@Test
	void updateBookExceptionTest() {
		
		when(bookRepo.findById(1L)).thenReturn(Optional.empty());
		Exception exception = assertThrows(BookNotFoundException.class, () -> {
			bookService.updateBook(1L, bookDto);});
			String expectedMessage ="book not found"; 
			String actualMessage =exception.getMessage();
			assertEquals(expectedMessage,actualMessage);

	}

	@Test
	void getBookTest() throws BookNotFoundException {
		Optional<Book> optBook = Optional.of(book);
		when(bookRepo.findById(1L)).thenReturn(optBook);
		when(modelMapper.map(optBook.get(), BookDto.class)).thenReturn(bookDto);
		assertEquals(bookService.findBook(1L).getName(),book.getName());
	}
	
	@Test
	void getBookExceptionTest()
	{
		when(bookRepo.findById(1L)).thenReturn(Optional.empty());
		Exception exception = assertThrows(BookNotFoundException.class, () -> {
			bookService.findBook(1L);});
			String expectedMessage ="book not found"; 
			String actualMessage =exception.getMessage();
			assertEquals(expectedMessage,actualMessage);
	}

}
