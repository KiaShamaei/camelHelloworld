package org.example.controller;

import org.apache.camel.ProducerTemplate;
import org.example.dto.User;
import org.example.service.KafkaService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rabbit" , produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_JSON_VALUE )
public class ControllerRabbitMQTest {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostMapping ("/user")
    public String m1(@RequestBody User user){
        rabbitTemplate.convertAndSend("","queue-1",user);
        return " rabbit test user api read... " + user.getName();
    }
}
