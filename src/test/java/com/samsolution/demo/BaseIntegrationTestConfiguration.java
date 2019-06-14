package com.samsolution.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import java.util.Arrays;

@TestConfiguration
public class BaseIntegrationTestConfiguration {
    @Autowired
    private ApplicationContext ctx;

    private static final String APP_PACKAGE = "com.samsolution.demo";

    @Bean
    // Or we can use @EventListener(ApplicationReadyEvent.class)
    public CommandLineRunner ctxBeanCountPrinter(ApplicationContext appContext) {
        return args -> {
            System.out.println("================================");
            System.out.println("appContext.getBeanDefinitionCount() = " + appContext.getBeanDefinitionCount());
            System.out.println("================================");
        };
    }

    @EventListener(ApplicationReadyEvent.class)
    public void ctxBeanClassPrinter() {
        String[] beanDefinitionNames = ctx.getBeanDefinitionNames();

        Arrays.stream(beanDefinitionNames)
                .map(bd -> "" + ctx.getBean(bd).getClass().getName() + "(" + bd + ")")
                .filter(s -> s.startsWith(APP_PACKAGE))
                .sorted()
                .forEach(System.out::println);

        System.out.println("======AutoConfiguration=========");

        AutoConfigurationPackages.get(ctx)
                .forEach(System.out::println);

//        Arrays.stream(beanDefinitionNames)
//                .filter(s -> s.contains("AutoConfiguration"))
//                .sorted()
//                .forEach(System.out::println);

        System.out.println("================================");
    }
}