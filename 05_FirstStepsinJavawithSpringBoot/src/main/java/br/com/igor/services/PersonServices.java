package br.com.igor.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.igor.exceptions.ResourceNotFoundException;
import br.com.igor.model.Person;
import br.com.igor.repositories.PersonRepository;

@Service
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepository reposity;

	public List<Person> findAll() {
		logger.info("Finding all people!");
		return reposity.findAll();

	}

	public Person findById(Long id) {
		logger.info("Finding one person!");
		return reposity.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("No records found for this ID!"));
	}

	public Person create(Person person) {
		logger.info("Create one person!");
		return reposity.save(person);
	}
	
	public Person update(Person person) {
		
		logger.info("Create one person!");
		var entity = reposity.findById(person.getId()).orElseThrow(()->new ResourceNotFoundException("No records found for this ID!"));
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAdress(person.getAdress());
		entity.setGender(person.getGender());
		return reposity.save(entity);
	}
	
	public void delete(Long id) {
		var entity = reposity.findById(id).orElseThrow(()->new ResourceNotFoundException("No records found for this ID!"));
		reposity.delete(entity);
		logger.info("Deleting one person!");
	}
	
	private Person mockPerson(int i) {

		Person person = new Person();
		person.setFirstName("Person Name " + i);
		person.setLastName("Last Name " + i);
		person.setAdress("Some address in Brasil " + i);
		person.setGender("Male ");
		return person;
	}

}
