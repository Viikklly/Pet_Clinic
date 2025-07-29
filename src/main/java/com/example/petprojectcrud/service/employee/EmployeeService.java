package com.example.petprojectcrud.service.employee;

import com.example.petprojectcrud.DTO.address.AddressDto;
import com.example.petprojectcrud.DTO.employee.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    public List<EmployeeDto> getAllEmployees();
    public EmployeeDto createEmployee(EmployeeDto employeeDto);
    public EmployeeDto updateEmployee(Integer id, EmployeeDto employeeDto);
    public void deleteEmployee(Integer id);
}
