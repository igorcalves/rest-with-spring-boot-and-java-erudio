package br.com.igor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.igor.model.Books;

public interface BookRepository extends JpaRepository<Books,Integer> {
	
}
