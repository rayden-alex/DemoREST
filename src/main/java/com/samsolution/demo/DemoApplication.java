package com.samsolution.demo;

import com.samsolution.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class DemoApplication {
    private EmployeeService employeeService;

    @Autowired
    public DemoApplication(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
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
