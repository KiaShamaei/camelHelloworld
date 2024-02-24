package org.example.config;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePropertyKey;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;


public class CamelRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        //persist queue with 1000
        from("kafka:topic2?brokers=localhost:9092&groupId=task-group")
//        from("seda:topic1")
                .log("message recevie in camel ")
                .process(t->{
                    //set for before process
                    var body = (String) t.getIn().getBody().toString();
                    var retryCount = t.getIn().getHeader(Exchange.REDELIVERY_COUNTER, Integer.class);

                    if(body.equals("1")){
                        if(retryCount== 3){
                            log.info("put it in other queue");
                        }
                        else{
                            throw new RuntimeException("something happen in process");
                        }

                    }
                    Thread.sleep(1000);
                    log.info("the thread in use : {}" , Thread.currentThread().getName());
                    log.info("process comes here for --------------------------------> : {} {}", body , retryCount);

       })
                .errorHandler(defaultErrorHandler()
                        .onExceptionOccurred(p->{
                            //process if exception happen
                            log.info("process if exception goes here ...");})
                        .maximumRedeliveries(3)
                        .redeliveryDelay(1000)

                ).process(p->{
                    var body = (String) p.getIn().getBody().toString();
                    log.info("second process : {} " , body);
                });



    }

}
