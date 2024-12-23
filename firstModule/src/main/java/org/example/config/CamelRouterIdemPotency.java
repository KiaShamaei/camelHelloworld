package org.example.config;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spi.IdempotentRepository;
import org.apache.camel.support.processor.idempotent.MemoryIdempotentRepository;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class CamelRouterIdemPotency extends RouteBuilder {

    private final IdempotentRepository idempotentRepository;

    public CamelRouterIdemPotency() {
        Map<String , Object> processedMessages = new HashMap<>();
        idempotentRepository = new MemoryIdempotentRepository(processedMessages);
    }

    @Override
    public void configure() throws Exception {
        from("direct:idempotency")
                .log("message recevie in camel  idem ${body}")
                .idempotentConsumer(header("messageId"), idempotentRepository())
                .skipDuplicate(true)
                .to("direct:processMessage");

        from("direct:processMessage")
                .log("Processing from not idempotency: ${body}");

//        from("netty:localhost:get")
//                .saga()
//                .option("messageId", header("messageId"))
//                .compensation("direct:compensate")
//                .to("direct:processMessage");
    }

    private IdempotentRepository idempotentRepository() {
        // Implement your custom IdempotentRepository here
        // This example uses a simple in-memory HashSet you can complete part in KiarashShamaii github
        return idempotentRepository;
    }
}
