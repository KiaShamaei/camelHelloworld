package org.example;


import lombok.extern.log4j.Log4j2;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
@Log4j2
public class App implements CommandLineRunner
{
    @Autowired
    KafkaTemplate kafkaTemplate;
    @Autowired
    ProducerTemplate producerTemplate;
    public static void main( String[] args )
    {
        System.out.println( "application before run------------------------->" );
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("------------------>application run");
//        producerTemplate.sendBody("direct:topic1", "My Event");
//        kafkaTemplate.send("topic1", "taskId", "test123434");
    }
}
