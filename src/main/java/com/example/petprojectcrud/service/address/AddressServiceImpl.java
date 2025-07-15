package com.example.petprojectcrud.service.address;


import com.example.petprojectcrud.DTO.address.AddressDto;
import com.example.petprojectcrud.model.address.Address;
import com.example.petprojectcrud.model.address.City;
import com.example.petprojectcrud.model.address.Country;
import com.example.petprojectcrud.model.address.Street;
import com.example.petprojectcrud.repository.address.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;


    @Override
    public List<AddressDto> getAllAddress() {
        List<Address> list = addressRepository.findAll();
        return addressRepository.findAll()
                .stream()
                .map(address -> address.toDto()).toList();
    }

    @Override
    public AddressDto createAddress(AddressDto addressDto) {
        Address address = new Address();

        Country country = new Country();
        country.setCountryName(addressDto.getCountry());
        country.setAddress(address);
        address.setCountry(country);

        City city = new City();
        city.setCityName(addressDto.getCity());
        city.setAddress(address);
        address.setCity(city);

        Street street = new Street();
        street.setStreetName(addressDto.getStreet());
        street.setAddress(address);
        address.setStreet(street);

        address.setHouseNumber(addressDto.getHouseNumber());
        address.setApartmentNumber(addressDto.getApartmentNumber());

        addressRepository.save(address);



        return address.toDto();
    }


    @Override
    public AddressDto getAddressByOwnerId(int id) {
        return addressRepository.findByOwnerId(id).toDto();
    }

    @Override
    public AddressDto getAddressByOwnerNameAndOwnerPhone(String ownerName, String phoneNumber) {
        return addressRepository.findByOwnerNameAndOwnerPhone(ownerName, phoneNumber).toDto();
    }
}
