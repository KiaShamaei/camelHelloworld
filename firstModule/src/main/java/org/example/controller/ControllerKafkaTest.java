package org.example.controller;

import org.apache.camel.ProducerTemplate;
import org.example.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class ControllerKafkaTest {
    @Autowired
    ProducerTemplate producerTemplate;
    @Autowired
    KafkaService kafkaService;

    @GetMapping("/topic1/{id}")
    public String m1(@PathVariable String id){
        kafkaService.send("topic1","id",id);
        return " test api read... " + id;
    }
    @GetMapping("/topic2/{id}")
    public String m2(@PathVariable String id){
//        producerTemplate.asyncSendBody("direct:topic1", "My Event from controller " + id);
        producerTemplate.sendBody("seda:topic1", "My Event");
//        kafkaService.send("topic2","id",id);
        return " test api read... " + id;
    }
}
