package com.example.petprojectcrud.service.employee;

import com.example.petprojectcrud.DTO.employee.MedicalServicesDto;

import java.util.List;

public interface MedicalServicesService {
    public List<MedicalServicesDto> getAllServices();
    public MedicalServicesDto createService(MedicalServicesDto medicalServicesDto);
    public MedicalServicesDto updateService(Integer id, MedicalServicesDto medicalServicesDto);
    public void deleteService(Integer id);
}
