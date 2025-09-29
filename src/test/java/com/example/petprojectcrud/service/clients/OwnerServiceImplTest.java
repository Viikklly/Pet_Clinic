package com.example.petprojectcrud.service.clients;

import com.example.petprojectcrud.DTO.clients.OwnerDto;
import com.example.petprojectcrud.DTO.clients.OwnerRequestDto;
import com.example.petprojectcrud.DTO.clients.OwnerResponseDto;
import com.example.petprojectcrud.DTO.clients.PetDto;
import com.example.petprojectcrud.enums.AnimalType;
import com.example.petprojectcrud.model.clients.Owner;
import com.example.petprojectcrud.model.clients.Pet;
import com.example.petprojectcrud.repository.clients.OwnerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;


class OwnerServiceImplTest {
    @Mock
    OwnerRepository ownerRepository;
    OwnerService ownerService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ownerService = new OwnerServiceImpl(ownerRepository);
    }


    @Test
    void getAllOwners() {
        List<Owner> owners = getListOwners();

        Mockito.when(ownerRepository.findAll()).thenReturn(owners);


        List<OwnerResponseDto> allOwners = ownerService.getAllOwners();


        Mockito.verify(ownerRepository, Mockito.times(1)).findAll();

        Assertions.assertEquals(allOwners.size(), owners.size());
        Assertions.assertEquals(allOwners.get(0).getId(), owners.get(0).getId());
        Assertions.assertEquals(allOwners.get(0).getPhone(), owners.get(0).getPhone());

    }

    @Test
    void getOwnerById() {
        List<Owner> owners = getListOwners();
        Integer idOwners = owners.get(0).getId();

        Mockito.when(ownerRepository.findById(idOwners)).thenReturn(Optional.of(owners.get(0)));

        OwnerDto ownerDto = ownerService.getOwnerById(idOwners);

        Mockito.verify(ownerRepository, Mockito.times(1)).findById(idOwners);

        Assertions.assertEquals(ownerDto.getId(), idOwners);
        Assertions.assertEquals(ownerDto.getPhone(), owners.get(0).getPhone());
    }

//    @Test
//    void updateOwnerException(){
//        OwnerDto ownerDto = getListOwners().get(0).toDto();
//        Mockito.when(ownerRepository.findById(any())).thenReturn(Optional.empty());
//        Assertions.assertThrows(EntityNotFoundException.class, () -> ownerService.updateOwner(any(), ownerDto));
//    }

//    @Test
//    void updateOwner() {
//        Owner owner1 = getListOwners().get(0);
//        Owner owner2 = getListOwners().get(0);
//        owner2.setName("new name");
//        owner2.getPets().get(0).setName("new name Pet");
//        owner2.getPets().get(0).setAge(1);
//
//        OwnerDto dto1 = owner1.toDto();
//        OwnerDto dto2 = owner2.toDto();
//
//        PetDto petDto = dto2.getPets().get(0);
//
//
//        Mockito.when(ownerRepository.findById(owner1.getId())).thenReturn(Optional.of(owner1));
//        Mockito.when(ownerRepository.save(owner1)).thenReturn(owner2);
//
//        OwnerDto ownerDtoUpdate = ownerService.updateOwner(1, dto2);
//
//        Mockito.verify(ownerRepository, Mockito.times(1)).findById(any());
//        Mockito.verify(ownerRepository, Mockito.times(1)).save(any());
//
//        Assertions.assertEquals(ownerDtoUpdate.getId(), dto2.getId());
//        Assertions.assertEquals(ownerDtoUpdate.getName(), dto2.getName());
//        Assertions.assertEquals(ownerDtoUpdate.getEmail(), dto2.getEmail());
//        Assertions.assertEquals(ownerDtoUpdate.getPhone(), dto2.getPhone());
//
//        PetDto ownerUpdatePetDto = ownerDtoUpdate.getPets().get(0);
//
//        Assertions.assertEquals(petDto.getId(), ownerUpdatePetDto.getId());
//        Assertions.assertEquals(petDto.getName(), ownerUpdatePetDto.getName());
//        Assertions.assertEquals(petDto.getAge(), ownerUpdatePetDto.getAge());
//        Assertions.assertEquals(petDto.getOwnerId(), ownerUpdatePetDto.getOwnerId());
//
//    }

    @Test
    void createOwner() {
        Owner owner = getListOwners().get(0);
        OwnerDto ownerDto = owner.toDto();

        Mockito.when(ownerRepository.save(any())).thenReturn(owner);

        //OwnerDto ownerDtoSaved = ownerService.createOwner(ownerDto);

        Mockito.verify(ownerRepository, Mockito.times(2)).save(any());

        //Assertions.assertEquals(ownerDtoSaved.getName(), ownerDto.getName());

    }


    // переделать под isActive
    @Test
    void deleteOwner() {
        ownerService.deleteOwner(any());
        Mockito.verify(ownerRepository, Mockito.times(1)).deleteById(any());
    }

    @Test
    void getOwnerByName() {
        List<Owner> listOwners = getListOwners();

        Mockito.when(ownerRepository.findByNameContainsIgnoreCase(any())).thenReturn(listOwners);

        List<OwnerResponseDto> ownerByName = ownerService.getOwnerByName("John Doe");

        Mockito.verify(ownerRepository, Mockito.times(1)).findByNameContainsIgnoreCase(any());

        Assertions.assertEquals(ownerByName.get(0).getName(), listOwners.get(0).getName());
    }

    /*
    @Test
    void getOwnerByPetName() {
        List<Owner> listOwners = getListOwners();

        Mockito.when(ownerRepository.findOwnerByPetsName(any())).thenReturn(listOwners);

        //List<OwnerResponseDto> ownerByPetName = ownerService.getOwnerByPetName("Cha-Cha");

        Mockito.verify(ownerRepository, Mockito.times(1)).findOwnerByPetsName(any());

       // Assertions.assertEquals(ownerByPetName.get(0).getPets().get(0).getName(), listOwners.get(0).getPets().get(0).getName());
    }

     */

    @Test
    void getOwnerByEmail() {

        List<Owner> listOwners = getListOwners();


        Mockito.when(ownerRepository.findByEmail(any())).thenReturn(listOwners);

        OwnerDto ownerByEmail = ownerService.getOwnerByEmail("john.doe@example.com");

        Mockito.verify(ownerRepository, Mockito.times(1)).findByEmail(any());

        Assertions.assertEquals(ownerByEmail.getEmail(), listOwners.get(0).getEmail());

    }


    static List<Owner> getListOwners() {
        List<Owner> ownerList = new ArrayList<>();
        Owner owner = new Owner();
        owner.setId(1);
        owner.setName("John Doe");
        owner.setEmail("john.doe@example.com");
        owner.setPhone("123456789");

        List<Pet> petList = new ArrayList<>();
        Pet pet = new Pet();
        pet.setId(1);
        pet.setOwner(owner);
        pet.setName("Cha-Cha");
        pet.setAnimalType(AnimalType.DOG);
        pet.setBreed("BullDog");
        pet.setAge(3);
        pet.setVaccinations("All");
        petList.add(pet);

        owner.setPets(petList);
        ownerList.add(owner);

        return ownerList;
    }
}