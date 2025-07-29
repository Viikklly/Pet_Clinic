package com.example.petprojectcrud.service.employee;

import com.example.petprojectcrud.DTO.employee.ServiceTypeDto;

import java.util.List;


public interface ServicesTypeService {
    public List<ServiceTypeDto> getAllServiceTypes();
    public ServiceTypeDto getServiceTypeById(int id);
}
