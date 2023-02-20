package br.com.igor.integrationstests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.igor.configs.TestConfigs;
import br.com.igor.data.vo.v1.security.TokenVO;
import br.com.igor.integrationstests.vo.AccountCredentialsVO;
import br.com.igor.integrationstests.vo.BookVO;
import br.com.igor.integrationtest.testcontainers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class BookControllerJsonTest extends AbstractIntegrationTest {
	

	private static RequestSpecification specification;
	private static ObjectMapper ObjectMapper;

	private static BookVO book;

	@BeforeAll
	public static void setup() {
		ObjectMapper = new ObjectMapper();
		ObjectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		book = new BookVO();

	}

	@Test
	@Order(0)
	public void authorization() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsVO user = new AccountCredentialsVO("Igor", "4044");
		
		var accessToken = given()
				.basePath("/auth/signin")
					.port(TestConfigs.SERVER_PORT)
					.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(user)
					.when()
				.post()
					.then()
						.statusCode(200)
							.extract()
							.body()
								.as(TokenVO.class)
							.getAccessToken();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/api/book/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}

	@Test
	@Order(1)
	public void testCreate() throws Exception {
		mockBook();

		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_IGOR)
				.body(book)
					.when()
					.post()
				.then()
				.statusCode(200)
					.extract()
					.body()
						.asString();

		BookVO persistedBook = ObjectMapper.readValue(content, BookVO.class);
		book = persistedBook;

		assertNotNull(persistedBook);
		assertNotNull(persistedBook.getTitle());
		assertNotNull(persistedBook.getAuthor());
		assertNotNull(persistedBook.getId());
		assertNotNull(persistedBook.getLaunch_date());
		assertNotNull(persistedBook.getPrice());

		assertTrue(persistedBook.getId() > 0);

		assertEquals("Naruto", persistedBook.getTitle());
		assertEquals(20.00, persistedBook.getPrice());
		assertEquals("Masashi Kishimoto", persistedBook.getAuthor());
		
		
	}

	

	@Test
	@Order(2)
	public void testFindById() throws Exception {

		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_IGOR)
					.pathParam("id", book.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(200)
						.extract()
						.body()
							.asString();

		BookVO persistedBook = ObjectMapper.readValue(content, BookVO.class);

		assertNotNull(persistedBook);
		assertNotNull(persistedBook.getTitle());
		assertNotNull(persistedBook.getAuthor());
		assertNotNull(persistedBook.getId());
		assertNotNull(persistedBook.getLaunch_date());
		assertNotNull(persistedBook.getPrice());

		assertTrue(persistedBook.getId() > 0);

		assertEquals("Naruto", persistedBook.getTitle());
		assertEquals(20.00, persistedBook.getPrice());
		assertEquals("Masashi Kishimoto", persistedBook.getAuthor());
		
	}

	@Test
	@Order(3)
	public void testUpdate() throws Exception {		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_IGOR)
					.pathParam("id", book.getId())
				.when()
					.get("{id}")
				.then()
				.statusCode(200)
					.extract()
						.body()
							.asString();
		
		BookVO persistedBook = ObjectMapper.readValue(content, BookVO.class);
		
		persistedBook.setTitle("Naurto Shippuden");
		
		 content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN,TestConfigs.ORIGIN_IGOR)
				.body(persistedBook)
					.when()
					.put()
				.then()
				.statusCode(200)
					.extract()
					.body()
						.asString();
		
		BookVO newPersistedBook = ObjectMapper.readValue(content, BookVO.class);;
		book = newPersistedBook;
		
		assertNotNull(persistedBook);
		assertNotNull(persistedBook.getTitle());
		assertNotNull(persistedBook.getAuthor());
		assertNotNull(persistedBook.getId());
		assertNotNull(persistedBook.getLaunch_date());
		assertNotNull(persistedBook.getPrice());

		assertTrue(persistedBook.getId() > 0);

		assertEquals("Naurto Shippuden", persistedBook.getTitle());
		assertEquals(20.00, persistedBook.getPrice());
		assertEquals("Masashi Kishimoto", persistedBook.getAuthor());
	}

	@Test
	@Order(4)
	public void delete() throws Exception {
		given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.pathParam("id", book.getId())
					.when()
					.delete("{id}")
				.then()
					.statusCode(204);
	}
	
	@Test
	@Order(5)
	public void findALL() throws Exception {
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.when()
					.get()
				.then()
					.statusCode(200)
						.extract()
							.body()
								.asString();
		
		List<BookVO> people = ObjectMapper.readValue(content, new TypeReference<List<BookVO>>() {});
		BookVO foundBookOne = people.get(0);
		
		assertNotNull(foundBookOne);
		assertNotNull(foundBookOne.getTitle());
		assertNotNull(foundBookOne.getAuthor());
		assertNotNull(foundBookOne.getId());
		assertNotNull(foundBookOne.getLaunch_date());
		assertNotNull(foundBookOne.getPrice());
		
		assertTrue(foundBookOne.getId() > 0);
		
		assertEquals("Working effectively with legacy code", foundBookOne.getTitle());
		assertEquals(49.00, foundBookOne.getPrice());
		assertEquals("Michael C. Feathers", foundBookOne.getAuthor());
	}
	@Order(6)
	public void findAllWithoutToken() throws Exception {
		
		RequestSpecification specificationWithoutToken = specification = new RequestSpecBuilder()
				.setBasePath("/api/book/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		given().spec(specificationWithoutToken)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.when()
				.get()
				.then()
				.statusCode(403);
	}
	
	private void mockBook() throws Exception {
	book.setTitle("Naruto");
	book.setPrice(20.00);
	book.setLaunch_date(new Date());
	book.setAuthor("Masashi Kishimoto");
	}

}
