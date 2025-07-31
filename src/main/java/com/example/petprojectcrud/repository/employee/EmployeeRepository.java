package com.example.petprojectcrud.repository.employee;

import com.example.petprojectcrud.model.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findFirstByNameContainsIgnoreCase(String name);

}
