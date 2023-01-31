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
import br.com.igor.data.vo.v1.BookVO;
import br.com.igor.data.vo.v1.PersonVO;
import br.com.igor.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/book/v1")
@Tag(name = "Book", description = "Endpoints for Managing Books ")
public class BookController {
	
	@Autowired
	BookService service;
	
	@PostMapping(produces = {
			MediaType.APPLICATION_JSON, 
			MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML}, 
				consumes ={ 
						MediaType.APPLICATION_JSON, 
						MediaType.APPLICATION_XML,
						MediaType.APPLICATION_YML})
	@Operation(summary = "Adds a new Book in JSON, XML or YML", description = "Adds a new  People in JSON, XML or YML",
	tags = {"People"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = PersonVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400",content = {@Content}),
			@ApiResponse(description = "Unauthorized", responseCode = "401",content = {@Content}),
			@ApiResponse(description = "Internal Error", responseCode = "500",content = {@Content})
	})
	public BookVO create(@RequestBody BookVO book) {
		return service.create(book);
	}
	
	@GetMapping(value = "/{id}", produces = {
			MediaType.APPLICATION_JSON, 
			MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML})
	@Operation(summary = "Finds a Book", description = "Finds a Book",
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
	public BookVO findById(@PathVariable(value = "id") Integer id) {
		return service.findById(id);
	}
	
	@GetMapping(produces = {
			MediaType.APPLICATION_JSON, 
			MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML})
	@Operation(summary = "Finds all Books", description = "Finds All Books",
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
	public List<BookVO> findALL() {
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
	@Operation(summary = "Update  a Book by parsing in a JSON ,XML or YML", description = "Update  a Book by parsing in a JSON ,XML or YML",
	tags = {"People"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = PersonVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400",content = {@Content}),
			@ApiResponse(description = "Unauthorized", responseCode = "401",content = {@Content}),
			@ApiResponse(description = "Not Found", responseCode = "404",content = {@Content}),
			@ApiResponse(description = "Internal Error", responseCode = "500",content = {@Content})
	})
	public BookVO update(@RequestBody BookVO book) {
		return service.update(book);
	}
	
	@DeleteMapping(value = "/{id}")
	@Operation(summary = "delete a Book by id", description = "delete a Book by id",
	tags = {"People"},
	responses = {
			@ApiResponse(description = "No Content", responseCode = "204",content = {@Content}),
			@ApiResponse(description = "Bad Request", responseCode = "400",content = {@Content}),
			@ApiResponse(description = "Unauthorized", responseCode = "401",content = {@Content}),
			@ApiResponse(description = "Not Found", responseCode = "404",content = {@Content}),
			@ApiResponse(description = "Internal Error", responseCode = "500",content = {@Content})
	})
	public ResponseEntity<?> delete(@PathVariable(value = "id") Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}

