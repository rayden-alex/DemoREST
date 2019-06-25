package com.samsolution.demo.rabbit;

import com.samsolution.demo.BaseIntegrationTestConfiguration;
import com.samsolution.demo.config.RabbitMQConfig;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

// Simple @Configuration classes are excluded from scanning in test ApplicationContext,
// so we have to use @TestConfiguration
@TestConfiguration

//Create additional beans in SpringContext
@Import({BaseIntegrationTestConfiguration.class, RabbitMQConfig.class})
public class RabbitMessageSenderTestConfig {
}
