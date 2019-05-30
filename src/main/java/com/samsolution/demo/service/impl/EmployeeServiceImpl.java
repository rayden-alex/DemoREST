package com.samsolution.demo.service.impl;

import com.samsolution.demo.dto.EmployeeDto;
import com.samsolution.demo.entity.Employee;
import com.samsolution.demo.jpa.EmployeeRepository;
import com.samsolution.demo.service.EmployeeService;
import com.samsolution.demo.validation.exception.BirthdayValidationException;
import com.samsolution.demo.validation.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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

    @Transactional(readOnly = true)
    @Override
    public List<EmployeeDto> findAllEmployees() {
        List<Employee> employees = dao.findAll();

        List<EmployeeDto> employeeDtos = employees.stream()
                .map(employee -> toDtoConverter.convert(employee))
                .collect(Collectors.toList());

        return employeeDtos;
    }

    @Override
    public void fillDemoEmployees() {
        dao.deleteAll();

        IntStream.rangeClosed(1, 10)
                .mapToObj(this::createEmployee)
                .forEach(employee -> dao.save(employee));
    }

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

    @Override
    public EmployeeDto update(Long id, EmployeeDto employeeDto) {
        Employee employee = Optional.ofNullable(id)
                .flatMap(empId -> dao.findById(empId))
                .orElseThrow(() -> new ResourceNotFoundException(employeeDto.toString()));

        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setBirthday(employeeDto.getBirthday());
        Employee savedEmployee = dao.save(employee);

        return toDtoConverter.convert(savedEmployee);
    }

    @Transactional(readOnly = true)
    @Override
    public EmployeeDto findById(Long id) {

        EmployeeDto employeeDto = dao.findById(id)
                .map(emp -> toDtoConverter.convert(emp))
                .orElseThrow(() -> new ResourceNotFoundException("employee id=" + id));

        return employeeDto;
    }

    @Override
    public void deleteById(Long id) {
        Employee employee = dao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("employee id=" + id));

        dao.delete(employee);
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
