package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class RabbitMqListener {
    private final ObjectMapper objectMapper;

    public RabbitMqListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = {"queue-1"})
    public void onUserRegistration(User message) throws JsonProcessingException {

//            User event = objectMapper.readValue(message, User.class);
            log.info("User Registration Event Received: {}", message);

    }
}





