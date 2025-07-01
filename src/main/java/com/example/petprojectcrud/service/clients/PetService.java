package com.example.petprojectcrud.service.clients;

import com.example.petprojectcrud.DTO.clients.PetDto;
import com.example.petprojectcrud.enums.AnimalType;
import com.example.petprojectcrud.model.clients.Pet;

import java.util.List;
import java.util.Optional;

public interface PetService {
    public List<PetDto> getAllPets();
    public PetDto getPetById(Integer id);
    public PetDto createPet(PetDto pet);
    public PetDto updatePet(Integer id, PetDto pet);
    public void deletePet(Integer id);
    public List<PetDto> getPetsByName(String name);
    public List<PetDto> getPetsByOwnerName(String ownerName);
    public PetDto findFirstByOwner_PhoneAndNameAndAnimalType(String phone, String name, AnimalType animalType);
}
