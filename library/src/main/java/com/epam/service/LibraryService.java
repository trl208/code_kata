	package com.epam.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.dto.LibraryDto;
import com.epam.exception.AssociationAlreadyExistsException;
import com.epam.exception.NoAssociationException;
import com.epam.model.Library;
import com.epam.repository.LibraryRepository;

@Service
public class LibraryService {

	@Autowired
	LibraryRepository libraryRepo;
	@Autowired
	ModelMapper modelMapper;
	
	public void createUser(LibraryDto libraryDto) {
		
		libraryRepo.save(modelMapper.map(libraryDto,Library.class));
	}
	
	public int countBooks(String username) {
		return libraryRepo.countBooksForUser(username);
	}
	
	public void deleteUserAndBookId(String username,Long bookId) {
		Library lib=libraryRepo.findByUserAndBookId(username, bookId).orElseThrow(()-> new NoAssociationException("no user and book record"));
		libraryRepo.delete(lib);
		
	}
	public List<Long> getBookIds(String userName){
		List<Library> libraryList = libraryRepo.findByUsername(userName);
		List<Long> IdList = new ArrayList<>();
		libraryList.forEach(library->IdList.add(library.getBookid()));
		return IdList;
		}

	public void findAssociation(String username, String bookId) {
		Optional<Library> lib=libraryRepo.findByUserAndBookId(username, Long.parseLong(bookId));
		if(lib.isPresent())
		{
			throw  new AssociationAlreadyExistsException("book already given");
		}
		
	}

}
