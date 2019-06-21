package com.samsolution.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@TestConfiguration
public class BaseIntegrationTestConfiguration {
    @Autowired
    private ApplicationContext ctx;

    @Bean
    // Or we can use @EventListener(ApplicationReadyEvent.class) and injected ctx
    public CommandLineRunner ctxBeanCountPrinter(ApplicationContext appContext) {
        return args -> {
            int count = appContext.getBeanDefinitionCount();

            System.out.println("================================");
            System.out.println("appContext.getBeanDefinitionCount() = " + count);
        };
    }

    @EventListener(ApplicationReadyEvent.class)
    public void ctxBeanClassPrinter() {
        String[] beanDefinitionNames = ctx.getBeanDefinitionNames();

        List<String> beanClasses = getBeanClassNames(beanDefinitionNames);
        System.out.println("======BeanClassNames=========");
        beanClasses.forEach(System.out::println);

        List<String> autoConfigurationClasses = getAutoConfigurationClassNames(beanDefinitionNames);
        System.out.println("======AutoConfiguration=========");
        autoConfigurationClasses.forEach(System.out::println);

        System.out.println("================================");
    }

    private List<String> getAutoConfigurationClassNames(String[] beanDefinitionNames) {
        List<String> autoConfiguration = Arrays.stream(beanDefinitionNames)
                .filter(s -> s.contains("AutoConfiguration"))
                .sorted()
                .collect(Collectors.toList());

        return autoConfiguration;
    }

    private List<String> getBeanClassNames(String[] beanDefinitionNames) {
        final String APP_PACKAGE = this.getClass().getPackage().getName();

        List<String> beanClassNames = Arrays.stream(beanDefinitionNames)
                .map(bd -> "" + ctx.getBean(bd).getClass().getName() + "(" + bd + ")")
                .filter(s -> s.startsWith(APP_PACKAGE))
                .sorted()
                .collect(Collectors.toList());

        return beanClassNames;
    }
}