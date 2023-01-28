package br.com.igor.unittest.mapper.mocks;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.igor.data.vo.v1.BookVO;
import br.com.igor.model.Books;

public class MockBook {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public Books mockEntity() throws Exception{
		return mockEntity(0);
	}

	public BookVO mockVO()throws Exception {
		return mockVO(0);
	}

	public List<Books> mockEntityList()throws Exception {
		List<Books> persons = new ArrayList<Books>();
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

	public Books mockEntity(Integer number) throws Exception{
		Books book = new Books();
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
