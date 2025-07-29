package com.example.petprojectcrud.service.employee;


import com.example.petprojectcrud.DTO.employee.ServiceTypeDto;
import com.example.petprojectcrud.enums.ServicesTypeEnum;
import com.example.petprojectcrud.model.employee.ServiceType;
import com.example.petprojectcrud.repository.employee.ServiceTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class ServicesTypeServiceImpl implements ServicesTypeService {
    private ServiceTypeRepository serviceTypeRepository;


    @Override
    public List<ServiceTypeDto> getAllServiceTypes() {
        List<ServiceTypeDto> serviceTypeDtos = new ArrayList<>();
        serviceTypeRepository.findAll().forEach(serviceTypeDto -> serviceTypeDtos.add(serviceTypeDto.toDto()));
        return serviceTypeDtos;
    }

    @Override
    public ServiceTypeDto getServiceTypeById(int id) {
        Optional<ServiceType> serviceTypeRepositoryById = serviceTypeRepository.findById(id);
        if (serviceTypeRepositoryById.isPresent()) {
            return serviceTypeRepositoryById.get().toDto();
        } else {
            throw new EntityNotFoundException("Service Type by id "+ id +" not found. Create new Service Type");
        }
    }

}
