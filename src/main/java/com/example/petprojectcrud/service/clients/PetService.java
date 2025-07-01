package com.example.petprojectcrud.service.clients;

import com.example.petprojectcrud.DTO.clients.PetDto;

import java.util.List;

public interface PetService {
    public List<PetDto> getAllPets();
    public PetDto getPetById(Integer id);
    public PetDto createPet(PetDto pet);
    public PetDto updatePet(Integer id, PetDto pet);
    public void deletePet(Integer id);
    public List<PetDto> getPetsByName(String name);
    public List<PetDto> getPetsByOwnerName(String ownerName);
}
