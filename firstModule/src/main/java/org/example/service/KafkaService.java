package org.example.service;

import lombok.extern.log4j.Log4j2;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@Log4j2
public class KafkaService {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topicName, String key, String value) {
        log.info("service kafka call with key {}  and value :  {}" ,key, value );
        kafkaTemplate.send(topicName, key, value);
    }
}
