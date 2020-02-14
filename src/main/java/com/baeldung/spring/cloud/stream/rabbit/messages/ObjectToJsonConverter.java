package com.baeldung.spring.cloud.stream.rabbit.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ObjectToJsonConverter {
	
	@Autowired
	ObjectMapper mapper;
	
	public String convertToJson(Object object) throws JsonProcessingException {
		return mapper.writeValueAsString(object);
	}
}
