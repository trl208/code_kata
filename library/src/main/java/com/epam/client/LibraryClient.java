package com.epam.client;
import java.util.List;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.epam.dto.BookDto;
import com.epam.exception.BookNotFoundException;

@FeignClient(name="book-service",fallback=LibraryCleintImp.class)
@LoadBalancerClient(name="book-service",configuration=LibraryCleintImp.class)
public interface LibraryClient {

	@GetMapping("/books")
	public ResponseEntity<List<BookDto>> showBooks();
	
	@GetMapping("/books/{id}")
	public ResponseEntity<BookDto> findBook(@PathVariable Long id) throws BookNotFoundException;
	
	@DeleteMapping("/books/{id}")
	public ResponseEntity<String> deleteBook(@PathVariable Long id) throws BookNotFoundException;
	
	@PutMapping("/books/{id}")
	public ResponseEntity<String> updateBook(@PathVariable Long id,@RequestBody BookDto bookDto);
	
	@PostMapping("/books")
	public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto);
}
