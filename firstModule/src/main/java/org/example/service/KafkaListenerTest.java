package org.example.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.example.dto.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class KafkaListenerTest {

    private final ObjectMapper objectMapper;

    public KafkaListenerTest(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @RetryableTopic(attempts = "4",
            backoff = @Backoff(delay = 30000L, multiplier = 2),
            retryTopicSuffix = "_RETRY",
            dltTopicSuffix = "_DLT")
    @KafkaListener(autoStartup = "true",
            topics = "topic2",
            groupId = "task-group")
    public void consume(String value) throws InterruptedException {
        log.info("listener get value  : " + value);
        if(value.equals("1")){
            throw new RuntimeException("listener get error");
        }
        log.info(String.format(" listener get value and process  : " + value));
    }
    @KafkaListener(autoStartup = "true",
            topics = "topic1",
            groupId = "task-group")
    public void consume2(String value) throws InterruptedException {
        log.info("--------------> AAA listener from camel seda get this value  : " + value);
    }
    @KafkaListener(autoStartup = "true",
            topics = "topic3",
            groupId = "task-group")
    public void consume3(String value) throws InterruptedException {
        log.info("--------------->listener from camel file 3 get this value  : " + value);
    }
    @KafkaListener(autoStartup = "true",
            topics = "topic4",
            groupId = "task-group")
    public void consume4(String value) throws InterruptedException, JsonProcessingException {
        log.info("----------------->listener from camel file 4 get  " );
        User user = objectMapper.readValue(value, User.class);
        log.info("----------------->listener from camel file 4 get {} " , user
        );
    }
    @KafkaListener(autoStartup = "true",
            topics = "topic5",
            groupId = "task-group")
    public void consume5(String value) throws InterruptedException, JsonProcessingException {
        User user = objectMapper.readValue(value, User.class);
        log.info("----------------->listener from camel file 4 get {} " , user
        );
    }

    @KafkaListener(autoStartup = "true",
            topics = "kia",
            groupId = "task-group")
    public void consumeKia(String value) throws InterruptedException, JsonProcessingException {
        User user = objectMapper.readValue(value, User.class);
        log.info("----------------->listener from camel file kia get {} " , user.getName());
    }

    @KafkaListener(autoStartup = "true",
            topics = "other",
            groupId = "task-group")
    public void consumeOther(String value) throws InterruptedException, JsonProcessingException {
        log.info("----------------->listener from camel file other get  " );
        User user = objectMapper.readValue(value, User.class);
        log.info("----------------->listener from camel file other get {} " , user.getName()
        );
    }

}
