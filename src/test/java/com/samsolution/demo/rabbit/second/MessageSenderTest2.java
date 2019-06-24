package com.samsolution.demo.rabbit.second;

import com.samsolution.demo.BaseIntegrationTestConfiguration;
import com.samsolution.demo.config.RabbitMQConfig;
import com.samsolution.demo.dto.OrderMessageDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessageSenderTest2.TestConfig.class)

// Prevents the search for SpringBoot config in the upper classes (i.e. in com.samsolution.demo.DemoApplication)
@SpringBootConfiguration

// Restricts the auto-configuration classes to the specified set
@ImportAutoConfiguration(classes = {RabbitAutoConfiguration.class, JacksonAutoConfiguration.class})
public class MessageSenderTest2 {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    // Simple @Configuration classes are excluded from scanning in test ApplicationContext,
    // so we have to use @TestConfiguration
    @TestConfiguration

    //Create additional beans in SpringContext
    @Import({BaseIntegrationTestConfiguration.class, RabbitMQConfig.class})
    static class TestConfig {
    }

    @Test
    public void shouldSendAtLeastASingleMessage() {
        // Using the queue, exchange and binding already declared in RabbitMQConfig

        OrderMessageDto messageDto = new OrderMessageDto(22L, "Hello");
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.QUEUE_NAME, messageDto);

        Message msg = rabbitTemplate.receive(RabbitMQConfig.QUEUE_NAME, 1500);

        assertThat(msg).isNotNull();
        assertThat(msg.getBody()).isNotEmpty();
        assertThat(msg.getMessageProperties().getReceivedExchange()).isEqualTo(RabbitMQConfig.EXCHANGE_NAME);
        assertThat(msg.getMessageProperties().getReceivedRoutingKey()).isEqualTo(RabbitMQConfig.QUEUE_NAME);
        assertThat(msg.getMessageProperties().getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);
    }

    @Test
    public void shouldSendAndThenReceiveConvertedMessage() {
        OrderMessageDto messageDto = new OrderMessageDto(22L, "Hello");
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.QUEUE_NAME, messageDto);

        OrderMessageDto receivedMessageDto = (OrderMessageDto) rabbitTemplate.receiveAndConvert(RabbitMQConfig.QUEUE_NAME, 1500);

        assertThat(receivedMessageDto).isNotNull().isEqualTo(messageDto);
    }
}