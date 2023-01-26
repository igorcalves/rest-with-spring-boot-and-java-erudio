package br.com.igor.unittest.mapper.mocks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.igor.data.vo.v1.BookVO;
import br.com.igor.model.Book;

public class MockBook {

	public Book mockEntity() {
		return mockEntity(0);
	}

	public BookVO mockVO() {
		return mockVO(0);
	}

	public List<Book> mockEntityList() {
		List<Book> persons = new ArrayList<Book>();
		for (int i = 0; i < 14; i++) {
			persons.add(mockEntity(i));
		}
		return persons;
	}

	public List<BookVO> mockVOList() {
		List<BookVO> persons = new ArrayList<>();
		for (int i = 0; i < 14; i++) {
			persons.add(mockVO(i));
		}
		return persons;
	}

	public Book mockEntity(Integer number) {
		Book book = new Book();
		book.setAuthor("Authro Test"+ number);
		book.setId(number);
		book.setLaucheDate(LocalDate.of(1, 1, 1));
		book.setPrice(0.00);
		book.setText("Text Test"+ number);
		return book;
	}

	public BookVO mockVO(Integer number) {
		BookVO bookVO = new BookVO();
		bookVO.setAuthor("Authro Test"+ number);
		bookVO.setKey(number);
		bookVO.setLaucheDate(LocalDate.of(1, 1, 1));
		bookVO.setPrice(0.00);
		bookVO.setText("Text Test"+ number);
		
		return bookVO;
		
	}
	
}
