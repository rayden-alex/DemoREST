package com.samsolution.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
// @see org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration
public class RabbitMQConfig {
    private final static String queueName = "my_message_queue";

//    private final RabbitTemplate rabbitTemplate;
//
//    public RabbitMQConfig(RabbitTemplate rabbitTemplate) {
//        this.rabbitTemplate = rabbitTemplate;
//    }

    @Bean
    Queue queue() {
        return new Queue(queueName, true);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("message_queue_exchange");
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(queueName);
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
//        container.setQueueNames(queueName);
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


}