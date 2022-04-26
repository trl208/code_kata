package com.epam.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.epam.client.LibraryClient;
import com.epam.client.UserClient;
import com.epam.dto.BookDto;
import com.epam.dto.LibraryDto;
import com.epam.dto.UserDto;
import com.epam.exception.UserNotFoundException;
import com.epam.service.LibraryService;

@RestController
@RequestMapping("library")
public class LibraryController {

	@Autowired
	LibraryService libraryService;

	@Autowired
	LibraryClient libClient;

	@Autowired
	UserClient userClient;
	

	@GetMapping("/books")
	public ResponseEntity<List<BookDto>> getAllBooks() {

		return libClient.showBooks();

	}

	@GetMapping("/books/{bookId}")
	public ResponseEntity<BookDto> getBookDetails(@PathVariable String bookId) {

		return libClient.findBook(Long.parseLong(bookId));
	}

	@PostMapping("/books/{bookId}")
	public ResponseEntity<BookDto> addBook(@PathVariable String bookId, @RequestBody BookDto bookDto) {
		return libClient.createBook(bookDto);
	}

	@PutMapping("books/{bookId}")
	public ResponseEntity<String> updateBook(@PathVariable String bookId, @RequestBody BookDto bookDto) {
		return libClient.updateBook(Long.parseLong(bookId), bookDto);
	}

	@DeleteMapping("/books/{bookId}")
	public ResponseEntity<String> deleteBookDetails(@PathVariable String bookId) {
		return libClient.deleteBook(Long.parseLong(bookId));
	}

	@GetMapping("/users")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		return userClient.showAccounts();
	}

	@GetMapping("/users/{userName}")
	public ResponseEntity<List<BookDto>> getUserBooks(@PathVariable String userName) {
		userClient.findUser(userName);
		List<Long> IdList = libraryService.getBookIds(userName);
		List<BookDto> booksArray = libClient.showBooks().getBody();
		List<BookDto> userBooksList = new ArrayList<>();
		IdList.forEach(id -> {
			for (BookDto book : booksArray) {
				if (id.equals(book.getId())) {
					userBooksList.add(book);
				}
			}
		});
		return new ResponseEntity<>(userBooksList, HttpStatus.OK);
	}

	@PostMapping("/users/{username}")
	public ResponseEntity<UserDto> addUser(@PathVariable String username, @RequestBody UserDto userDto) {
		return userClient.createUser(userDto);
	}

	@PutMapping("users/{username}")
	public ResponseEntity<String> updateUser(@PathVariable String username, @RequestBody UserDto userDto) {
		return userClient.updateUser(username, userDto);
	}

	@DeleteMapping("/users/{username}")
	public ResponseEntity<String> deleteUserDetails(@PathVariable String username) {
		return userClient.DeleteUser(username);
	}

	@PostMapping("/users/{username}/books/{bookId}")
	public ResponseEntity<String> createLibraryUser(@PathVariable String username, @PathVariable String bookId) throws UserNotFoundException {

			userClient.findUser(username);
			libClient.findBook(Long.parseLong(bookId));
			
			LibraryDto libDto = new LibraryDto();
			libDto.setUsername(username);
			libDto.setId(Long.parseLong(bookId));
			String status = "";
			HttpStatus statusCode = null;
			int bookCount = libraryService.countBooks(username);
			if (bookCount <= 2) {
				libraryService.createUser(libDto);
				status ="book is assigned to user" ;
				statusCode = HttpStatus.CREATED;
			} else {
				status = "books limit exceeded";
				statusCode = HttpStatus.BAD_REQUEST;

			}

			return new ResponseEntity<>(status, statusCode);

	}

	@DeleteMapping("/users/{username}/books/{bookId}")
	public ResponseEntity<String> deleteLibraryUser(@PathVariable String username, @PathVariable String bookId) {
		libraryService.deleteUserAndBookId(username, Long.parseLong(bookId));
		return new ResponseEntity<>("deletion done", HttpStatus.NO_CONTENT);
	}

}
