package com.example.petprojectcrud.service.address;


import com.example.petprojectcrud.model.address.Country;
import com.example.petprojectcrud.repository.address.CountryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Transactional
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    public Country save(Country country){
        return countryRepository.saveAndFlush(country);
    }
}
