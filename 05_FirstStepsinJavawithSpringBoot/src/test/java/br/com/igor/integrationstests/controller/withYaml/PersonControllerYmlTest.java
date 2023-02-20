package br.com.igor.integrationstests.controller.withYaml;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.igor.configs.TestConfigs;
import br.com.igor.data.vo.v1.security.TokenVO;
import br.com.igor.integrationstests.controller.withYml.mapper.YmlMapper;
import br.com.igor.integrationstests.vo.AccountCredentialsVO;
import br.com.igor.integrationstests.vo.PersonVO;
import br.com.igor.integrationtest.testcontainers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerYmlTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static YmlMapper objectMapper;

	private static PersonVO person;

	@BeforeAll
	public static void setup() {
		objectMapper = new YmlMapper();
		person = new PersonVO();

	}

	@Test
	@Order(0)
	public void authorization() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsVO user = new AccountCredentialsVO("Igor", "4044");
		
		RequestSpecification specification1 = new RequestSpecBuilder()
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
			.build();
		
		var accessToken = given()
				.spec(specification1)
				.config(RestAssuredConfig
						.config()
						.encoderConfig(EncoderConfig.encoderConfig()
							.encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.basePath("/auth/signin")
					.port(TestConfigs.SERVER_PORT)
					.contentType(TestConfigs.CONTENT_TYPE_YML)
					.accept(TestConfigs.CONTENT_TYPE_YML)
				.body(user,objectMapper)
					.when()
				.post()
					.then()
						.statusCode(200)
							.extract()
							.body()
								.as(TokenVO.class,objectMapper)
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

		PersonVO personEntity = given().spec(specification)
				.config(RestAssuredConfig
						.config()
						.encoderConfig(EncoderConfig.encoderConfig()
							.encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_IGOR)
				.body(person,objectMapper)
					.when()
					.post()
				.then()
				.statusCode(200)
					.extract()
					.body()
						.as(PersonVO.class,objectMapper);
		person = personEntity;


		assertNotNull(personEntity);
		assertNotNull(personEntity.getId());
		assertNotNull(personEntity.getFirstName());
		assertNotNull(personEntity.getLastName());
		assertNotNull(personEntity.getGender());

		assertTrue(personEntity.getId() > 0);

		assertEquals("Super", personEntity.getFirstName());
		assertEquals("Man", personEntity.getLastName());
		assertEquals("Metropolis", personEntity.getAddress());
		assertEquals("Male", personEntity.getGender());
	}

	

	@Test
	@Order(2)
	public void testFindById() throws Exception {
		mockPerson();

		var content = given().spec(specification)
				.config(RestAssuredConfig
						.config()
						.encoderConfig(EncoderConfig.encoderConfig()
							.encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_IGOR)
					.pathParam("id", person.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(200)
						.extract()
							.body()
								.as(PersonVO.class,objectMapper);



		assertNotNull(content);
		assertNotNull(content.getId());
		assertNotNull(content.getFirstName());
		assertNotNull(content.getLastName());
		assertNotNull(content.getGender());

		assertTrue(content.getId() > 0);

		assertEquals("Super", content.getFirstName());
		assertEquals("Man", content.getLastName());
		assertEquals("Metropolis", content.getAddress());
		assertEquals("Male", content.getGender());
	}

	@Test
	@Order(3)
	public void testUpdate() throws Exception {
		PersonVO OriginPerson = given().spec(specification)
				.config(RestAssuredConfig
						.config()
						.encoderConfig(EncoderConfig.encoderConfig()
							.encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_IGOR)
					.pathParam("id", person.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(200)
						.extract()
							.body()
								.as(PersonVO.class,objectMapper);
		
		PersonVO newPutPerson = OriginPerson;
		
		newPutPerson.setFirstName("Wonder-Woman");
		newPutPerson.setLastName("");
		newPutPerson.setAddress("Metropolis");
		newPutPerson.setGender("Female");
		
		var persistedNewPutPerson = given().spec(specification)
				.config(RestAssuredConfig
						.config()
						.encoderConfig(EncoderConfig.encoderConfig()
							.encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_IGOR)
				.body(newPutPerson,objectMapper)
					.when()
					.put()
				.then()
				.statusCode(200)
					.extract()
					.body()
					.as(PersonVO.class,objectMapper);
		

		
		assertNotNull(persistedNewPutPerson);
		assertNotNull(persistedNewPutPerson.getId());
		assertNotNull(persistedNewPutPerson.getFirstName());
		assertNotNull(persistedNewPutPerson.getLastName());
		assertNotNull(persistedNewPutPerson.getGender());
		
		assertEquals(OriginPerson.getId(), persistedNewPutPerson.getId());
		
		assertEquals(persistedNewPutPerson.getFirstName(), "Wonder-Woman");
		assertEquals(persistedNewPutPerson.getLastName(), "");
		assertEquals(persistedNewPutPerson.getAddress(), "Metropolis");
		assertEquals(persistedNewPutPerson.getGender(),"Female");
	}

	@Test
	@Order(4)
	public void delete() throws Exception {
		given().spec(specification)
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
				.config(RestAssuredConfig
						.config()
						.encoderConfig(EncoderConfig.encoderConfig()
							.encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
				.when()
				.get()
				.then()
				.statusCode(200)
				.extract()
				.body()
				.as(PersonVO[].class,objectMapper);
		
		List<PersonVO> people  = Arrays.asList(content);
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
				.config(RestAssuredConfig
						.config()
							.encoderConfig(EncoderConfig.encoderConfig()
									.encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
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
