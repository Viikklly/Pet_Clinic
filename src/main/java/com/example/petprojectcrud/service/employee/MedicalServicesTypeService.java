package com.example.petprojectcrud.service.employee;

import com.example.petprojectcrud.DTO.employee.MedicalServiceTypeDto;

import java.util.List;


public interface MedicalServicesTypeService {
    public List<MedicalServiceTypeDto> getAllServiceTypes();
    public MedicalServiceTypeDto getServiceTypeById(int id);
}
