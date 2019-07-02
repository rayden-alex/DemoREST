package com.samsolution.demo;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.annotation.RabbitBootstrapConfiguration;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest

// Without @DirtiesContext "MessageSenderTest2" failed on running "all test".
//
// Похоже это происходит потому, что бины продолжают "жить" в закешированных
// тестовых контекстах. И если эти бины имели коннекты к внешним системам
// не через DispatcherServlet и прочие спринговые плюшки, то эти коннекты тоже будут "живыми".
// И, соответственно, продолжат обрабатывать запросы через эти коннекты.
// Чтобы остановить RabbitListener-ы нужно удалить их из закешированного контекста:
// @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)

// Или вообще не поднимать бины связанные с Rabbit при запуске основной массы тестов,
// а запускать их только тогда когда это действительно нужно. Типа аналог slice-test.
@TestPropertySource(properties = "my.rabbit.disable=true")
public class DemoApplicationTests {
    @Test
    public void contextLoads() {
    }

    /**
     * Stop all ListenerEndpoint in "RabbitListenerEndpointRegistry" takes almost 770 ms.
     * "DirtiesContext" takes much less time!
     * <p/>
     * Contrary to MessageListenerContainers created manually, listener containers managed by registry
     * are not beans in the application context and are not candidates for autowiring.
     *
     * @see RabbitBootstrapConfiguration
     * @see RabbitListenerEndpointRegistry
     * @see <a href="https://stackoverflow.com/questions/41035454/how-to-stop-consuming-messages-with-rabbitlistener">how-to-stop-consuming-messages-with-rabbitlistener</a>
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
