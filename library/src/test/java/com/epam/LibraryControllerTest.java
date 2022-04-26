package com.epam;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import com.epam.controller.LibraryController;
import com.epam.dto.BookDto;
import com.epam.dto.LibraryDto;
import com.epam.dto.UserDto;
import com.epam.model.Library;
import com.epam.service.LibraryService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class LibraryControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	LibraryService libraryService;

	@MockBean
	RestTemplate restTemplate;


	@Autowired
	LibraryController libraryController;

	String bookServiceUrl = "http://localhost:8082/books/";

	String userServiceUrl = "http://localhost:8081/users/";

	ResponseEntity<String> response;

	@BeforeEach
	void setUp() {
		response = new ResponseEntity<String>(HttpStatus.OK);

	}

	@Test
	void getBookTest() throws Exception {
		when(restTemplate.exchange(bookServiceUrl, HttpMethod.GET, null, String.class)).thenReturn(response);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("http://localhost:8083/library/books/1");
		MvcResult result1 = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(HttpStatus.OK.value(), result1.getResponse().getStatus());

	}

	@Test
	void deleteBookTest() throws Exception {
		when(restTemplate.exchange(bookServiceUrl + "1", HttpMethod.DELETE, null, String.class)).thenReturn(response);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("http://localhost:8083/library/books/1");
		MvcResult result1 = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(HttpStatus.OK.value(), result1.getResponse().getStatus());

	}

	@Test
	void createBookTest() throws Exception {
		BookDto bookDto = new BookDto();
		bookDto.setName("name");
		ObjectMapper objectMapper = new ObjectMapper();
		String inputJson = objectMapper.writeValueAsString(bookDto);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<BookDto> entity = new HttpEntity<>(bookDto, headers);
		when(restTemplate.exchange(bookServiceUrl + "1", HttpMethod.POST, entity, String.class)).thenReturn(response);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8083/library/books/1")
				.contentType(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();
		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	void updateBookTest() throws Exception {
		BookDto bookDto = new BookDto();
		bookDto.setName("name");
		ObjectMapper objectMapper = new ObjectMapper();
		String inputJson = objectMapper.writeValueAsString(bookDto);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<BookDto> entity = new HttpEntity<>(bookDto, headers);
		when(restTemplate.exchange(bookServiceUrl + "1", HttpMethod.PUT, entity, String.class)).thenReturn(response);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8083/library/books/1")
				.contentType(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();
		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	void getAllUserTest() throws Exception {
		when(restTemplate.exchange(userServiceUrl, HttpMethod.GET, null, String.class)).thenReturn(response);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("http://localhost:8083/library/users");
		MvcResult result1 = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(HttpStatus.OK.value(), result1.getResponse().getStatus());

	}

//	@Test
//	void getUserTest() throws Exception
//	{
//		when(restTemplate.exchange(userServiceUrl+"username", HttpMethod.GET,null,String.class)).thenReturn(response);
//		RequestBuilder requestBuilder=MockMvcRequestBuilders.get("http://localhost:8083/library/users/username");
//		MvcResult result1 = mockMvc.perform(requestBuilder).andReturn();
//		assertEquals(HttpStatus.OK.value(),result1.getResponse().getStatus());
//		
//	}
	@Test
	void getUserBooksTest() {
		
		Library library = new Library();
		library.setBookid(0L);
		library.setId(0L);
		library.setUsername("harsha");
		List<Long> libList = new ArrayList<>();
		libList.add(0L);
		BookDto book = new BookDto();
		book.setAuthor("harsha");
		book.setId(0L);
		book.setName("GOT");
		book.setPublisher("MyPublisher");
		BookDto[] booksArray = new BookDto[1];
		booksArray[0] = book;
		when(libraryService.getBookIds("harsha")).thenReturn(libList);
		when(restTemplate.getForObject(bookServiceUrl, BookDto[].class)).thenReturn(booksArray);
		assertEquals("harshaGOTMyPublisher", libraryController.getUserBooks("harsha"));
	}

	@Test
	void deleteUserTest() throws Exception {
		when(restTemplate.exchange(userServiceUrl + "username", HttpMethod.DELETE, null, String.class))
				.thenReturn(response);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("http://localhost:8083/library/users/username");
		MvcResult result1 = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(HttpStatus.OK.value(), result1.getResponse().getStatus());

	}

	@Test
	void createUserTest() throws Exception {
		UserDto userDto = new UserDto();
		userDto.setName("name");
		ObjectMapper objectMapper = new ObjectMapper();
		String inputJson = objectMapper.writeValueAsString(userDto);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<UserDto> entity = new HttpEntity<>(userDto, headers);
		when(restTemplate.exchange(userServiceUrl + "username", HttpMethod.POST, entity, String.class))
				.thenReturn(response);
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post("http://localhost:8083/library/users/username")
						.contentType(MediaType.APPLICATION_JSON).content(inputJson))
				.andReturn();
		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	void updateUserTest() throws Exception {
		UserDto userDto = new UserDto();
		userDto.setName("name");
		ObjectMapper objectMapper = new ObjectMapper();
		String inputJson = objectMapper.writeValueAsString(userDto);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<UserDto> entity = new HttpEntity<>(userDto, headers);
		when(restTemplate.exchange(userServiceUrl + "username", HttpMethod.PUT, entity, String.class))
				.thenReturn(response);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8083/library/users/username")
				.contentType(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();
		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	void createAssociation() throws Exception {
		UserDto userDto = new UserDto();
		userDto.setName("username");
		BookDto bookDto = new BookDto();
		bookDto.setName("username");
		when(restTemplate.getForObject(userServiceUrl + "username", UserDto.class, "username")).thenReturn(userDto);
		when(restTemplate.getForObject(bookServiceUrl + "1", BookDto.class, "1")).thenReturn(bookDto);
		LibraryDto libDto = new LibraryDto();
		libDto.setUsername("username");
		libDto.setBookid(1L);
		ObjectMapper objectMapper = new ObjectMapper();
		String inputJson = objectMapper.writeValueAsString(libDto);
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post("http://localhost:8083/library/users/username/books/1")
						.contentType(MediaType.APPLICATION_JSON).content(inputJson))
				.andReturn();
		assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	void deleteAssociation() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
				.delete("http://localhost:8083/library/users/username/books/1").contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}

}
