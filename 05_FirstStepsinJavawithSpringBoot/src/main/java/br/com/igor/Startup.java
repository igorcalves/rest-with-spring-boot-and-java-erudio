package br.com.igor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class Startup {
	
	 @Autowired
//	  private ObjectMapper objectMapper;

	public static void main(String[] args) {
		SpringApplication.run(Startup.class, args);
//        Map<String, PasswordEncoder> encoders = new HashMap<>();
//        
//        Pbkdf2PasswordEncoder pbkdf2Encoder =
//        		new Pbkdf2PasswordEncoder(
//    				"", 8, 185000,
//    				SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
//        
//        encoders.put("pbkdf2", pbkdf2Encoder);
//        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
//        passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);
//        
//        String result1 = passwordEncoder.encode("4044");
//        String result2 = passwordEncoder.encode("4044");
//        System.out.println("My hash result1 " + result1);
//        System.out.println("My hash result2 " + result2);
		
			}

//	@PostConstruct
//	  public void setUp() {
//	    objectMapper.registerModule(new JavaTimeModule());
//	  }
}
