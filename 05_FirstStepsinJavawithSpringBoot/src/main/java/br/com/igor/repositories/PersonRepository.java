package br.com.igor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.igor.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{

}
