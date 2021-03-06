package com.samsolution.demo.controller;

import com.samsolution.demo.BaseIntegrationTestConfiguration;
import com.samsolution.demo.entity.Employee;
import com.samsolution.demo.service.EmployeeService;
import com.samsolution.demo.validation.ErrorCode;
import com.samsolution.demo.validation.ErrorDetails;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
// Test with full Spring context, embedded Tomcat and real database
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {BaseIntegrationTestConfiguration.class})

@ImportAutoConfiguration(exclude = {RabbitAutoConfiguration.class})

// Without @DirtiesContext "MessageSenderTest2" failed on running "all test"
// @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
//
// Или вообще не поднимать бины связанные с Rabbit при запуске основной массы тестов,
// а запускать их только тогда когда это действительно нужно. Типа аналог slice-test.
@TestPropertySource(properties = "my.rabbit.disable=true")
public class EmployeeControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EmployeeService service;

    private final String RESOURCE_URL = "/employees";
    private final int EXPECTED_EMPLOYEES_COUNT = 11;

    @Before
    public void setUp() {
        service.fillDemoEmployees(EXPECTED_EMPLOYEES_COUNT);
    }

    @Test
    public void getAllEmployees() {
        //when
        ResponseEntity<List<Employee>> responseList = restTemplate.exchange(
                RESOURCE_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Employee>>() {
                });

        //then
        assertThat(responseList.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Employee> employees = responseList.getBody();
        assertThat(employees).hasSize(EXPECTED_EMPLOYEES_COUNT);
    }

    @Test
    public void getEmployeeById() {
        //when
        ResponseEntity<ErrorDetails> response = restTemplate.exchange(
                RESOURCE_URL + "/99",
                HttpMethod.GET,
                null,
                ErrorDetails.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        ErrorDetails errorDetails = response.getBody();
        assertThat(errorDetails).isNotNull();
        assertThat(errorDetails.getErrorCode()).isEqualTo(ErrorCode.RESOURCE_NOT_FOUND);

    }
}