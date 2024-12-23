package org.example.controller;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.example.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/seda")
public class ControllerKafkaWithSedaTest {
    @Autowired
    ProducerTemplate producerTemplate;

    @Autowired
    CamelContext camelContext;
    @Autowired
    KafkaService kafkaService;

    @GetMapping("/m1/{id}")
    public String m1(@PathVariable String id){

          producerTemplate.asyncSendBody("direct:topic1", "My Event from controller " + id);
//        CompletableFuture<Object> t= producerTemplate.asyncSendBody("direct:topic1", "My Event from controller " + id);
//        CompletableFuture<Object> t= producerTemplate.asyncSendBody("direct:topic1", "My Event from controller " + id);
//        producerTemplate.sendBody("seda:topic1", "My Event" + id);
        return " test api read... " + id;
    }
    @GetMapping("/m2/{id}")
    public String m2(@PathVariable String id){
//        CompletableFuture<Object> t= producerTemplate.asyncSendBody("direct:topic1", "My Event from controller " + id);
        kafkaService.send("topic1","id",id);
        return " test api read... " + id;
    }
    @GetMapping("/m3/{id}")
    public String m3(@PathVariable String id){
        producerTemplate.sendBody("seda:topic1", "My Event" + id);
        return " this is send to seda" + id;
    }
}
