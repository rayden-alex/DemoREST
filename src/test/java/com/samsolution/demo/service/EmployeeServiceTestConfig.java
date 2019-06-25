package com.samsolution.demo.service;

import com.samsolution.demo.BaseIntegrationTestConfiguration;
import com.samsolution.demo.converter.EmployeeDtoToEmployeeConverter;
import com.samsolution.demo.converter.EmployeeToEmployeeDtoConverter;
import com.samsolution.demo.service.impl.EmployeeServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;


@TestConfiguration
//  Create additional beans in SpringContext (which @DataJpaTest not created)
//  see https://stackoverflow.com/questions/41081589/datajpatest-needing-a-class-outside-the-test
@Import({BaseIntegrationTestConfiguration.class, EmployeeServiceImpl.class, EmployeeToEmployeeDtoConverter.class, EmployeeDtoToEmployeeConverter.class})
public class EmployeeServiceTestConfig {
}
