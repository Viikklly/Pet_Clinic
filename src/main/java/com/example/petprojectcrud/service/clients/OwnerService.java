package com.example.petprojectcrud.service.clients;

import com.example.petprojectcrud.DTO.clients.OwnerDto;

import java.util.List;

public interface OwnerService {
    public List<OwnerDto> getAllOwners();
    public OwnerDto getOwnerById(Integer id);
    public OwnerDto updateOwner(Integer id, OwnerDto owner);


    public OwnerDto createOwner(OwnerDto owner);
    /*
    public OwnerDto createOwner(OwnerDto owner);

     */



    public void deleteOwner(Integer id);

    List<OwnerDto> getOwnerByName(String name);
    //public List<OwnerDto> getOwnerByName(String name);


    // +BD
    public  List<OwnerDto> getOwnerByPetName(String petName);
    //+BD
    public OwnerDto getOwnerByEmail(String email);

}
