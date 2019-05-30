package com.samsolution.demo.service;

import com.samsolution.demo.dto.EmployeeDto;

import java.util.List;

/**
 * Service for Employee resource
 */
public interface EmployeeService {

    /**
     * Find all items in Employee resource
     *
     * @return All Employees resource items
     */
    List<EmployeeDto> findAllEmployees();

    /**
     * Generates demo data for Employee resource
     * <p/>
     * For testing ONLY!
     */
    void fillDemoEmployees();

    /**
     * Save Employee item to DB
     *
     * @param employeeDto - DTO from request
     * @return DTO for saved Employee
     */
    EmployeeDto save(EmployeeDto employeeDto);

    EmployeeDto update(Long id, EmployeeDto employeeDto);

    EmployeeDto findById(Long id);

    void deleteById(Long id);
}
