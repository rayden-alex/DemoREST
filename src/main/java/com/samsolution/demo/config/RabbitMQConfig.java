package com.samsolution.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
@ConditionalOnProperty(value = "my.rabbit.disable", havingValue = "false", matchIfMissing = true)
@Slf4j
// @see org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration
public class RabbitMQConfig {
    public static final String QUEUE_NAME = "my_message_queue";
    public static final String EXCHANGE_NAME = "message_queue_exchange";

//    private final RabbitTemplate rabbitTemplate;
//
//    public RabbitMQConfig(RabbitTemplate rabbitTemplate) {
//        this.rabbitTemplate = rabbitTemplate;
//    }

    // If necessary, any org.springframework.amqp.core.Queue that is defined as a bean
    // is automatically used to declare a corresponding queue on the RabbitMQ instance.
    @Bean
    Queue queue() {
        // return new Queue(QUEUE_NAME, true);
        return QueueBuilder
                .durable(QUEUE_NAME)
                .build();
    }

    @Bean
    Exchange exchange() {
        //return new TopicExchange("message_queue_exchange");
        return ExchangeBuilder
                .topicExchange(EXCHANGE_NAME)
                .durable(true)
                .build();
    }

    @Bean
    Binding binding(Queue queue, Exchange exchange) {
        //return BindingBuilder.bind(queue).to(exchange).with(QUEUE_NAME);
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(QUEUE_NAME)
                .noargs();
    }

//    @Bean
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
//        connectionFactory.setPort(5672);
//        connectionFactory.setUsername("guest");
//        connectionFactory.setPassword("guest");
//        return connectionFactory;
//    }

//    @Bean
//    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory/*, MessageListenerAdapter listenerAdapter*/) {
//        System.out.println("host = " + connectionFactory.getHost());
//
//        SimpleMessageListenerContainer container = myRabbitListenerContainerFactory().createListenerContainer();
//        //SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames(QUEUE_NAME);
//    //    container.setMessageListener(listenerAdapter);
//
//        return container;
//    }

//    @Bean
//    public SimpleRabbitListenerContainerFactory myRabbitListenerContainerFactory() {
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory());
//        factory.setMaxConcurrentConsumers(5);
//        return factory;
//    }

//    @Bean
//    MessageReceiver receiver() {
//        return new MessageReceiver();
//    }

//    @Bean
//    MessageListenerAdapter listenerAdapter(MessageReceiver receiver) {
//        return new MessageListenerAdapter(receiver, "receiveMsg");
//    }


    // The error handler can either return some result (which is sent as the reply)
    // or throw the original or a new exception
    // (which is thrown to the container or returned to the sender, depending on the returnExceptions setting).
    //
    // see https://docs.spring.io/spring-amqp/reference/html/#async-annotation-driven
    @Bean
    public RabbitListenerErrorHandler errorHandler() {
        RabbitListenerErrorHandler handler = (amqpMessage, message, exception) -> {
            log.error("RabbitListenerError - ", exception);
            throw exception;
        };
        return handler;
    }

    // To send a message to RabbitMQ, the message payload has to be converted into a byte[].
    // For a String that is pretty easy by calling String.getBytes().
    // By default it uses the SimpleMessageConverter, which check if the object is Serializable and if so
    // will use Java serialization to convert the object to a byte[].
    // There are, however, various implementations that use XML
    // (MarshallingMessageConverter) or JSON (Jackson2JsonMessageConverter) for the
    // actual payload (instead of Java serialization).
    @Bean
    public Jackson2JsonMessageConverter messageConverter(ObjectMapper objectMapper) {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter(objectMapper);
        return converter;
    }
}