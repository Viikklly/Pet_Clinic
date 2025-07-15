package com.example.petprojectcrud.controller.address;


import com.example.petprojectcrud.DTO.address.AddressDto;
import com.example.petprojectcrud.service.address.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/address")
public class AddressController {
    private AddressService addressService;

    @GetMapping
    public List<AddressDto> getAllOwners() {
        return addressService.getAllAddress();
    }


    @PostMapping
    public void addAddress(@RequestBody AddressDto addressDto) {
        addressService.createAddress(addressDto);
    }

    @GetMapping("/getAddressByOwnerId/")
    public AddressDto getAddressByOwnerId(@RequestParam(name = "Owner Id") int id) {
        return addressService.getAddressByOwnerId(id);
    }

    @GetMapping("/getAddressByOwnerNameAndOwnerPhone/")
    public AddressDto getAddressByOwnerNameAndOwnerPhone(@RequestParam(name = "Owner Name") String ownerName,
                                                         @RequestParam(name = "Owner Phone") String ownerPhone) {
        return addressService.getAddressByOwnerNameAndOwnerPhone(ownerName, ownerPhone);
    }

}
