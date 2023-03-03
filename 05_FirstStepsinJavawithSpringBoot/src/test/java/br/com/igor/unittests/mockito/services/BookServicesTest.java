package br.com.igor.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.igor.data.vo.v1.BookVO;
import br.com.igor.exceptions.RequiredObjectIsNullException;
import br.com.igor.model.Book;
import br.com.igor.repositories.BookRepository;
import br.com.igor.services.BookService;
import br.com.igor.unittest.mapper.mocks.MockBook;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServicesTest {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	MockBook input;

	@InjectMocks
	private BookService service;

	@Mock
	BookRepository repository;

	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockBook();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() throws Exception{
		Date dmock = sdf.parse("11/11/1111");
		Book entity = input.mockEntity(1);
		entity.setId(1);
		when(repository.findById(1)).thenReturn(Optional.of(entity));
		var result = service.findById(1);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals(1, result.getKey());
        assertEquals("Authro Test1", result.getAuthor());
        assertEquals("Text Test1", result.getTitle());
        assertEquals(dmock, result.getLaunchDate());
        assertEquals(0.00, result.getPrice());
	}

	/*
	@Test
	void testFindAll() throws Exception{
		Date dmock = sdf.parse("11/11/1111");
		List<Book> list = input.mockEntityList();

		when(repository.findAll()).thenReturn(list);

		var books = service.findAll();

		assertNotNull(books);
		assertEquals(14, books.size());

		var bookOne = books.get(1);

		assertNotNull(bookOne);
		assertNotNull(bookOne.getKey());
		assertNotNull(bookOne.getLinks());
		assertTrue(bookOne.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals(1, bookOne.getKey());
        assertEquals("Authro Test1", bookOne.getAuthor());
        assertEquals("Text Test1", bookOne.getTitle());
        assertEquals(dmock, bookOne.getLaunchDate());
        assertEquals(0.00, bookOne.getPrice());
        
		var bookFour = books.get(4);

		assertNotNull(bookFour);
		assertNotNull(bookFour.getKey());
		assertNotNull(bookFour.getLinks());
		assertTrue(bookFour.toString().contains("links: [</api/book/v1/4>;rel=\"self\"]"));
		assertEquals(4, bookFour.getKey());
        assertEquals("Authro Test4", bookFour.getAuthor());
        assertEquals("Text Test4", bookFour.getTitle());
        assertEquals(dmock, bookFour.getLaunchDate());
        assertEquals(0.00, bookFour.getPrice());

		var bookSeven = books.get(7);

		assertNotNull(bookSeven);
		assertNotNull(bookSeven.getKey());
		assertNotNull(bookSeven.getLinks());
		assertTrue(bookSeven.toString().contains("links: [</api/book/v1/7>;rel=\"self\"]"));
		assertEquals(7, bookSeven.getKey());
        assertEquals("Authro Test7", bookSeven.getAuthor());
        assertEquals("Text Test7", bookSeven.getTitle());
        assertEquals(dmock, bookSeven.getLaunchDate());
        assertEquals(0.00, bookSeven.getPrice());
	}
	 * */

	@Test
	void testCreate() throws Exception {
		Date dmock = sdf.parse("11/11/1111");
		Book entity = input.mockEntity(1);
		entity.setId(1);
		Book persisted = entity;
		persisted.setId(1);

		BookVO vo = input.mockVO(1);
		vo.setKey(1);
		when(repository.save(entity)).thenReturn(persisted);

		var result = service.create(vo);

		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());

		assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Authro Test1", result.getAuthor());
        assertEquals("Text Test1", result.getTitle());
        assertEquals(dmock, result.getLaunchDate());
        assertEquals(0.00, result.getPrice());
	}

	@Test
	void testCreateWithNullBook() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});
		String expectedMessage = "It is not allowd to persist a null object";

		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testUpdate() throws Exception {
		Date dmock = sdf.parse("11/11/1111");
		Book entity = input.mockEntity(1);
		entity.setId(1);
		Book persisted = entity;
		persisted.setId(1);

		BookVO vo = input.mockVO(1);
		vo.setKey(1);
		when(repository.findById(1)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);

		var result = service.update(vo);

		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());

		assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Authro Test1", result.getAuthor());
        assertEquals("Text Test1", result.getTitle());
        assertEquals(dmock, result.getLaunchDate());
        assertEquals(0.00, result.getPrice());
	}

	@Test
	void testupdateWithNullBook() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.update(null);
		});
		String expectedMessage = "It is not allowd to persist a null object";

		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testDelete() throws Exception {
		Book entity = input.mockEntity(1);
		entity.setId(1);

		when(repository.findById(1)).thenReturn(Optional.of(entity));

		service.delete(1);
	}

}
