package com.example.petprojectcrud.service.address;

import com.example.petprojectcrud.DTO.address.AddressDto;

import java.util.List;

public interface AddressService {
    List<AddressDto> getAllAddress();
    AddressDto createAddress(AddressDto addressDto);
    AddressDto getAddressByOwnerId(int id);
    AddressDto getAddressByOwnerNameAndOwnerPhone(String ownerName, String phoneNumber);
}
