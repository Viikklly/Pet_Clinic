package com.example.petprojectcrud.service.clients;

import com.example.petprojectcrud.DTO.clients.OwnerDto;
import com.example.petprojectcrud.DTO.clients.OwnerRequestDto;
import com.example.petprojectcrud.DTO.clients.OwnerResponseDto;

import java.util.List;

public interface OwnerService {
    public List<OwnerResponseDto> getAllOwners();
    public OwnerResponseDto getOwnerById(Integer id);
    public OwnerResponseDto updateOwner(Integer id, OwnerRequestDto owner);


    public OwnerResponseDto createOwner(OwnerRequestDto owner);
    /*
    public OwnerDto createOwner(OwnerDto owner);

     */



    public void deleteOwner(Integer id);

    List<OwnerResponseDto> getOwnerByName(String name);
    //public List<OwnerDto> getOwnerByName(String name);


    // +BD
    public  List<OwnerResponseDto> getOwnerByPetName(String petName);
    //+BD
    public OwnerResponseDto getOwnerByEmail(String email);

}
