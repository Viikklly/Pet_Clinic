package com.example.petprojectcrud.repository.address;

import com.example.petprojectcrud.model.address.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Integer> {
}
