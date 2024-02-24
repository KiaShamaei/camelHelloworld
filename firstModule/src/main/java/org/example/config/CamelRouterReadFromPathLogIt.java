package org.example.config;
import lombok.extern.log4j.Log4j2;
import org.apache.camel.builder.RouteBuilder;

@Log4j2
public class CamelRouterReadFromPathLogIt extends RouteBuilder {

    //read from file path and log it
    @Override
    public void configure() throws Exception {
        from("file:C:/blue/testCamel/firstModule/src/data?noop=false")
                .split()
                .tokenize("\n")
                .log("Read file ---------------------------------------->: ${file:name} with content: ${body}")
                .process(t->{
                    log.info("=========================> {}" , t.getIn().getBody(String.class));
                });

    }

}


