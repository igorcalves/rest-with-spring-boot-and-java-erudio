package br.com.igor.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.igor.config.util.MediaType;
import br.com.igor.data.vo.v1.PersonVO;
import br.com.igor.data.vo.v2.PersonVOV2;
import br.com.igor.services.PersonServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
  
@RestController
@RequestMapping("/person/v1")
@Tag(name = "people", description = "Endpoints for Managing People ")
public class PersonController {

	@Autowired
	PersonServices service;
	
	@PostMapping(produces = {
			MediaType.APPLICATION_JSON, 
			MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML}, 
				consumes ={ 
						MediaType.APPLICATION_JSON, 
						MediaType.APPLICATION_XML,
						MediaType.APPLICATION_YML})
	@Operation(summary = "Adds a new  People in JSON, XML or YML", description = "Adds a new  People in JSON, XML or YML",
	tags = {"People"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = PersonVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400",content = {@Content}),
			@ApiResponse(description = "Unauthorized", responseCode = "401",content = {@Content}),
			@ApiResponse(description = "Internal Error", responseCode = "500",content = {@Content})
	})
	public PersonVO create(@RequestBody PersonVO person) {
		return service.create(person);
	}
	
	

	@GetMapping(value = "/{id}", produces = {
			MediaType.APPLICATION_JSON, 
			MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML})
	@Operation(summary = "Finds a People", description = "Finds a Poeple",
	tags = {"People"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = PersonVO.class))),
			@ApiResponse(description = "No Content", responseCode = "204",content = {@Content}),
			@ApiResponse(description = "Bad Request", responseCode = "400",content = {@Content}),
			@ApiResponse(description = "Unauthorized", responseCode = "401",content = {@Content}),
			@ApiResponse(description = "Not Found", responseCode = "404",content = {@Content}),
			@ApiResponse(description = "Internal Error", responseCode = "500",content = {@Content})
	})
	public PersonVO findById(@PathVariable(value = "id") Long id) {
		return service.findById(id);
	}

	@GetMapping(produces = {
			MediaType.APPLICATION_JSON, 
			MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML})
	@Operation(summary = "Finds all People", description = "Finds All Poeple",
	tags = {"People"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = {
					 @Content(
							 mediaType = "application/json",
							 array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))
							 )
				}),
			@ApiResponse(description = "No Content", responseCode = "204",content = {@Content}),
			@ApiResponse(description = "Bad Request", responseCode = "400",content = {@Content}),
			@ApiResponse(description = "Unauthorized", responseCode = "401",content = {@Content}),
			@ApiResponse(description = "Not Found", responseCode = "404",content = {@Content}),
			@ApiResponse(description = "Internal Error", responseCode = "500",content = {@Content})
	})
	public List<PersonVO> findALL() {
		return service.findAll();
	}

	@PutMapping(produces = {
			MediaType.APPLICATION_JSON, 
			MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML}, 
				consumes ={ 
						MediaType.APPLICATION_JSON, 
						MediaType.APPLICATION_XML,
						MediaType.APPLICATION_YML})
	@Operation(summary = "Update  a People by parsing in a JSON ,XML or YML", description = "Update  a People by parsing in a JSON ,XML or YML",
	tags = {"People"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = PersonVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400",content = {@Content}),
			@ApiResponse(description = "Unauthorized", responseCode = "401",content = {@Content}),
			@ApiResponse(description = "Not Found", responseCode = "404",content = {@Content}),
			@ApiResponse(description = "Internal Error", responseCode = "500",content = {@Content})
	})
	public PersonVO update(@RequestBody PersonVO person) {
		return service.update(person);
	}

	@DeleteMapping(value = "/{id}")
	@Operation(summary = "delete a People by id", description = "delete a Poeple by id",
	tags = {"People"},
	responses = {
			@ApiResponse(description = "No Content", responseCode = "204",content = {@Content}),
			@ApiResponse(description = "Bad Request", responseCode = "400",content = {@Content}),
			@ApiResponse(description = "Unauthorized", responseCode = "401",content = {@Content}),
			@ApiResponse(description = "Not Found", responseCode = "404",content = {@Content}),
			@ApiResponse(description = "Internal Error", responseCode = "500",content = {@Content})
	})
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
//	@PostMapping(value = "/v2",produces = {
//			MediaType.APPLICATION_JSON, 
//			MediaType.APPLICATION_XML,
//			MediaType.APPLICATION_YML}, 
//				consumes ={ 
//						MediaType.APPLICATION_JSON, 
//						MediaType.APPLICATION_XML
//						,MediaType.APPLICATION_YML})
//	@Operation(summary = "Finds a People", description = "Finds a Poeple",
//	tags = {"People"},
//	responses = {
//			@ApiResponse(description = "Success", responseCode = "200",
//					content = @Content(schema = @Schema(implementation = PersonVO.class))),
//			@ApiResponse(description = "No Content", responseCode = "204",content = {@Content}),
//			@ApiResponse(description = "Bad Request", responseCode = "400",content = {@Content}),
//			@ApiResponse(description = "Unauthorized", responseCode = "401",content = {@Content}),
//			@ApiResponse(description = "Not Found", responseCode = "404",content = {@Content}),
//			@ApiResponse(description = "Internal Error", responseCode = "500",content = {@Content})
//	})
//	public PersonVOV2 createV2(@RequestBody PersonVOV2 person) {
//		return service.createV2(person);
//	}