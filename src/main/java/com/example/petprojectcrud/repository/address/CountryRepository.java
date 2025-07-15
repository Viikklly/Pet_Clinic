package com.example.petprojectcrud.repository.address;

import com.example.petprojectcrud.model.address.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Integer> {


}
