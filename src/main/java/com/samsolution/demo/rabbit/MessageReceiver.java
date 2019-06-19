package com.samsolution.demo.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
@Slf4j
public class MessageReceiver {
    private CountDownLatch countDownLatch = new CountDownLatch(1);

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
