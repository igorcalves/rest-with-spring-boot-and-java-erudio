package br.com.igor.integrationstests.controller.withXml;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import br.com.igor.configs.TestConfigs;
import br.com.igor.data.vo.v1.security.TokenVO;
import br.com.igor.integrationstests.vo.AccountCredentialsVO;
import br.com.igor.integrationstests.vo.PersonVO;
import br.com.igor.integrationtest.testcontainers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerXmlTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static XmlMapper ObjectMapper;

	private static PersonVO person;

	@BeforeAll
	public static void setup() {
		ObjectMapper = new XmlMapper();
		ObjectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		person = new PersonVO();

	}

	@Test
	@Order(0)
	public void authorization() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsVO user = new AccountCredentialsVO("Igor", "4044");
		
		var accessToken = given()
				.basePath("/auth/signin")
					.port(TestConfigs.SERVER_PORT)
					.contentType(TestConfigs.CONTENT_TYPE_XML)
					.accept(TestConfigs.CONTENT_TYPE_XML)
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
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}

	@Test
	@Order(1)
	public void testCreate() throws Exception {
		mockPerson();

		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_IGOR)
				.body(person)
					.when()
					.post()
				.then()
				.statusCode(200)
					.extract()
					.body()
						.asString();

		PersonVO createPerson = ObjectMapper.readValue(content, PersonVO.class);
		person = createPerson;

		assertNotNull(createPerson);
		assertNotNull(createPerson.getId());
		assertNotNull(createPerson.getFirstName());
		assertNotNull(createPerson.getLastName());
		assertNotNull(createPerson.getGender());

		assertTrue(createPerson.getId() > 0);

		assertEquals("Super", createPerson.getFirstName());
		assertEquals("Man", createPerson.getLastName());
		assertEquals("Metropolis", createPerson.getAddress());
		assertEquals("Male", createPerson.getGender());
	}

	

	@Test
	@Order(2)
	public void testFindById() throws Exception {
		mockPerson();

		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_IGOR)
					.pathParam("id", person.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(200)
						.extract()
						.body()
							.asString();

		PersonVO createPerson = ObjectMapper.readValue(content, PersonVO.class);
		person = createPerson;

		assertNotNull(createPerson);
		assertNotNull(createPerson.getId());
		assertNotNull(createPerson.getFirstName());
		assertNotNull(createPerson.getLastName());
		assertNotNull(createPerson.getGender());

		assertTrue(createPerson.getId() > 0);

		assertEquals("Super", createPerson.getFirstName());
		assertEquals("Man", createPerson.getLastName());
		assertEquals("Metropolis", createPerson.getAddress());
		assertEquals("Male", createPerson.getGender());
	}

	@Test
	@Order(3)
	public void testUpdate() throws Exception {
		mockPerson();
		
		
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_IGOR)
				.pathParam("id", person.getId())
				.when()
				.get("{id}")
				.then()
				.statusCode(200)
				.extract()
				.body()
				.asString();
		
		PersonVO oldPerson = ObjectMapper.readValue(content, PersonVO.class);
		person = oldPerson;
		
		person.setFirstName("Wonder-Woman");
		person.setLastName("");
		person.setAddress("Metropolis");
		person.setGender("Female");
		
		var newContent = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.header(TestConfigs.HEADER_PARAM_ORIGIN,TestConfigs.ORIGIN_IGOR)
				.body(person)
					.when()
					.put()
				.then()
				.statusCode(200)
					.extract()
					.body()
						.asString();
		
		PersonVO newPerson = ObjectMapper.readValue(newContent, PersonVO.class);
		PersonVO newPersonPut = newPerson;
		
		assertNotNull(newPersonPut);
		assertNotNull(newPersonPut.getId());
		assertNotNull(newPersonPut.getFirstName());
		assertNotNull(newPersonPut.getLastName());
		assertNotNull(newPersonPut.getGender());
		
		assertEquals(oldPerson.getId(), newPerson.getId());
		
		assertEquals(newPersonPut.getFirstName(), "Wonder-Woman");
		assertEquals(newPersonPut.getLastName(), "");
		assertEquals(newPersonPut.getAddress(), "Metropolis");
		assertEquals(newPersonPut.getGender(),"Female");
	}

	@Test
	@Order(4)
	public void delete() throws Exception {
		given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
					.pathParam("id", person.getId())
					.when()
					.delete("{id}")
				.then()
					.statusCode(204);
	}
	
	@Test
	@Order(5)
	public void findALL() throws Exception {
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.when()
				.get()
				.then()
				.statusCode(200)
				.extract()
				.body()
					.asString();
				//.as(new TypeRef<List<PersonVO>>() {});
		
		List<PersonVO> people = ObjectMapper.readValue(content, new TypeReference<List<PersonVO>>() {});
		PersonVO foundPersonOne = people.get(0);
		
		assertNotNull(foundPersonOne);
		assertNotNull(foundPersonOne.getId());
		assertNotNull(foundPersonOne.getFirstName());
		assertNotNull(foundPersonOne.getLastName());
		assertNotNull(foundPersonOne.getGender());
		
		assertTrue(foundPersonOne.getId() > 0);
		
		assertEquals("Ayrton", foundPersonOne.getFirstName());
		assertEquals("Senna", foundPersonOne.getLastName());
		assertEquals("São Paulo", foundPersonOne.getAddress());
		assertEquals("Male", foundPersonOne.getGender());
	}
	@Order(6)
	public void findAllWithoutToken() throws Exception {
		
		RequestSpecification specificationWithoutToken = specification = new RequestSpecBuilder()
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		given().spec(specificationWithoutToken)
		.contentType(TestConfigs.CONTENT_TYPE_XML)
				.when()
				.get()
				.then()
				.statusCode(403);
	}
	private void mockPerson() {
		person.setFirstName("Super");
		person.setLastName("Man");
		person.setAddress("Metropolis");
		person.setGender("Male");

	}

}
