package com.samsolution.demo.rabbit;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@SuppressWarnings("unused")
public class RabbitTestContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
//            TestPropertyValues values = TestPropertyValues.of(
//                    "spring.rabbitmq.host=" + RABBIT_MQ_CONTAINER.getContainerIpAddress(),
//                    "spring.rabbitmq.port=" + RABBIT_MQ_CONTAINER.getMappedPort(5672),
//                    "spring.rabbitmq.user=" + "guest",
//                    "spring.rabbitmq.password=" + "guest",
//                    "spring.rabbitmq.virtual-host=" + "/"
//            );
//            values.applyTo(configurableApplicationContext);
    }

}