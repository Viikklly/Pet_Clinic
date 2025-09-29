package com.example.petprojectcrud.controller.clients;

import com.example.petprojectcrud.DTO.clients.OwnerDto;
import com.example.petprojectcrud.DTO.clients.OwnerRequestDto;
import com.example.petprojectcrud.DTO.clients.OwnerResponseDto;
import com.example.petprojectcrud.service.clients.OwnerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/owners")
public class OwnerController {

    private final OwnerService ownerService;

    @GetMapping
    public List<OwnerResponseDto> getAllOwners() {
        return ownerService.getAllOwners();
    }

    @GetMapping("/{id}")
    public OwnerDto getOwnerById(@PathVariable Integer id) {
        return ownerService.getOwnerById(id);
    }



    @PostMapping
    public OwnerResponseDto createOwner(@RequestBody OwnerRequestDto owner) {
        return ownerService.createOwner(owner);
    }



/*
    @PostMapping
    public OwnerDto createOwner(@RequestBody OwnerDto owner) {
        return ownerService.createOwner(owner);
    }



 */


    @PutMapping("/{id}")
    public OwnerResponseDto updateOwner(@PathVariable Integer id, @RequestBody OwnerRequestDto owner) {
        return ownerService.updateOwner(id, owner);
    }

    @DeleteMapping("/{id}")
    public void deleteOwner(@PathVariable Integer id) {
        ownerService.deleteOwner(id);
    }

    //+BD
    // получение списка имен по части имени
    @GetMapping("/ownersByName/")
    public List<OwnerResponseDto> getOwnersByName(@RequestParam(value = "name") String name) {
        return ownerService.getOwnerByName(name);
    }


    //+BD
    // получение списка имен Owner по имени питомца
    @GetMapping("/ownerByPetName/")
    public List<OwnerResponseDto> getOwnersByPetName(@RequestParam(value = "petName") String petName) {
        return ownerService.getOwnerByPetName(petName);
    }

    // +BD
    // получение Owner по email
    @GetMapping("/ownerByEmail/")
    public OwnerResponseDto getOwnersByEmail(@RequestParam(value = "email") String email) {
        return ownerService.getOwnerByEmail(email);
    }
}
