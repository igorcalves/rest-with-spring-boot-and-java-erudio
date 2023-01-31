package br.com.igor.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.igor.Controllers.BookController;
import br.com.igor.data.vo.v1.BookVO;
import br.com.igor.exceptions.RequiredObjectIsNullException;
import br.com.igor.exceptions.ResourceNotFoundException;
import br.com.igor.mapper.DozerMapper;
import br.com.igor.model.Book;
import br.com.igor.repositories.BookRepository;

@Service
public class BookService {
	
	
	@Autowired
	BookRepository bookRepository;
	
	public List<BookVO> findAll(){
		var books = DozerMapper.parseListObjects(bookRepository.findAll(),BookVO.class);
		books
		.stream()
		.forEach(b -> b.add(linkTo(methodOn(BookController.class).findById(b.getKey())).withSelfRel()));
		return books;
	}
	
	public BookVO findById(Integer id) {
	var entity = bookRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("No records found for id " +id));
	var vo = DozerMapper.parseObject(entity, BookVO.class);
	vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
	return vo;
	}
	
	
	public BookVO create(BookVO book){
		if(book == null) throw new RequiredObjectIsNullException();
		var entity = DozerMapper.parseObject(book, Book.class);
		var vo = DozerMapper.parseObject(bookRepository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public BookVO update(BookVO book) {
		if(book == null) throw new RequiredObjectIsNullException();
		var entity = bookRepository.findById(book.getKey()).orElseThrow(()->new ResourceNotFoundException("No records found for id " + book.getKey()));
		entity.setAuthor(book.getAuthor());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setPrice(book.getPrice());
		entity.setTitle(book.getTitle());
		var vo = DozerMapper.parseObject(bookRepository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		return vo;	
		
	}
	
	public void delete(Integer id) {
		var entity = bookRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("No records found for id " + id));
		bookRepository.delete(entity);	
	}
	
	
	
	
	
	
	
	

}
