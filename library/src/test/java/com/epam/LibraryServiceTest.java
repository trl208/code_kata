package com.epam;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.epam.dto.LibraryDto;
import com.epam.model.Library;
import com.epam.repository.LibraryRepository;
import com.epam.service.LibraryService;

@ExtendWith({ SpringExtension.class, MockitoExtension.class })
@SpringBootTest
class LibraryServiceTest {
	
	
	
	@Mock
	LibraryRepository libraryRepo;
	
	@Mock
	ModelMapper modelMapper;
	
	@InjectMocks
	LibraryService libraryService;
	
	@Test
	void createUserTest()
	{
		Library library=new Library();
		library.setUsername("username");
		LibraryDto libraryDto=new LibraryDto();
		libraryDto.setUsername("username");
		when(modelMapper.map(libraryDto,Library.class)).thenReturn(library);
		libraryService.createUser(libraryDto);
		verify(libraryRepo).save(library);
	}
	
	@Test
	void countTest()
	{
		when(libraryRepo.countBooksForUser("user")).thenReturn(3);
		assertEquals(3,libraryService.countBooks("user"));
	}
	
	
	@Test
	void deleteUserAndBookTest()
	{
		libraryService.deleteUserAndBookId("user", 1L);
		verify(libraryRepo).deleteByUserAndBookId("user",1L);
	}


}
