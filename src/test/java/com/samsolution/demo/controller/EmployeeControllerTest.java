package com.samsolution.demo.controller;

import com.samsolution.demo.entity.Employee;
import com.samsolution.demo.jpa.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private EmployeeRepository dao;

    @Test
    public void getAllEmployees() {
        //given
        final int EMPLOYEES_COUNT = 10;
        List<Employee> employeeList = IntStream.rangeClosed(1, EMPLOYEES_COUNT)
                .mapToObj(this::createEmployee)
                .collect(Collectors.toList());

        when(dao.findAll()).thenReturn(employeeList);

        //when
        ResponseEntity<List<Employee>> responseList = restTemplate.exchange(
                "http://localhost:8080/employees/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Employee>>() {
                });

        //then
        assertThat(responseList.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Employee> employees = responseList.getBody();
        assertThat(employees).isNotNull();
        assertThat(employees.size()).isEqualTo(EMPLOYEES_COUNT);
        assertThat(employees).containsAll(employeeList);
    }

    private Employee createEmployee(int i) {
        Employee employee = new Employee();
        employee.setLastName("LastName" + i);
        employee.setFirstName("FirstName" + i);

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