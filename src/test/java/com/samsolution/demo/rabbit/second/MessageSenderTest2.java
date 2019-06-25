package com.samsolution.demo.rabbit.second;

import com.samsolution.demo.config.RabbitMQConfig;
import com.samsolution.demo.dto.OrderMessageDto;
import com.samsolution.demo.rabbit.RabbitMessageSenderTestConfig;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RabbitMessageSenderTestConfig.class}) // or we can use "classes" in @ContextConfiguration

// Prevents the search for SpringBoot config in the upper classes (i.e. in com.samsolution.demo.DemoApplication)
@SpringBootConfiguration

// Restricts the auto-configuration classes to the specified set
@ImportAutoConfiguration(classes = {RabbitAutoConfiguration.class, JacksonAutoConfiguration.class})
public class MessageSenderTest2 {
    @Autowired
    private RabbitTemplate rabbitTemplate;

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