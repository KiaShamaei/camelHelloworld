package org.example.config;
import org.apache.camel.builder.RouteBuilder;


public class CamelRouter2 extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        //persist queue with 1000
        from("seda:topic1")
                .log(" seda in camel get message ")
                .process(t->{
                    //set for before process
                    var body = (String) t.getIn().getBody().toString();})
                .to("kafka:topic1?brokers=localhost:9092&groupId=task-group");



    }

}
