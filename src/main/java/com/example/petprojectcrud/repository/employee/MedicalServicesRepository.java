package com.example.petprojectcrud.repository.employee;

import com.example.petprojectcrud.model.employee.MedicalServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface MedicalServicesRepository extends JpaRepository<MedicalServices, Integer> {
    Optional<MedicalServices> findByDescriptionContainsIgnoreCase(String description);
    Set<MedicalServices> findAllByIdIn(List<Integer> ids);
}
