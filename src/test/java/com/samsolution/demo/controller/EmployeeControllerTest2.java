package com.samsolution.demo.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.samsolution.demo.converter.EmployeeToEmployeeDtoConverter;
import com.samsolution.demo.dto.EmployeeDto;
import com.samsolution.demo.entity.Employee;
import com.samsolution.demo.service.EmployeeService;
import com.samsolution.demo.service.impl.EmployeeServiceImpl;
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

import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@AutoConfigureMockMvc
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest2 {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeService service;

    private Converter<Employee, EmployeeDto> toDtoConverter;

    // This object will be magically initialized by the initFields method below.
    private JacksonTester<List<EmployeeDto>> jsonEmployeeDtoList;

    private final String RESOURCE_URL = "/employees";
    private final int EXPECTED_EMPLOYEES_COUNT = 11;
    private ObjectMapper mapper;

    @Before
    public void setUp() {
        // Initializes the JacksonTester
        mapper = new ObjectMapper();
        //mapper.registerModule(new JavaTimeModule());
        mapper.findAndRegisterModules();
        JacksonTester.initFields(this, mapper);

        //service.fillDemoEmployees(EXPECTED_EMPLOYEES_COUNT);
        toDtoConverter = new EmployeeToEmployeeDtoConverter();
    }

    @Test
    public void getAllEmployees() throws Exception {
        List<Employee> expectedEmployees = EmployeeServiceImpl.generateEmployees(EXPECTED_EMPLOYEES_COUNT);
        List<EmployeeDto> expectedEmployeesDto = expectedEmployees.stream()
                .map(employee -> toDtoConverter.convert(employee))
                .collect(Collectors.toList());

        //when
        when(service.findAllEmployees()).thenReturn(expectedEmployeesDto);


        MockHttpServletResponse response = mvc.perform(get(RESOURCE_URL).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();


        //JsonNode data = mapper.readTree(response.getContentAsString());
        List<EmployeeDto> responseEmployeesDto= mapper.readValue(response.getContentAsString(), new TypeReference<List<EmployeeDto>>() {
        });

        assertThat(responseEmployeesDto.size()).isEqualTo(expectedEmployeesDto.size());
        assertThat(responseEmployeesDto).isEqualTo(expectedEmployeesDto);

        List<EmployeeDto> dtos = jsonEmployeeDtoList.readObject(new StringReader(response.getContentAsString()));
        assertThat(dtos).isEqualTo(expectedEmployeesDto);
//
//        ResponseEntity<List<Employee>> responseList = restTemplate.exchange(
//                RESOURCE_URL,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<Employee>>() {
//                });
//
//        //then
//        assertThat(responseList.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        List<Employee> employees = responseList.getBody();
//        assertThat(employees).isNotNull();
//        assertThat(employees.size()).isEqualTo(EXPECTED_EMPLOYEES_COUNT);
    }

}