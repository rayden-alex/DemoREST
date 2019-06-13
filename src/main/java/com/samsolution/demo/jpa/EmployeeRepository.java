package com.samsolution.demo.jpa;

import com.samsolution.demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Stream<Employee> streamAllBy();
}
