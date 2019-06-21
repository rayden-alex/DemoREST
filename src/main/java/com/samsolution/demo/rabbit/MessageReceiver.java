package com.samsolution.demo.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
@Slf4j
public class MessageReceiver {
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    // Queue binding can also be declared in @Configuration
    // see com.samsolution.demo.config.RabbitMQConfig.binding
    // see https://docs.spring.io/spring-amqp/reference/html/#async-annotation-driven
//    @RabbitListener(
//            containerFactory = "myConnectionFactory",
//            bindings = @QueueBinding(
//                    value = @Queue(value = "my_message_queue", durable = "true"),
//                    exchange = @Exchange(value = "message_queue_exchange", type = ExchangeTypes.DIRECT), key = "key1")
//    )
//   @RabbitListener(queuesToDeclare = @Queue(name = "${my.queue}", durable = "true"))
    @RabbitListener(queues = "my_message_queue")
    public void receiveMsg(@SuppressWarnings("unused") String message) {
        //log.info("Message Received: " + message);
        // countDownLatch.countDown();
    }

    @SuppressWarnings("unused")
    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }
}
