package br.com.igor.seriealization.converter;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

public class YamilJackson2HttpMessasgeConverter extends AbstractJackson2HttpMessageConverter{

	public YamilJackson2HttpMessasgeConverter() {
		super(new YAMLMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL),MediaType.parseMediaType("application/x-yaml"));
	}
	

}


