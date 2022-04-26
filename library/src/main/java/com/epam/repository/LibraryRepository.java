package com.epam.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.epam.model.Library;

public interface LibraryRepository extends CrudRepository<Library,Long>{
	
	
	
	@Query("select count(*) from Library u where u.username=?1")
	public int countBooksForUser(String username);

	

	@Transactional
	@Modifying
	@Query("delete from Library lib where lib.username=:username and lib.bookid=:bookid")
	public void deleteByUserAndBookId(String username, Long bookid);
	
	@Query("select lib from Library lib where lib.username=:username and lib.bookid=:bookid")
	public Optional<Library> findByUserAndBookId(String username,Long bookid);

	public List<Library> findByUsername(String userName);
}
