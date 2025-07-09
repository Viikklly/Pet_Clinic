package com.example.petprojectcrud.controller.address;


import com.example.petprojectcrud.DTO.address.AddressDto;
import com.example.petprojectcrud.model.address.Address;
import com.example.petprojectcrud.service.address.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    public List<AddressDto> getAllAddress() {
        return addressService.getAllAddress();
    }
}
