package org.example.config;
import lombok.extern.log4j.Log4j2;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.CsvDataFormat;
import org.example.dto.User;

@Log4j2
public class CamelRouterReadFromCsvMapToObjectWithChoice extends RouteBuilder {

    //read from file path make obj and put it to kafka topic log it
    @Override
    public void configure() throws Exception {
        CsvDataFormat csvDataFormat = new CsvDataFormat();
        csvDataFormat.setDelimiter(",");
        csvDataFormat.setSkipHeaderRecord("true");

        from("file:C:/blue/testCamel/firstModule/src/dataCsvChoice?noop=false")
                .unmarshal(csvDataFormat)
                .split(body())
                .streaming()
                .process(t->{
                    log.info("=========================> {}" , t.getIn().getBody(String[].class));
                    String[] tBody = t.getIn().getBody(String[].class);
                    User user = new User();
                    user.setName(tBody[0]);
                    user.setEmail(tBody[1]);
                    user.setMobileNumber(tBody[2]);
                    t.getIn().setBody(user);
                })
                .choice()
                .when(t->t.getIn().getBody(User.class).getName().contains("kia"))
                .marshal()
                .json()
                .to("kafka:kia?brokers=localhost:9092&groupId=task-group")
                .when(t-> !t.getIn().getBody(User.class).getName().contains("kia") && !t.getIn().getBody(User.class).getName().contains("") )
                .marshal()
                .json()
                .to("kafka:other?brokers=localhost:9092&groupId=task-group");

    }

}


