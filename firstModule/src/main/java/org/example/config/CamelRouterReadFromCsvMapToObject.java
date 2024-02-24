package org.example.config;
import lombok.extern.log4j.Log4j2;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.CsvDataFormat;
import org.example.dto.User;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.CsvFormat;

@Log4j2
public class CamelRouterReadFromCsvMapToObject extends RouteBuilder {

    //read from file path make obj and put it to kafka topic log it
    @Override
    public void configure() throws Exception {
        CsvDataFormat csvDataFormat = new CsvDataFormat();
        csvDataFormat.setDelimiter(",");
        csvDataFormat.setSkipHeaderRecord("true");

        from("file:C:/blue/testCamel/firstModule/src/dataCsv?noop=false")
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
                .marshal()
                .json()
                .to("kafka:topic4?brokers=localhost:9092&groupId=task-group")
                .log("obj go to topic 4 with this body  ${body}");
    }

}


