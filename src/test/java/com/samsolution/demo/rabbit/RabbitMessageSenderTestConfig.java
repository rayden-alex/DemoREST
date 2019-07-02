package com.samsolution.demo.rabbit;

import com.samsolution.demo.BaseIntegrationTestConfiguration;
import com.samsolution.demo.config.RabbitMQConfig;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

// Simple @Configuration classes are excluded from scanning in test ApplicationContext,
// so we have to use @TestConfiguration
@TestConfiguration

//Create additional beans in SpringContext
@Import({BaseIntegrationTestConfiguration.class, RabbitMQConfig.class})
@TestPropertySource(properties = "my.rabbit.disable=false")
public class RabbitMessageSenderTestConfig {
//    @Bean
//    @Primary
//    public RabbitProperties rabbitProperties(){
//        RabbitProperties properties = new RabbitProperties();
//        properties.setHost(RABBIT_MQ_CONTAINER.getContainerIpAddress());
//        properties.setPort(RABBIT_MQ_CONTAINER.getMappedPort(5672));
//        return properties;
//    }
}
