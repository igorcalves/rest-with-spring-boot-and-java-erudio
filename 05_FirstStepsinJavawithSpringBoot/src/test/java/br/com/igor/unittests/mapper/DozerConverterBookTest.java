package br.com.igor.unittests.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.igor.data.vo.v1.BookVO;
import br.com.igor.data.vo.v1.PersonVO;
import br.com.igor.mapper.DozerMapper;
import br.com.igor.model.Books;
import br.com.igor.unittest.mapper.mocks.MockBook;

public class DozerConverterBookTest {
    
    MockBook inputObject;

    @BeforeEach
    public void setUp() {  
        inputObject = new MockBook();
    }

    @Test
    public void parseEntityToVOTest() {
    	LocalDate dmock = LocalDate.of(1, 1, 1);
        BookVO output = DozerMapper.parseObject(inputObject.mockEntity(), BookVO.class);
        
        assertEquals(0, output.getKey());
        assertEquals("Authro Test0", output.getAuthor());
        assertEquals("Text Test0", output.getTitle());
        assertEquals(dmock, output.getLaunchDate());
        assertEquals(0.00, output.getPrice());
        
    }
    
    @Test
    public void parseEntityListToVOListTest() {
    	LocalDate dmock = LocalDate.of(1, 1, 1);
        List<BookVO> outputList = DozerMapper.parseListObjects(inputObject.mockEntityList(), BookVO.class);
        BookVO outputZero = outputList.get(0);
        
        assertEquals(0, outputZero.getKey());
        assertEquals("Authro Test0", outputZero.getAuthor());
        assertEquals("Text Test0", outputZero.getTitle());
        assertEquals(dmock, outputZero.getLaunchDate());
        assertEquals(0.00, outputZero.getPrice());
        
        BookVO outputSeven = outputList.get(7);
        
        assertEquals(7, outputSeven.getKey());
        assertEquals("Authro Test7", outputSeven.getAuthor());
        assertEquals("Text Test7", outputSeven.getTitle());
        assertEquals(dmock, outputSeven.getLaunchDate());
        assertEquals(0.00, outputSeven.getPrice());
        
        BookVO outputTwelve = outputList.get(12);
        
        assertEquals(12, outputTwelve.getKey());
        assertEquals("Authro Test12", outputTwelve.getAuthor());
        assertEquals("Text Test12", outputTwelve.getTitle());
        assertEquals(dmock, outputTwelve.getLaunchDate());
        assertEquals(0.00, outputTwelve.getPrice());
    }
    
    @Test
    public void parseVOToEntityTest() {
    	LocalDate dmock = LocalDate.of(1, 1, 1);
        Books output = DozerMapper.parseObject(inputObject.mockVO(),Books.class);
        
        assertEquals(0, output.getId());
        assertEquals("Authro Test0", output.getAuthor());
        assertEquals("Text Test0", output.getTitle());
        assertEquals(dmock, output.getLaunchDate());
        assertEquals(0.00, output.getPrice());
        
    }
    
    @Test
    public void parseVOListToEntityListTest() {
    	LocalDate dmock = LocalDate.of(1, 1, 1);
        List<Books> outputList = DozerMapper.parseListObjects(inputObject.mockVOList(), Books.class);
        Books outputZero = outputList.get(0);
        
        assertEquals(0, outputZero.getId());
        assertEquals("Authro Test0", outputZero.getAuthor());
        assertEquals("Text Test0", outputZero.getTitle());
        assertEquals(dmock, outputZero.getLaunchDate());
        assertEquals(0.00, outputZero.getPrice());
        
        Books outputSeven = outputList.get(7);
        
        assertEquals(7, outputSeven.getId());
        assertEquals("Authro Test7", outputSeven.getAuthor());
        assertEquals("Text Test7", outputSeven.getTitle());
        assertEquals(dmock, outputSeven.getLaunchDate());
        assertEquals(0.00, outputSeven.getPrice());
        
        Books outputTwelve = outputList.get(12);
        
        assertEquals(12, outputTwelve.getId());
        assertEquals("Authro Test12", outputTwelve.getAuthor());
        assertEquals("Text Test12", outputTwelve.getTitle());
        assertEquals(dmock, outputTwelve.getLaunchDate());
        assertEquals(0.00, outputTwelve.getPrice());
    }



}