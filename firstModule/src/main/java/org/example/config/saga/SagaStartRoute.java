package org.example.config.saga;

import org.apache.camel.builder.RouteBuilder;

public class SagaStartRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:startOrderProcessing")
                .log("Starting Order Processing")
                .setHeader("sagaId", simple("${exchangeId}"))
                .setHeader("sagaAction", constant("start"))
                .to("saga:orderProcessingSaga")
                .log("Order Processing Started");
    }
}
