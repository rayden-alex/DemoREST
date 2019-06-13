package com.samsolution.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.samsolution.demo.jaxrs.controller.HelloService;
import com.samsolution.demo.jaxrs.controller.impl.HelloServiceImpl1;
import com.samsolution.demo.jaxrs.controller.impl.HelloServiceImpl2;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

//import org.apache.cxf.jaxrs.swagger.Swagger2Feature;

@Configuration
class CXFConfig {
    private final ObjectMapper mapper;
    private final Bus bus;

    @Autowired
    public CXFConfig(ObjectMapper mapper, Bus bus) {
        this.mapper = mapper;
        this.bus = bus;
    }


    /**
     * "cxf.jaxrs." params in "application.properties" are ignored
     * when "org.apache.cxf.endpoint.Server" bean is created and configured manually
     * <p>
     * "cxf.path=/cxf"  ---- CXF servlet path is ignoring on manually "cxfServletRegistration" bean creation
     * <p>
     * see org.apache.cxf.spring.boot.autoconfigure.CxfAutoConfiguration.JaxRsComponentConfiguration
     * see org.apache.cxf.spring.boot.autoconfigure.CxfAutoConfiguration.#cxfServletRegistration()
     */
    @Bean
    public Server rsServer() {
        final JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
        endpoint.setProvider(jsonProvider());
        endpoint.setBus(bus);
        endpoint.setAddress("/hello"); // concrete endpoint path
        endpoint.setServiceBeans(Arrays.asList(helloController(), helloController2()));
//        endpoint.setFeatures(Arrays.asList(new Swagger2Feature()));
        return endpoint.create();
    }

    @Bean
    public HelloService helloController() {
        return new HelloServiceImpl1();
    }

    @Bean
    public HelloService helloController2() {
        return new HelloServiceImpl2();
    }

    @Bean
    public JacksonJsonProvider jsonProvider() {
        // Creating JsonProvider with already predefined and configured "com.fasterxml.jackson.databind.ObjectMapper".
        // We get it from the Spring context. This is allow to us do the same serialization/deserialization like in SpringMVC REST.
        // Otherwise we must use @JsonDeserialize, @JsonSerialize, @JsonFormat on DTO.

        return new JacksonJaxbJsonProvider(mapper, JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS);
    }
}
