package br.com.igor.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.igor.seriealization.converter.YamilJackson2HttpMessasgeConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	

	

	private static final MediaType MEDIA_TYPE_APPLICATION_YML = MediaType.valueOf("application/x-yaml");
	
	@Value("${cors.originPatterns:default}")
	private String corsoriginPatterns = "";

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new YamilJackson2HttpMessasgeConverter());
	}
	
	private ObjectMapper objectMapper() {
		var builder = new Jackson2ObjectMapperBuilder();
		builder.modules(new JavaTimeModule());
		builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return builder.build();
	}
	
	
	
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		var allowedOrigins = corsoriginPatterns.split(",");
		registry.addMapping("/**")
//			.allowedMethods("GET", "POST", "PUT") // configura o cors por method
			.allowedMethods("*") // configura para serem todos
			.allowedOrigins(allowedOrigins)
		.allowCredentials(true);
		
	}
	


	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		
//				reference to query parameter
//				configurer.favorParameter(true)
//				.parameterName("mediaType").ignoreAcceptHeader(true)
//				.useRegisteredExtensionsOnly(false)
//				.ignoreAcceptHeader(false)
//				.defaultContentType(MediaType.APPLICATION_JSON)
//					.mediaType("json", MediaType.APPLICATION_JSON)
//					.mediaType("xml", MediaType.APPLICATION_XML);
		
				//via header
				configurer.favorParameter(false)
				.ignoreAcceptHeader(false)
				.useRegisteredExtensionsOnly(false)
				.defaultContentType(MediaType.APPLICATION_JSON)
					.mediaType("json", MediaType.APPLICATION_JSON)
					.mediaType("xml", MediaType.APPLICATION_XML)
					.mediaType("yml", MEDIA_TYPE_APPLICATION_YML);
	}
	
	
	
	
	

}
