package br.com.igor.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import br.com.igor.Controllers.PersonController;
import br.com.igor.data.vo.v1.PersonVO;
import br.com.igor.data.vo.v2.PersonVOV2;
import br.com.igor.exceptions.RequiredObjectIsNullException;
import br.com.igor.exceptions.ResourceNotFoundException;
import br.com.igor.mapper.DozerMapper;
import br.com.igor.mapper.custom.PersonMapper;
import br.com.igor.model.Person;
import br.com.igor.repositories.PersonRepository;

@Service
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired
	PersonRepository reposity;
	
	@Autowired
	PersonMapper mapper;

	public List<PersonVO> findAll() {
		logger.info("Finding all people!");
		var persons = DozerMapper.parseListObjects(reposity.findAll(), PersonVO.class);
		persons
			.stream()
			.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
		return persons;

	}

	public PersonVO findById(Long id) {
		logger.info("Finding one person!");
		var entity = reposity.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		var  vo =  DozerMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return vo;
	}

	public PersonVO create(PersonVO person) {
		
		if(person == null) throw new RequiredObjectIsNullException();
		var entity = DozerMapper.parseObject(person, Person.class);
		logger.info("Create one person!");
		var vo = DozerMapper.parseObject(reposity.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	public PersonVOV2 createV2(PersonVOV2 person) {
		
		var entity = mapper.convertVOtoEntity(person);
		logger.info("Create one person!");
		var vo = mapper.convertEntityToVO(reposity.save(entity));
		return vo;
	}

	public PersonVO update(PersonVO person) {
		if(person == null) throw new RequiredObjectIsNullException();
		logger.info("Create one person!");
		var entity = reposity.findById(person.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		var vo = DozerMapper.parseObject(reposity.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	public void delete(Long id) {
		var entity = reposity.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		reposity.delete(entity);
		logger.info("Deleting one person!");
	}
}
