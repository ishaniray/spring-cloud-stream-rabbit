package com.baeldung.spring.cloud.stream.rabbit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.spring.cloud.stream.rabbit.messages.ObjectToJsonConverter;
import com.baeldung.spring.cloud.stream.rabbit.model.LogMessage;
import com.fasterxml.jackson.core.JsonProcessingException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MyLoggerServiceApplication.class)
@DirtiesContext
public class MyLoggerApplicationIntegrationTest {

    @Autowired
    private Processor pipe;

    @Autowired
    private MessageCollector messageCollector;
    
    @Autowired
    ObjectToJsonConverter converter;

    @Test
    public void whenSendMessage_thenResponseShouldUpdateText() throws JsonProcessingException {
    	
    	LogMessage logMessage = new LogMessage("This is my message");
    	
        pipe.input()
            .send(MessageBuilder.withPayload(logMessage)
                .build());

        Object payload = messageCollector.forChannel(pipe.output())
            .poll()
            .getPayload();
        
        logMessage.setMessage(String.format("[1]: %s", logMessage.getMessage()));

        assertEquals(converter.convertToJson(logMessage), payload.toString());
    }
}
