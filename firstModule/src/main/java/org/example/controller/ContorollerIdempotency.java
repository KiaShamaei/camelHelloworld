package org.example.controller;

import org.apache.camel.ProducerTemplate;
import org.example.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/idem")
public class ContorollerIdempotency {
    @Autowired
    ProducerTemplate producerTemplate;


    @GetMapping("/{id}")
    public String m1(@PathVariable String id){
        String message = "Hello, world!";
        String messageId = "header" + id;
        producerTemplate.sendBodyAndHeader("direct:idempotency", message, "MessageId", messageId);
        return "success";
    }

}
