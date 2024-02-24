import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class RabbitConfig {
    public static final String DIRECT_EXCHANGE_NAME = "direct-exchange";
    public static final String QUEUE_1_NAME = "queue-1";
    public static final String QUEUE_2_NAME = "queue-2";
    public static final String ROUTING_KEY_1 = "routing-key-1";
    public static final String ROUTING_KEY_2 = "routing-key-2";

    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE_NAME);
    }

    @Bean
    Queue queue1() {
        return new Queue(QUEUE_1_NAME);
    }

    @Bean
    Binding binding1(Queue queue1, DirectExchange directExchange) {
        return BindingBuilder.bind(queue1).to(directExchange).with(ROUTING_KEY_1);
    }
    @Bean
    Queue queue2() {
        return new Queue(QUEUE_2_NAME);
    }
    @Bean
    Binding binding2(Queue queue2, DirectExchange directExchange) {
        return BindingBuilder.bind(queue2).to(directExchange).with(ROUTING_KEY_2);
    }
}