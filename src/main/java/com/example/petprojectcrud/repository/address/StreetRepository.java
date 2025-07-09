package com.example.petprojectcrud.repository.address;

import com.example.petprojectcrud.model.address.Street;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreetRepository extends JpaRepository<Street, Integer> {
}
