package br.com.igor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.igor.model.Book;

public interface BookRepository extends JpaRepository<Book,Integer> {
	
}
