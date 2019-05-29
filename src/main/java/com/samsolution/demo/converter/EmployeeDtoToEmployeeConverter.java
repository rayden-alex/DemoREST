package com.samsolution.demo.converter;

import com.samsolution.demo.entity.Employee;
import com.samsolution.demo.dto.EmployeeDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDtoToEmployeeConverter implements Converter<EmployeeDto, Employee> {
    @Override
    public Employee convert(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setBirthday(employeeDto.getBirthday());

        return employee;
    }
}