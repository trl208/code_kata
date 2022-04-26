package com.epam;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.epam.controller.BookController;
import com.epam.dto.BookDto;
import com.epam.model.Book;
import com.epam.repository.BookRepository;
import com.epam.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;


@ContextConfiguration(classes= {BookController.class,BookRepository.class,BookService.class})
@WebMvcTest(value = BookController.class)
class BookControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	BookService bookService;

	@Test
	void getAllBooksTest() throws Exception
	{
		List<Book> books=new ArrayList<>();
		Book book=new Book();
		book.setAuthor("abc");
		book.setName("name");
		book.setPublisher("publisher");
		books.add(book);
		when(bookService.displayAllBooks()).thenReturn(books);
		RequestBuilder requestBuilder=MockMvcRequestBuilders.get("/books/");
		MvcResult result1 = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
		ObjectMapper objMap=new ObjectMapper();
		Book[] reslist=objMap.readValue(result1.getResponse().getContentAsString(),Book[].class);
		assertTrue(reslist.length>0);
		assertEquals(reslist[0].getName(),book.getName());
		
	}
	
	@Test
	void getBooktest() throws Exception
	{
		
		BookDto book=new BookDto();
		book.setAuthor("abc");
		book.setName("name");
		book.setPublisher("publisher");
		when(bookService.findBook(1L)).thenReturn(book);
		RequestBuilder requestBuilder=MockMvcRequestBuilders.get("/books/1");
		MvcResult result1 = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
		ObjectMapper objMap=new ObjectMapper();
		BookDto reslist=objMap.readValue(result1.getResponse().getContentAsString(),BookDto.class);
		assertEquals(reslist.getName(),book.getName());
		
	}
	

	@Test
	void createBookTest() throws Exception
	{
		BookDto bookDto=new BookDto();
		bookDto.setName("name");
		when(bookService.createUser(any(BookDto.class))).thenReturn(bookDto);
		String uri="/books/";
		 MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content("{\r\n"
		 		+ "    \"name\":\"Java\",\r\n"
		 		+ "    \"publisher\":\"book\",\r\n"
		 		+"     \"author\":\"bala\"\r\n"
		 		+ "}")).andReturn();
		ObjectMapper objMap=new ObjectMapper();
		BookDto reslist=objMap.readValue(mvcResult.getResponse().getContentAsString(),BookDto.class);
		assertEquals(reslist.getName(),bookDto.getName());
	}
	
	@Test
	void deleteBookTest() throws Exception
	{
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/books/1");
		mockMvc.perform(requestBuilder).andExpect(status().isNoContent());
	
	}
	@Test
	void updateBookTest() throws Exception
	{
		BookDto bookDto=new BookDto();
		bookDto.setName("name");
		ObjectMapper objectMapper = new ObjectMapper();
		String inputJson = objectMapper.writeValueAsString(bookDto);
		String expected="book updated";
		MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.put("/books/1").contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
		assertEquals(result2.getResponse().getContentAsString(),expected);
	}
	
	
}
