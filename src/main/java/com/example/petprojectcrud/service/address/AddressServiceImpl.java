package com.example.petprojectcrud.service.address;

import com.example.petprojectcrud.DTO.address.AddressDto;
import com.example.petprojectcrud.repository.address.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
@Transactional
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    @Override
    public List<AddressDto> getAllAddress() {
        return addressRepository.findAll().stream().map(address -> address.toDto()).collect(Collectors.toList());
    }
}
