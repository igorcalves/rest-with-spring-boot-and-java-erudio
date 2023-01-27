package br.com.igor.unittest.mapper.mocks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.igor.data.vo.v1.BookVO;
import br.com.igor.model.Books;

public class MockBook {

	public Books mockEntity() {
		return mockEntity(0);
	}

	public BookVO mockVO() {
		return mockVO(0);
	}

	public List<Books> mockEntityList() {
		List<Books> persons = new ArrayList<Books>();
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

	public Books mockEntity(Integer number) {
		Books book = new Books();
		book.setAuthor("Authro Test"+ number);
		book.setId(number);
		book.setLaunchDate(LocalDate.of(1, 1, 1));
		book.setPrice(0.00);
		book.setTitle("Text Test"+ number);
		return book;
	}

	public BookVO mockVO(Integer number) {
		BookVO bookVO = new BookVO();
		bookVO.setAuthor("Authro Test"+ number);
		bookVO.setKey(number);
		bookVO.setLaunchDate(LocalDate.of(1, 1, 1));
		bookVO.setPrice(0.00);
		bookVO.setTitle("Text Test"+ number);
		
		return bookVO;
		
	}
	
}
