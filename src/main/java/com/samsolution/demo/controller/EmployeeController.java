package com.samsolution.demo.controller;

import com.samsolution.demo.dto.EmployeeDto;
import com.samsolution.demo.service.impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private EmployeeServiceImpl employeeService;

    @Autowired
    public EmployeeController(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Generates demo data for Employee resource
     * <p/>
     * For testing ONLY
     */
    @GetMapping("/fillEmployees")
    public void fillDemoEmployees() {
        employeeService.fillDemoEmployees();
    }

    @GetMapping
    public List<EmployeeDto> getAllBooks() {
        List<EmployeeDto> allEmployees = employeeService.findAllEmployees();
        return allEmployees;
    }

    @PostMapping(value = {"/create"})
    public ResponseEntity<?> create(@RequestBody @Valid EmployeeDto employeeDto) {
        EmployeeDto savedEmployee = employeeService.save(employeeDto);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

}
