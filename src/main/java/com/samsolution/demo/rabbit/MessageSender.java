package com.samsolution.demo.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Slf4j
public class MessageSender {
    private final RabbitTemplate rabbitTemplate;
    private final static String queueName = "my_message_queue";

    public MessageSender(@Autowired RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    //@EventListener(ApplicationReadyEvent.class)
    @Async("rabbitExecutor")
    public void sendMessages(int threadId) {
        StopWatch watch = new StopWatch("rabbitWatch");

        watch.start("threadId=" + threadId);
        sendMsg(threadId);
        watch.stop();

        log.info(watch.prettyPrint());
    }


    private void sendMsg(int threadId) {
        for (int i = 0; i < 500_000; i++) {
            rabbitTemplate.convertAndSend(queueName, "Hello " + threadId + " World! " + i);
        }
    }
}
