package com.example.petprojectcrud.repository.clients;

import com.example.petprojectcrud.enums.AnimalType;
import com.example.petprojectcrud.model.clients.Owner;
import com.example.petprojectcrud.model.clients.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*
*  Здесь при помощи аннотации @Repository мы просим Spring
*  автоматически реализовать интерфейс репозитория для нашего
*  класса Pet.
*  PetRepository нужен для обеспечения доступа к данным в БД,
*  связанным с сущностью Pet в нашем приложении:
* */

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {

    List<Pet> findByNameContainsIgnoreCase(String name);

    List<Pet> findByOwnerNameContainsIgnoreCase(String ownerName);

    Optional<Pet> findFirstByOwner_PhoneAndNameAndAnimalType(String phone, String name, AnimalType animalType);

}
