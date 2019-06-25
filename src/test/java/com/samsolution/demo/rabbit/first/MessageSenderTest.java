package com.samsolution.demo.rabbit.first;

import com.samsolution.demo.dto.OrderMessageDto;
import com.samsolution.demo.rabbit.RabbitMessageSenderTestConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest

// Prevents the search for SpringBoot config in the upper classes (i.e. in com.samsolution.demo.DemoApplication)
@SpringBootConfiguration

// Restricts the auto-configuration classes to the specified set
@ImportAutoConfiguration(classes = {RabbitAutoConfiguration.class, JacksonAutoConfiguration.class})
@ContextConfiguration(classes = {RabbitMessageSenderTestConfig.class}) // or we can use "classes" in @SpringBootTest
public class MessageSenderTest {
    private static final String QUEUE_NAME = "test_queue";
    private static final String EXCHANGE_NAME = "test_exchange";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    AmqpAdmin amqpAdmin;

    @SuppressWarnings("FieldCanBeLocal")
    private Queue queue;

    @SuppressWarnings("FieldCanBeLocal")
    private Exchange exchange;

    private Binding binding;

    @Before
    public void setUp() {
        queue = QueueBuilder.durable(QUEUE_NAME).build();
        exchange = ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();
        binding = BindingBuilder.bind(queue).to(exchange).with(QUEUE_NAME).noargs();

        // Manual declaring of the queue, exchange and binding
        // RabbitAdmin amqpAdmin = new RabbitAdmin(rabbitTemplate);
        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareExchange(exchange);
        amqpAdmin.declareBinding(binding);
    }

    @After
    public void tearDown() {
        // Manual removing of the queue, exchange and binding
        // RabbitAdmin amqpAdmin = new RabbitAdmin(rabbitTemplate);
        amqpAdmin.removeBinding(binding);
        amqpAdmin.deleteExchange(EXCHANGE_NAME);
        amqpAdmin.deleteQueue(QUEUE_NAME);
    }

    @Test
    public void shouldSendAtLeastASingleMessage() {
        OrderMessageDto messageDto = new OrderMessageDto(22L, "Hello");
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, QUEUE_NAME, messageDto);

        Message msg = rabbitTemplate.receive(QUEUE_NAME, 1500);

        assertThat(msg).isNotNull();
        assertThat(msg.getBody()).isNotEmpty();
        assertThat(msg.getMessageProperties().getReceivedExchange()).isEqualTo(EXCHANGE_NAME);
        assertThat(msg.getMessageProperties().getReceivedRoutingKey()).isEqualTo(QUEUE_NAME);
        assertThat(msg.getMessageProperties().getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);
    }

    @Test
    public void shouldSendAndThenReceiveConvertedMessage() {
        OrderMessageDto messageDto = new OrderMessageDto(22L, "Hello");
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, QUEUE_NAME, messageDto);

        OrderMessageDto receivedMessageDto = (OrderMessageDto) rabbitTemplate.receiveAndConvert(QUEUE_NAME, 1500);

        assertThat(receivedMessageDto).isNotNull().isEqualTo(messageDto);
    }
}