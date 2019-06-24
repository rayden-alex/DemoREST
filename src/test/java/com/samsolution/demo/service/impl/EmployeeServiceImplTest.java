package com.samsolution.demo.service.impl;

import com.samsolution.demo.BaseIntegrationTestConfiguration;
import com.samsolution.demo.converter.EmployeeDtoToEmployeeConverter;
import com.samsolution.demo.converter.EmployeeToEmployeeDtoConverter;
import com.samsolution.demo.dto.EmployeeDto;
import com.samsolution.demo.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
// Slice test
// Disable full auto-configuration and instead apply only configuration relevant to JPA tests.
@DataJpaTest(excludeAutoConfiguration = {JdbcTemplateAutoConfiguration.class, TransactionAutoConfiguration.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeServiceImplTest {
    @Autowired
    private EmployeeService service;

    private final int EXPECTED_EMPLOYEES_COUNT = 12;


    @TestConfiguration
/*  Create additional beans in SpringContext (which @DataJpaTest not created)
    see https://stackoverflow.com/questions/41081589/datajpatest-needing-a-class-outside-the-test

    =BAD SOLUTIONS=
    @ContextConfiguration(classes = {EmployeeServiceImpl.class, EmployeeToEmployeeDtoConverter.class, EmployeeDtoToEmployeeConverter.class})
    @ImportAutoConfiguration(classes = {EmployeeServiceImpl.class, EmployeeToEmployeeDtoConverter.class, EmployeeDtoToEmployeeConverter.class})
    @SpringBootConfiguration
    @EnableAutoConfiguration

    =GOOD SOLUTIONS=
    @ComponentScan({"com.samsolution.demo.converter", "com.samsolution.demo.service"})
    @ImportAutoConfiguration(classes = {EmployeeServiceImpl.class, EmployeeToEmployeeDtoConverter.class, EmployeeDtoToEmployeeConverter.class})
*/
    @Import({BaseIntegrationTestConfiguration.class, EmployeeServiceImpl.class, EmployeeToEmployeeDtoConverter.class, EmployeeDtoToEmployeeConverter.class})
    static class TestConfig {
    }

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