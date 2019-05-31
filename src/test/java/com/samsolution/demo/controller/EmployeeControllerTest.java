package com.samsolution.demo.controller;

import com.samsolution.demo.entity.Employee;
import com.samsolution.demo.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EmployeeService service;

    private final int EXPECTED_EMPLOYEES_COUNT = 11;

    @Before
    public void setUp() {
        service.fillDemoEmployees(EXPECTED_EMPLOYEES_COUNT);
    }

    @Test
    public void getAllEmployees() {
        //when
        ResponseEntity<List<Employee>> responseList = restTemplate.exchange(
                "/employees",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Employee>>() {
                });

        //then
        assertThat(responseList.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Employee> employees = responseList.getBody();
        assertThat(employees).isNotNull();
        assertThat(employees.size()).isEqualTo(EXPECTED_EMPLOYEES_COUNT);
    }

    @Test
    public void getEmployeeById() {
        //when
        ResponseEntity<String> response = restTemplate.exchange(
                "/employee/99",
                HttpMethod.GET,
                null,
                String.class);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}