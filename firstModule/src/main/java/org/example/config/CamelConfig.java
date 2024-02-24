package org.example.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.jpa.JpaComponent;
import org.apache.camel.component.micrometer.messagehistory.MicrometerMessageHistoryFactory;
import org.apache.camel.component.micrometer.routepolicy.MicrometerRoutePolicyFactory;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.dataformat.BindyDataFormat;
import org.apache.camel.model.dataformat.CsvDataFormat;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

@Configuration
public class CamelConfig {
    @Autowired
    private EntityManagerFactory entityManager;

    @Bean
    public JpaComponent jpaComponent(EntityManagerFactory entityManagerFactory){
          JpaComponent jpaComponent = new JpaComponent();
          jpaComponent.setEntityManagerFactory( entityManagerFactory);
          return jpaComponent;
     }


    // Other configuration and route setup methods...

    @Bean
    public CamelContext camelContext() throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        ctx.addComponent("jpa" , jpaComponent(entityManager));
        ctx.addRoutes(new CamelRouter());
        ctx.addRoutes(new CamelRouter2());
        ctx.addRoutes(new CamelRouterReadFromPathLogIt());
        ctx.addRoutes(new CamelRouterReadFromCsvMapToObject());
        ctx.addRoutes(new CamelRouterReadFromCsvMapToObjectWithChoice());
        ctx.addRoutes(new CamelRouterJpa());
        ctx.start();
        return ctx;

    }
    @Bean
    public ProducerTemplate producerTemplate() throws Exception {
        var ctx = camelContext();
        ProducerTemplate producer = ctx.createProducerTemplate();
        return producer;

    }
    @Bean
    public CamelContextConfiguration camelContextConfiguration() {

        return new CamelContextConfiguration() {
            @Override
            public void beforeApplicationStart(CamelContext camelContext) {
                camelContext.addRoutePolicyFactory(new MicrometerRoutePolicyFactory());
                camelContext.setMessageHistoryFactory(new MicrometerMessageHistoryFactory());
            }

            @Override
            public void afterApplicationStart(CamelContext camelContext) {

            }
        };
    }

}
