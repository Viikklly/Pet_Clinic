package com.example.petprojectcrud.repository.employee;

import com.example.petprojectcrud.model.employee.Services;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServicesRepository extends JpaRepository<Services, Integer> {
    Optional<Services> findByDescriptionContainsIgnoreCase(String description);
}
