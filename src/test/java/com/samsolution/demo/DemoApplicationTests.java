package com.samsolution.demo;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.annotation.RabbitBootstrapConfiguration;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest

// Without @DirtiesContext "MessageSenderTest2" failed on running "all test"
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class DemoApplicationTests {
    @Test
    public void contextLoads() {
    }

    /**
     * Stop all ListenerEndpoint in "RabbitListenerEndpointRegistry" takes almost 770 ms.
     * "DirtiesContext" takes much less time!
     *
     * Contrary to MessageListenerContainers created manually, listener containers managed by registry
     * are not beans in the application context and are not candidates for autowiring.
     *
     * @see RabbitBootstrapConfiguration
     * @see RabbitListenerEndpointRegistry
     */
    @After
    public void tearDown() {
//        StopWatch stopWatch = new StopWatch();
//
//        RabbitListenerEndpointRegistry registry = ctx.getBean(
//                RabbitListenerConfigUtils.RABBIT_LISTENER_ENDPOINT_REGISTRY_BEAN_NAME,
//                RabbitListenerEndpointRegistry.class);
//
//        stopWatch.start();
//        registry.stop();
//        stopWatch.stop();
//
//        log.warn("RabbitListenerEndpointRegistry stop: {}", stopWatch.shortSummary());
    }
}
