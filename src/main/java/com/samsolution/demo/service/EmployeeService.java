package com.samsolution.demo.service;

import com.samsolution.demo.dto.EmployeeDto;
import com.samsolution.demo.entity.Employee;

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
     * @param count  - count of test Employees
     * For testing ONLY!
     */
    void fillDemoEmployees(int count);

    /**
     * Save Employee item to DB
     *
     * @param employeeDto - DTO from request
     * @return DTO for saved Employee
     */
    EmployeeDto save(EmployeeDto employeeDto);

    /**
     * Update Employee item to DB
     *
     * @param id          - Employee ID for update
     * @param employeeDto - DTO from request
     * @return DTO for updated Employee
     */
    EmployeeDto update(Long id, EmployeeDto employeeDto);

    /**
     * Find Employee item in DB
     *
     * @param id - Employee ID to find
     * @return DTO for found Employee
     */
    EmployeeDto findById(Long id);

    /**
     * Delete Employee item from DB
     *
     * @param id - Employee ID to delete
     */
    void deleteById(Long id);
}
