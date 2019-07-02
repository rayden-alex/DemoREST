package com.samsolution.demo.rabbit;

import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;


public abstract class AbstractRabbitContainerTest {
    private static final int RABBIT_MQ_PORT = 5672;
    private static final int RABBIT_MQ_WEB_MANAGEMENT_PORT = 15672;
    private static final String DOCKER_IMAGE_NAME = "rabbitmq:3.7.15-management-alpine";

//    private static final List<Integer> RABBITMQ_PORTS = Arrays.asList(RABBIT_MQ_PORT, RABBIT_MQ_WEB_MANAGEMENT_PORT);
//    private static final List<String> RABBITMQ_PORT_BINDINGS = Arrays.asList("5672:5672", "15672:15672");

    protected static final GenericContainer<?> RABBIT_MQ_CONTAINER = new FixedHostPortGenericContainer<>(DOCKER_IMAGE_NAME)
            .withExposedPorts(RABBIT_MQ_PORT, RABBIT_MQ_WEB_MANAGEMENT_PORT) // or "GenericContainer.setExposedPorts()" can be used
            .withFixedExposedPort(RABBIT_MQ_PORT, RABBIT_MQ_PORT) // or "GenericContainer.setPortBindings()" can be used
            .withFixedExposedPort(RABBIT_MQ_WEB_MANAGEMENT_PORT, RABBIT_MQ_WEB_MANAGEMENT_PORT);

    static {
        // We can get real ports for working with RabbitMQ from RABBIT_MQ_CONTAINER.getMappedPort()
        // see RabbitTestContainerInitializer.class.
        //
        // Or we can use fixed exposed ports: RABBIT_MQ_CONTAINER.withFixedExposedPort()
        // to bind container port to equal host port.

        // RABBIT_MQ_CONTAINER.setExposedPorts(RABBITMQ_PORTS);
        // RABBIT_MQ_CONTAINER.setPortBindings(RABBITMQ_PORT_BINDINGS);
        RABBIT_MQ_CONTAINER.start();
    }
}
