package com.example.petprojectcrud.service.employee;


import com.example.petprojectcrud.DTO.employee.MedicalServiceTypeDto;
import com.example.petprojectcrud.model.employee.MedicalServiceType;
import com.example.petprojectcrud.repository.employee.MedicalServiceTypeRepository;
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
public class MedicalServicesTypeServiceImpl implements MedicalServicesTypeService {
    private MedicalServiceTypeRepository medicalServiceTypeRepository;


    @Override
    public List<MedicalServiceTypeDto> getAllServiceTypes() {
        List<MedicalServiceTypeDto> medicalServiceTypeDtos = new ArrayList<>();
        medicalServiceTypeRepository.findAll().forEach(serviceTypeDto -> medicalServiceTypeDtos.add(serviceTypeDto.toDto()));
        return medicalServiceTypeDtos;
    }

    @Override
    public MedicalServiceTypeDto getServiceTypeById(int id) {
        Optional<MedicalServiceType> serviceTypeRepositoryById = medicalServiceTypeRepository.findById(id);
        if (serviceTypeRepositoryById.isPresent()) {
            return serviceTypeRepositoryById.get().toDto();
        } else {
            throw new EntityNotFoundException("Service Type by id "+ id +" not found. Create new Service Type");
        }
    }

}
