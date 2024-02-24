package org.example.config;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.camel.builder.RouteBuilder;
import org.example.service.CustomBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log4j2

public class CamelRouterJpa extends RouteBuilder {

    //read from file path and log it
    @Override
    public void configure() throws Exception {
        from("jpa:org.example.domain.UserEntity?namedQuery=step1")
//                .bean(customBean,"changeState")
                .process(t->{
                    log.info("==========kia========>{}" ,t.getIn().getBody());

                });


    }

}


