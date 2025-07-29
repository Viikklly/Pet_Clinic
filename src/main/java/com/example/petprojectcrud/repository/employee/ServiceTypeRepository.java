package com.example.petprojectcrud.repository.employee;

import com.example.petprojectcrud.enums.ServicesTypeEnum;
import com.example.petprojectcrud.model.employee.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, Integer> {

     Optional<ServiceType> findByServicesTypeEnum(ServicesTypeEnum servicesTypeEnum);
}
