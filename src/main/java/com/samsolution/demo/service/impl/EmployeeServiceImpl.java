package com.samsolution.demo.service.impl;

import com.samsolution.demo.dto.EmployeeDto;
import com.samsolution.demo.entity.Employee;
import com.samsolution.demo.jpa.EmployeeRepository;
import com.samsolution.demo.service.EmployeeService;
import com.samsolution.demo.validation.exception.BirthdayValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Service for Employee resource
 */
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository dao;
    private Converter<Employee, EmployeeDto> toDtoConverter;
    private Converter<EmployeeDto, Employee> fromDtoConverter;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository dao,
                               Converter<Employee, EmployeeDto> toDtoConverter,
                               Converter<EmployeeDto, Employee> fromDtoConverter) {
        this.dao = dao;
        this.toDtoConverter = toDtoConverter;
        this.fromDtoConverter = fromDtoConverter;
    }

    /**
     * Find all items in Employee resource
     *
     * @return All Employees resource items
     */
    @Override
    public List<EmployeeDto> findAllEmployees() {
        List<Employee> employees = dao.findAll();

        List<EmployeeDto> employeeDtos = employees.stream()
                .map(employee -> toDtoConverter.convert(employee))
                .collect(Collectors.toList());

        return employeeDtos;
    }

    /**
     * Generates demo data for Employee resource
     * <p/>
     * For testing ONLY!
     */
    @Override
    public void fillDemoEmployees() {
        dao.deleteAll();

        IntStream.rangeClosed(1, 10)
                .mapToObj(this::createEmployee)
                .forEach(employee -> dao.save(employee));
    }

    /**
     * Save Employee item to DB
     *
     * @param employeeDto - DTO from request
     * @return DTO for saved Employee
     */
    @Override
    public EmployeeDto save(EmployeeDto employeeDto) {
        // Some business logic that can throw exception
        if (employeeDto.getBirthday().isAfter(LocalDate.now())){
            throw new BirthdayValidationException(employeeDto.toString());
        }

        Employee employee = fromDtoConverter.convert(employeeDto);
        Employee savedEmployee = dao.save(employee);

        return toDtoConverter.convert(savedEmployee);
    }

    private Employee createEmployee(int i) {
        Employee employee = new Employee();
        employee.setLastName("LastName" + i);
        employee.setFirstName("FirstName" + i);

        LocalDate randomDate = generateRandomDate();
        employee.setBirthday(randomDate);

        return employee;
    }

    private LocalDate generateRandomDate() {
        long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2000, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);

        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
        return randomDate;
    }
}
