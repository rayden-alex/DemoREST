package com.samsolution.demo.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Slf4j
public class MessageSender {
    @Autowired
    RabbitTemplate rabbitTemplate;

    private final static String queueName = "my_message_queue";

    @EventListener(ApplicationReadyEvent.class)
    public void sendMsg() {
        StopWatch watch = new StopWatch();
        watch.start();

        for (int i = 0; i < 1_000_000; i++) {
            rabbitTemplate.convertAndSend(queueName, "Hello World! " + i);
        }

        watch.stop();
        log.info(watch.prettyPrint());
    }
}
