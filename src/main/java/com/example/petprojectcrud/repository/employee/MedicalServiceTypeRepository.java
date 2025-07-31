package com.example.petprojectcrud.repository.employee;

import com.example.petprojectcrud.enums.ServicesTypeEnum;
import com.example.petprojectcrud.model.employee.MedicalServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalServiceTypeRepository extends JpaRepository<MedicalServiceType, Integer> {

     Optional<MedicalServiceType> findByServicesTypeEnum(ServicesTypeEnum servicesTypeEnum);
}
