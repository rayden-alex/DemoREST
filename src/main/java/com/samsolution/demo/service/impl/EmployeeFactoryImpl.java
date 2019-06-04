package com.samsolution.demo.service.impl;

import com.samsolution.demo.entity.Employee;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EmployeeFactoryImpl {
    private final static EmployeeFactoryImpl factory = new EmployeeFactoryImpl();

    private EmployeeFactoryImpl() {
        // closed constructor
    }

    public static EmployeeFactoryImpl getInstance() {
        return factory;
    }

    public List<Employee> createList(int count) {
        return IntStream.rangeClosed(1, count)
                .mapToObj(this::createOneWithId)
                .collect(Collectors.toList());
    }

    public Employee createOneWithId(long index) {
        Employee employee = new Employee();
        employee.setLastName("LastName" + index);
        employee.setFirstName("FirstName" + index);

        LocalDate randomDate = generateRandomDate();
        employee.setBirthday(randomDate);

        return employee;
    }

    private LocalDate generateRandomDate() {
        long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2000, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);

        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
        return randomDate;
    }
}
