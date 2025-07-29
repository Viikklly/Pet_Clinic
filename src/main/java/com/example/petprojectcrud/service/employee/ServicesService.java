package com.example.petprojectcrud.service.employee;

import com.example.petprojectcrud.DTO.employee.ServicesDto;

import java.util.List;

public interface ServicesService {
    public List<ServicesDto> getAllServices();
    public ServicesDto createService(ServicesDto servicesDto);
    public ServicesDto updateService(Integer id, ServicesDto servicesDto);
    public void deleteService(Integer id);
}
