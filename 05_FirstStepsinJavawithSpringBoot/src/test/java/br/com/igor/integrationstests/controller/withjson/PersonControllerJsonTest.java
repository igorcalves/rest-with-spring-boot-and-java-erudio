package br.com.igor.integrationstests.controller.withjson;

import static io.restassured.RestAssured.given;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.igor.configs.TestConfigs;
import br.com.igor.integrationstests.vo.PersonVOTest;
import br.com.igor.integrationtest.testcontainers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerJsonTest extends AbstractIntegrationTest {
	
	private static RequestSpecification specification;
	private static ObjectMapper ObjectMapper;
	
	private static PersonVOTest person;
	
	@BeforeAll
	public static void setup() {
		ObjectMapper = new ObjectMapper();
		ObjectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		person = new PersonVOTest();
		
		
	}

	@Test
	@Order(1)
	public void testCreate() throws Exception {
		mockPerson();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_IGOR)
				.setBasePath("/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
			.build();
		
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.body(person)
					.when()
					.post()
				.then()
					.statusCode(200)
						.extract()
						.body()
							.asString();
		
		
		PersonVOTest createPerson = ObjectMapper.readValue(content, PersonVOTest.class);
		person = createPerson;
		
		assertNotNull(createPerson);
		assertNotNull(createPerson.getId());
		assertNotNull(createPerson.getFirstName());
		assertNotNull(createPerson.getLastName());
		assertNotNull(createPerson.getGender());
		
		assertTrue(createPerson.getId() > 0);
		
		assertEquals("Super",createPerson.getFirstName());
		assertEquals("Man",createPerson.getLastName());
		assertEquals("Metropolis",createPerson.getAddress());
		assertEquals("Male",createPerson.getGender());
	}
	@Test
	@Order(2)
	public void testCreateWithWrongOrigin() throws Exception {
		mockPerson();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_IGARU)
				.setBasePath("/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(person)
					.when()
					.post()
					.then()
				.statusCode(403)
					.extract()
					.body()
						.asString();
		
		
		
		assertNotNull(content);
		assertEquals("Invalid CORS request", content);
	}
	
	@Test
	@Order(3)
	public void	testFindById() throws Exception {
		mockPerson();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_IGOR)
				.setBasePath("/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
			.build();
		
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.pathParam("id", person.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(200)
						.extract()
						.body()
							.asString();
		
		
		PersonVOTest createPerson = ObjectMapper.readValue(content, PersonVOTest.class);
		person = createPerson;
		
		assertNotNull(createPerson);
		assertNotNull(createPerson.getId());
		assertNotNull(createPerson.getFirstName());
		assertNotNull(createPerson.getLastName());
		assertNotNull(createPerson.getGender());
		
		assertTrue(createPerson.getId() > 0);
		
		assertEquals("Super",createPerson.getFirstName());
		assertEquals("Man",createPerson.getLastName());
		assertEquals("Metropolis",createPerson.getAddress());
		assertEquals("Male",createPerson.getGender());
	}
	
	@Test
	@Order(4)
	public void	testFindByIdWithWrongOrigin() throws Exception {
		mockPerson();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_IGARU)
				.setBasePath("/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
			.build();
		
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.pathParam("id", person.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(403)
						.extract()
						.body()
							.asString();
		
		
		assertNotNull(content);
		assertEquals("Invalid CORS request", content);
	}

	private void mockPerson() {
		person.setFirstName("Super");
		person.setLastName("Man");
		person.setAddress("Metropolis");
		person.setGender("Male");

	}

}
