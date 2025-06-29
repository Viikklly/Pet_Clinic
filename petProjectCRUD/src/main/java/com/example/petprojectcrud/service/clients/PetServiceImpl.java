package com.example.petprojectcrud.service.clients;

import com.example.petprojectcrud.DTO.clients.OwnerDto;
import com.example.petprojectcrud.DTO.clients.PetDto;
import com.example.petprojectcrud.model.clients.Owner;
import com.example.petprojectcrud.model.clients.Pet;
import com.example.petprojectcrud.repository.clients.OwnerReposotory;
import com.example.petprojectcrud.repository.clients.PetRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
@Transactional
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final OwnerService ownerService;
    private final OwnerReposotory ownerReposotory;


    @Override
    public List<PetDto> getAllPets() {
        return petRepository.findAll().stream().map(pet -> pet.toDto()).toList();
    }

    @Override
    public PetDto getPetById(Integer id) {
        Optional<Pet> petOptional = petRepository.findById(id);

        if (petOptional.isPresent()) {
            return petOptional.get().toDto();
        } else {
            // бросить свое исключение notFound
            // сейчас возвращается пустой объект
            return PetDto.builder().build();
        }
    }

    // Создаем нового питомца
    @Override
    public PetDto createPet(PetDto pet) {

        //создаем нового Pet
        Pet petCreate = new Pet();

        OwnerDto owner = pet.getOwner();

        petCreate.setName(pet.getName());
        petCreate.setAnimalType(pet.getAnimalType());
        petCreate.setBreed(pet.getBreed());
        petCreate.setAge(pet.getAge());
        petCreate.setVaccinations(pet.getVaccinations());


        //добавляем связь Pet c Owner
        // если id не 0
        if (owner.getId() != null) {
            // через ownerService ищем нашего owner
            //Получаем Owner  -  ownerReposotory.findById(id);

            Optional<Owner> ownerOptional = ownerReposotory.findById(owner.getId());

            // проверяем не пустой ли owner с таким id
            if (ownerOptional.isPresent()) {

                // добавляем owner в petCreate
                petCreate.setOwner(ownerOptional.get());

                //также, добавляем в список Owner нашего Pet
                ownerOptional.get().getPets().add(petCreate);

            } else {
                // добавить исключение с сообщением, что такого owner нет
                // или подумать, что делать, если пользователя с таким id нет
                throw new EntityNotFoundException("Owner not found");
            }

            // сохраняем изменения Cascad All сохраняем 1 раз
            ownerReposotory.save(ownerOptional.get());
        } else {
            throw new EntityNotFoundException("Owner not found");
        }

        return petCreate.toDto();
    }



    // Обновляем питомца
    @Override
    public PetDto updatePet(Integer id, PetDto pet) {

        // получили petDto по id
        Optional<Pet> petOptionalByID = petRepository.findById(id);

        // Если есть такой ПЕТ
        if (petOptionalByID.isPresent()) {

            // Pet из PetDto по id
            Pet petById = petOptionalByID.get();

            // если имя не null в pet
            if (pet.getName() != null) {
                petById.setName(pet.getName());
            }
            if (pet.getAnimalType() != null) {
                petById.setAnimalType(pet.getAnimalType());
            }
            if (pet.getBreed() != null) {
                petById.setBreed(pet.getBreed());
            }
            if (pet.getAge() != null) {
                petById.setAge(pet.getAge());
            }
            if (pet.getVaccinations() != null) {
                petById.setVaccinations(pet.getVaccinations());
            }
            return petRepository.save(petById).toDto();

        } else {
            throw new EntityNotFoundException("Pet not found");
        }
    }

    @Override
    public void deletePet(Integer id) {
        petRepository.deleteById(id);
    }
}
