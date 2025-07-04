package com.example.petprojectcrud.service.clients;


import com.example.petprojectcrud.DTO.clients.OwnerDto;
import com.example.petprojectcrud.model.clients.Owner;
import com.example.petprojectcrud.model.clients.Pet;
import com.example.petprojectcrud.repository.clients.OwnerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;


    @Override
    public List<OwnerDto> getAllOwners() {
        return ownerRepository.findAll().stream().map(owner -> owner.toDto()).toList();
    }

    @Override
    public OwnerDto getOwnerById(Integer id) {
        Optional<Owner> ownerOptional = ownerRepository.findById(id);
        if (ownerOptional.isPresent()) {
            return ownerOptional.get().toDto();
        } else {
            // // бросить свое исключение notFound
            // сейчас возвращается пустой объект
            return OwnerDto.builder().build();
        }
    }

    @Override
    public OwnerDto updateOwner(Integer id, OwnerDto owner) {
        //получили старого owner по id
        Optional<Owner> ownerOptional = ownerRepository.findById(id);

        if (ownerOptional.isPresent()) {
            Owner oldOwner = ownerOptional.get();
            // список
            List<Pet> petsOldOwnerList = oldOwner.getPets();

            if (owner.getName() != null) {
                oldOwner.setName(owner.getName());
            }
            if (owner.getEmail() != null) {
                oldOwner.setEmail(owner.getEmail());
            }
            if (owner.getPhone() != null) {
                oldOwner.setPhone(owner.getPhone());
            }
            if (!owner.getPets().isEmpty()) {
                owner.getPets().forEach(pet -> {
                    Pet petNew = new Pet();

                    petNew.setName(pet.getName());
                    petNew.setAnimalType(pet.getAnimalType());
                    petNew.setBreed(pet.getBreed());
                    petNew.setAge(pet.getAge());
                    petNew.setVaccinations(pet.getVaccinations());
                    petNew.setOwner(oldOwner);

                    petsOldOwnerList.add(petNew);
                });
            }

            ownerRepository.save(oldOwner);

            return oldOwner.toDto();

        } else {
            // бросить свое исключение notFound
            throw new EntityNotFoundException("Owner not found");
        }
    }

    @Override
    public OwnerDto createOwner(OwnerDto owner) {
        //создаем нового Owner
        Owner newOwner = new Owner();

        newOwner.setName(owner.getName());
        newOwner.setEmail(owner.getEmail());
        newOwner.setPhone(owner.getPhone());
        ownerRepository.save(newOwner);

        // List питомцев
        List<Pet> petsOwnerList = new ArrayList<Pet>();

        if (!owner.getPets().isEmpty()) {
            owner.getPets().forEach(pet -> {
                Pet petNew = new Pet();
                petNew.setName(pet.getName());
                petNew.setAnimalType(pet.getAnimalType());
                petNew.setBreed(pet.getBreed());
                petNew.setAge(pet.getAge());
                petNew.setVaccinations(pet.getVaccinations());
                petNew.setOwner(newOwner);

                newOwner.getPets().add(petNew);
            });
        }

        ownerRepository.save(newOwner);


        return newOwner.toDto();
    }

    @Override
    public void deleteOwner(Integer id) {
        ownerRepository.deleteById(id);
    }

    // получение списка имен Owner по части имени
    @Override
    public List<OwnerDto> getOwnerByName(String name) {
        List<Owner> byNameLikeOwner = ownerRepository.findByNameContainsIgnoreCase(name);
        List<OwnerDto> listNameLikeOwnerDto = byNameLikeOwner.stream().map(owner -> owner.toDto()).toList();
        return listNameLikeOwnerDto;
    }



}
