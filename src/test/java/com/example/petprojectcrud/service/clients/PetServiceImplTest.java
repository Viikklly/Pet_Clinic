package com.example.petprojectcrud.service.clients;

import com.example.petprojectcrud.DTO.clients.OwnerDto;
import com.example.petprojectcrud.DTO.clients.PetDto;
import com.example.petprojectcrud.enums.AnimalType;
import com.example.petprojectcrud.model.clients.Owner;
import com.example.petprojectcrud.model.clients.Pet;
import com.example.petprojectcrud.repository.clients.OwnerRepository;
import com.example.petprojectcrud.repository.clients.PetRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.petprojectcrud.service.clients.OwnerServiceImplTest.getListOwners;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

class PetServiceImplTest {

    @Mock
    PetRepository petRepository;
    @Mock
    OwnerRepository ownerRepository;
    PetService petService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        petService = new PetServiceImpl(petRepository, ownerRepository);
    }

    @Test
    void getAllPets() {
        List<Pet> pets = getListOwners().get(0).getPets();

        Mockito.when(petRepository.findAll()).thenReturn(pets);

        List<PetDto> allPets = petService.getAllPets();

        Mockito.verify(petRepository, Mockito.times(1)).findAll();

        assertEquals(pets.size(), allPets.size());
        assertEquals(pets.get(0).getId(), allPets.get(0).getId());
        assertEquals(pets.get(0).getName(), allPets.get(0).getName());

    }

    @Test
    void getPetById() {

        List<Pet> pets = getListOwners().get(0).getPets();
        Integer idPet = pets.get(0).getId();

        Mockito.when(petRepository.findById(any())).thenReturn(Optional.of(pets.get(0)));

        PetDto petById = petService.getPetById(idPet);

        Mockito.verify(petRepository, Mockito.times(1)).findById(any());


        Assertions.assertEquals(petById.getId(), idPet);
        Assertions.assertEquals(petById.getName(), pets.get(0).getName());


    }

    @Test
    void createPet() {
        Owner owner = getListOwners().get(0);
        Pet pet = owner.getPets().get(0);


        Mockito.when(ownerRepository.findById(anyInt())).thenReturn(Optional.of(owner));
        Mockito.when(ownerRepository.save(any())).thenReturn(owner);

        PetDto servicePet = petService.createPet(pet.toDto());

        Mockito.verify(ownerRepository, Mockito.times(1)).findById(anyInt());
        Mockito.verify(ownerRepository, Mockito.times(1)).save(any());

        Assertions.assertEquals(pet.getName(), servicePet.getName());
        Assertions.assertEquals(pet.getOwner().getId(), servicePet.getOwnerId());

    }

    @Test
    void updatePet() {
        List<Pet> petList = getListOwners().get(0).getPets();
        Pet pet = petList.get(0);

        Pet petToUpdate = petList.get(0);
        Integer idPet = petToUpdate.getId();
        petToUpdate.setAnimalType(AnimalType.CAT);
        petToUpdate.setAge(45);

        Mockito.when(petRepository.findById(anyInt())).thenReturn(Optional.of(petToUpdate));
        Mockito.when(petRepository.save(any())).thenReturn(petToUpdate);

        PetDto petServiceUpdate = petService.updatePet(idPet, petToUpdate.toDto());

        Mockito.verify(petRepository, Mockito.times(1)).findById(anyInt());
        Mockito.verify(petRepository, Mockito.times(1)).save(any());

        Assertions.assertEquals(petToUpdate.getId(), petServiceUpdate.getId());
        Assertions.assertEquals(petToUpdate.getName(), petServiceUpdate.getName());
        Assertions.assertEquals(petToUpdate.getAnimalType(), petServiceUpdate.getAnimalType());



    }

    @Test
    void deletePet() {
        petService.deletePet(any());

        Mockito.verify(petRepository, Mockito.times(1)).deleteById(any());
    }

    @Test
    void getPetsByName() {
        List<Pet> petList = getListOwners().get(0).getPets();
        String petName = petList.get(0).getName();

        Mockito.when(petRepository.findByNameContainsIgnoreCase(any())).thenReturn(petList);

        List<PetDto> petsByName = petService.getPetsByName(petName);

        Mockito.verify(petRepository, Mockito.times(1)).findByNameContainsIgnoreCase(any());
        assertEquals(petList.size(), petsByName.size());
        assertEquals(petList.get(0).getId(), petsByName.get(0).getId());
        assertEquals(petList.get(0).getName(), petsByName.get(0).getName());
    }

    @Test
    void getPetsByOwnerName() {
        List<Pet> petList = getListOwners().get(0).getPets();
        String ownerName = petList.get(0).getOwner().getName();

        Mockito.when(petRepository.findByOwnerNameContainsIgnoreCase(any())).thenReturn(petList);

        List<PetDto> petsByOwnerName = petService.getPetsByOwnerName(ownerName);

        Mockito.verify(petRepository, Mockito.times(1)).findByOwnerNameContainsIgnoreCase(any());
        assertEquals(petList.size(), petsByOwnerName.size());
        assertEquals(petList.get(0).getId(), petsByOwnerName.get(0).getId());
        assertEquals(petList.get(0).getName(), petsByOwnerName.get(0).getName());

    }

    @Test
    void findFirstByOwnerPhoneAndNameAndAnimalType() {
        List<Pet> petList = getListOwners().get(0).getPets();
        Pet pet = petList.get(0);
        Owner owner = pet.getOwner();
        String phone = owner.getPhone();
        String ownerName = owner.getName();
        AnimalType animalType = pet.getAnimalType();

        Mockito.when(petRepository.findFirstByOwner_PhoneAndNameAndAnimalType(any(), any(), any())).thenReturn(Optional.of(pet));

        PetDto firstByOwnerPhoneAndNameAndAnimalType = petService.findFirstByOwnerPhoneAndNameAndAnimalType(phone, ownerName, animalType);

        Mockito.verify(petRepository, Mockito.times(1)).findFirstByOwner_PhoneAndNameAndAnimalType(any(), any(), any());

        assertEquals(owner.getId(), firstByOwnerPhoneAndNameAndAnimalType.getOwnerId());
        assertEquals(pet.getAnimalType(), firstByOwnerPhoneAndNameAndAnimalType.getAnimalType());


    }

    @Test
    void findFirstByOwnerPhoneAndNameAndAnimalTypeException(){
        List<Pet> petList = getListOwners().get(0).getPets();
        Pet pet = petList.get(0);

        Mockito.when(petRepository.findFirstByOwner_PhoneAndNameAndAnimalType(any(), any(), any())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> petService.findFirstByOwnerPhoneAndNameAndAnimalType(any(), any(), any()));
    }

}