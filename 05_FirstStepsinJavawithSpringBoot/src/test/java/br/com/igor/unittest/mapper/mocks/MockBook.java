package br.com.igor.unittest.mapper.mocks;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.igor.data.vo.v1.BookVO;
import br.com.igor.model.Book;

public class MockBook {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public Book mockEntity() throws Exception{
		return mockEntity(0);
	}

	public BookVO mockVO()throws Exception {
		return mockVO(0);
	}

	public List<Book> mockEntityList()throws Exception {
		List<Book> persons = new ArrayList<Book>();
		for (int i = 0; i < 14; i++) {
			persons.add(mockEntity(i));
		}
		return persons;
	}

	public List<BookVO> mockVOList()throws Exception {
		List<BookVO> persons = new ArrayList<>();
		for (int i = 0; i < 14; i++) {
			persons.add(mockVO(i));
		}
		return persons;
	}

	public Book mockEntity(Integer number) throws Exception{
		Book book = new Book();
		book.setAuthor("Authro Test"+ number);
		book.setId(number);
		book.setLaunchDate(sdf.parse("11/11/1111"));
		book.setPrice(0.00);
		book.setTitle("Text Test"+ number);
		return book;
	}

	public BookVO mockVO(Integer number) throws Exception{
		BookVO bookVO = new BookVO();
		bookVO.setAuthor("Authro Test"+ number);
		bookVO.setKey(number);
		bookVO.setLaunchDate(sdf.parse("11/11/1111"));
		bookVO.setPrice(0.00);
		bookVO.setTitle("Text Test"+ number);
		
		return bookVO;
		
	}
	
}
