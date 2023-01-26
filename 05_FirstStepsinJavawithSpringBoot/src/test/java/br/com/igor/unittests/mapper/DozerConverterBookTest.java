package br.com.igor.unittests.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.igor.data.vo.v1.BookVO;
import br.com.igor.mapper.DozerMapper;
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
        assertEquals("Text Test0", output.getText());
        assertEquals(dmock, output.getLaucheDate());
        assertEquals(0.00, output.getPrice());
        
    }



}