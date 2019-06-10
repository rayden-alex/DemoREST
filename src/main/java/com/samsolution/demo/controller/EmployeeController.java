package com.samsolution.demo.controller;

import com.samsolution.demo.dto.EmployeeDto;
import com.samsolution.demo.service.EmployeeService;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


/**
 * REST Controller for Employee resource
 * <p/>
 * '@Timed' annotation enables metric collecting<br/>
 * By default, metrics are generated with the name, http.server.requests.<br/>
 * The name can be customized by setting the management.metrics.web.server.requests-metric-name property.
 *
 */
@RestController
@Timed(description="EmployeeController request metrics")
@RequestMapping("/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @ApiOperation(value = "Returns a list of all available employees")
    public List<EmployeeDto> getAllEmployees() {
        List<EmployeeDto> allEmployees = employeeService.findAllEmployees();
        return allEmployees;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Returns employee by ID")
    public EmployeeDto getEmployeeById(@PathVariable("id") Long id) {
        EmployeeDto employee = employeeService.findById(id);
        return employee;
    }

    @PostMapping()
    @ApiOperation(value = "Creates new employee")
    public ResponseEntity<?> createEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
        EmployeeDto savedEmployee = employeeService.save(employeeDto);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Full update of existing employee")
    public EmployeeDto updateEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeDto employeeDto) {
        EmployeeDto savedEmployee = employeeService.update(id, employeeDto);
        return savedEmployee;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes of existing employee")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
