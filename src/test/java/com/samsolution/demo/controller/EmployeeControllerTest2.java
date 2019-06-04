package com.samsolution.demo.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samsolution.demo.converter.EmployeeToEmployeeDtoConverter;
import com.samsolution.demo.dto.EmployeeDto;
import com.samsolution.demo.entity.Employee;
import com.samsolution.demo.service.EmployeeService;
import com.samsolution.demo.service.impl.EmployeeFactoryImpl;
import com.samsolution.demo.validation.ErrorCode;
import com.samsolution.demo.validation.ErrorDetails;
import com.samsolution.demo.validation.exception.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
// Disable full auto-configuration and instead apply only configuration relevant to MVC tests
// Not all components available in Spring context, no embedded Tomcat, no real database, web-layer is mocked
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest2 {

    @Autowired
    private MockMvc mvc; //tests annotated with @WebMvcTest will also auto-configure Spring Security and MockMvc

    @Autowired
    private ObjectMapper jsonMapper; // Can be Autowired because of @WebMvcTest (it's auto configure JsonComponent, Converter and other MVC components)
    //private final ObjectMapper jsonMapper = new ObjectMapper().findAndRegisterModules();

    @MockBean
    private EmployeeService service;

    private Converter<Employee, EmployeeDto> toDtoConverter;

    // This objects will be magically initialized by the JacksonTester.initFields() method below
    private JacksonTester<EmployeeDto> testerEmployeeDto;
    private JacksonTester<List<EmployeeDto>> testerEmployeeDtoList;

    private final String RESOURCE_URL = "/employees";

    @Before
    public void setUp() {
        // Initializes the JacksonTester
        // (or @AutoConfigureJsonTesters can be used to autowire JacksonTester instances)
        // (@JsonTest cannot be applied because it include @BootstrapWith and @WebMvcTest already have it)
        JacksonTester.initFields(this, jsonMapper);

        toDtoConverter = new EmployeeToEmployeeDtoConverter();
    }

    @Test
    public void getAllEmployees() throws Exception {
        final int EXPECTED_EMPLOYEES_COUNT = 11;
        List<Employee> expectedEmployees = EmployeeFactoryImpl.getInstance().createList(EXPECTED_EMPLOYEES_COUNT);
        List<EmployeeDto> expectedEmployeesDtoList = expectedEmployees.stream()
                .map(employee -> toDtoConverter.convert(employee))
                .collect(Collectors.toList());

        //when
        when(service.findAllEmployees()).thenReturn(expectedEmployeesDtoList);


        MockHttpServletResponse response = mvc.perform(get(RESOURCE_URL).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        String content = response.getContentAsString();

        // convert json to DTO by "ObjectMapper"
        List<EmployeeDto> employeeDtoList = jsonMapper.readValue(content, new TypeReference<List<EmployeeDto>>() {
        });

        assertThat(employeeDtoList.size()).isEqualTo(expectedEmployeesDtoList.size());
        assertThat(employeeDtoList).isEqualTo(expectedEmployeesDtoList);

        // convert json to DTO by "JacksonTester" v1
        assertThat(testerEmployeeDtoList.parse(content)).isEqualTo(expectedEmployeesDtoList);

        // convert json to DTO by "JacksonTester" v2
        employeeDtoList = testerEmployeeDtoList.parseObject(content);
        assertThat(employeeDtoList).isEqualTo(expectedEmployeesDtoList);
    }


    @Test
    public void getEmployeeByIdAndFound() throws Exception {
        final long EMPLOYEE_ID = 22L;
        Employee employee = EmployeeFactoryImpl.getInstance().createOneWithId(EMPLOYEE_ID);
        EmployeeDto expectedEmployee = toDtoConverter.convert(employee);

        //when
        when(service.findById(eq(EMPLOYEE_ID))).thenReturn(expectedEmployee);

        MockHttpServletResponse response = mvc.perform(get(RESOURCE_URL + "/" + EMPLOYEE_ID).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        String content = response.getContentAsString();

        // convert json to DTO by "ObjectMapper"
        EmployeeDto employeeDto = jsonMapper.readValue(content, EmployeeDto.class);
        assertThat(employeeDto).isEqualTo(expectedEmployee);

        // convert json to DTO by "JacksonTester" v1
        assertThat(testerEmployeeDto.parse(content)).isEqualTo(expectedEmployee);

        // convert json to DTO by "JacksonTester" v2
        employeeDto = testerEmployeeDto.parseObject(content);
        assertThat(employeeDto).isEqualTo(expectedEmployee);
    }

    @Test
    public void getEmployeeByIdAndNotFound() throws Exception {
        final long EMPLOYEE_ID = 22L;
        //when
        when(service.findById(anyLong())).thenThrow(new ResourceNotFoundException("employee id=" + EMPLOYEE_ID));

        MockHttpServletResponse response = mvc.perform(get(RESOURCE_URL + "/" + EMPLOYEE_ID).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse();

        String content = response.getContentAsString();

        // convert json to DTO by "ObjectMapper"
        ErrorDetails errorDetails = jsonMapper.readValue(content, ErrorDetails.class);
        assertThat(errorDetails).isNotNull();
        assertThat(errorDetails.getErrorCode()).isEqualTo(ErrorCode.RESOURCE_NOT_FOUND);
    }
}