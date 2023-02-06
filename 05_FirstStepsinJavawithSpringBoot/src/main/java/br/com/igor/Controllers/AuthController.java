package br.com.igor.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.igor.data.vo.v1.security.AccountCredentialsVO;
import br.com.igor.services.AuthServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication EndPoint")
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	AuthServices authServices;
	
	@SuppressWarnings("rawtypes")
	@Operation(summary = "Authenticates a useer and returns a token")
	@PostMapping("/signin")
	public ResponseEntity signin(@RequestBody AccountCredentialsVO data ) {
		if (checkIFParamIsNotNull(data)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
		
		var token = authServices.signin(data);
		if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
		return token;
	}

	private boolean checkIFParamIsNotNull(AccountCredentialsVO data) {
		return data == null || data.getUsername() == null || data.getUsername().isBlank() 
		|| data.getPassword() == null || data.getPassword().isBlank();
	}

}
