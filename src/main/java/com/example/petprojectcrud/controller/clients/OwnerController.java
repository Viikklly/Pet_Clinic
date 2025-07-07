package com.example.petprojectcrud.controller.clients;

import com.example.petprojectcrud.DTO.clients.OwnerDto;
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
    public List<OwnerDto> getAllOwners() {
        return ownerService.getAllOwners();
    }

    @GetMapping("/{id}")
    public OwnerDto getOwnerById(@PathVariable Integer id) {
        return ownerService.getOwnerById(id);
    }

    @PostMapping
    public OwnerDto createOwner(@RequestBody OwnerDto owner) {
        return ownerService.createOwner(owner);
    }

    @PutMapping("/{id}")
    public OwnerDto updateOwner(@PathVariable Integer id, @RequestBody OwnerDto owner) {
        return ownerService.updateOwner(id, owner);
    }

    @DeleteMapping("/{id}")
    public void deleteOwner(@PathVariable Integer id) {
        ownerService.deleteOwner(id);
    }

    // получение списка имен по части имени
    @GetMapping("/ownersByName/")
    public List<OwnerDto> getOwnersByName(@RequestParam(value = "name") String name) {
        return ownerService.getOwnerByName(name);
    }


    // получение списка имен Owner по имени питомца
    @GetMapping("/ownerByPetName/")
    public List<OwnerDto> getOwnersByPetName(@RequestParam(value = "petName") String petName) {
        return ownerService.getOwnerByPetName(petName);
    }

    // получение Owner по email
    @GetMapping("/ownerByEmail/")
    public OwnerDto getOwnersByEmail(@RequestParam(value = "email") String email) {
        return ownerService.getOwnerByEmail(email);
    }



}
