package com.epam.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.epam.model.Book;

public interface BookRepository extends CrudRepository<Book,Long>{

	Optional<Book> findByName(String name);

}
