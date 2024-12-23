package org.example.config;
import org.apache.camel.builder.RouteBuilder;


public class CamelRouterSedaAndDirect extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        //persist queue with 10000
//        from("direct:topic1")
//                .log(" seda in camel get message ")
//                .process(t->{
//                    //set for before process
//                    var body = (String) t.getIn().getBody().toString();})
//                .to("kafka:topic1?brokers=localhost:9092&groupId=task-group");
        from("seda:topic1")
                .log(" seda in camel get message ")
                .process(t->{
                    //set for before process
                    var body = (String) t.getIn().getBody().toString();})
                .to("kafka:topic1?brokers=localhost:9092&groupId=task-group");



    }

}
