package com.samsolution.demo.service.impl;

import com.samsolution.demo.dto.EmployeeDto;
import com.samsolution.demo.service.EmployeeService;
import com.samsolution.demo.service.EmployeeServiceTestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
// Slice test
// Disable full auto-configuration and instead apply only configuration relevant to JPA tests.
@DataJpaTest(excludeAutoConfiguration = {JdbcTemplateAutoConfiguration.class, TransactionAutoConfiguration.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

@ContextConfiguration(classes = {EmployeeServiceTestConfig.class})
public class EmployeeServiceImplTest {
    @Autowired
    private EmployeeService service;

    private final int EXPECTED_EMPLOYEES_COUNT = 12;

    @Before
    public void setUp() {
        //service = new EmployeeServiceImpl(dao, new EmployeeToEmployeeDtoConverter(), new EmployeeDtoToEmployeeConverter());
        service.fillDemoEmployees(EXPECTED_EMPLOYEES_COUNT);
    }

    @Test
    public void findAllEmployeesWithStream() {
        List<EmployeeDto> employees = service.findAllEmployeesWithStream();
        assertThat(employees).hasSize(EXPECTED_EMPLOYEES_COUNT);
    }

    @Test
    public void findAllEmployees() {
        List<EmployeeDto> employees = service.findAllEmployees();
        assertThat(employees).hasSize(EXPECTED_EMPLOYEES_COUNT);
    }
}