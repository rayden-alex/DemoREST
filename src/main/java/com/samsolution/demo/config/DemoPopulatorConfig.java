package com.samsolution.demo.config;

import com.samsolution.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class DemoPopulatorConfig {
    private final EmployeeService employeeService;

    @Autowired
    public DemoPopulatorConfig(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @EventListener(ApplicationReadyEvent.class)
    // An ApplicationReadyEvent is sent after
    // any application and command-line runners have been called.
    // (or we can use ApplicationRunner interface)
    // @see org.springframework.boot.SpringApplication.run(java.lang.String...)
    //
    // If your application uses a hierarchy of SpringApplication instances,
    // a listener may receive multiple instances of the same type of application event.
    // To distinguish between an event for its context and an event for a
    // descendant context, it should request that its application context is injected and then compare
    // the injected context with the context of the event.
    public void afterStartup() {
        final int demoEmployeesCount = 10;
        employeeService.fillDemoEmployees(demoEmployeesCount);
    }
}
