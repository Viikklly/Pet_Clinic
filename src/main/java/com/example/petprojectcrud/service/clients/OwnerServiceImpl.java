package com.example.petprojectcrud.service.clients;


import com.example.petprojectcrud.DTO.clients.OwnerDto;
import com.example.petprojectcrud.DTO.clients.PetDto;
import com.example.petprojectcrud.model.address.Address;
import com.example.petprojectcrud.model.address.City;
import com.example.petprojectcrud.model.address.Country;
import com.example.petprojectcrud.model.address.Street;
import com.example.petprojectcrud.model.clients.Owner;
import com.example.petprojectcrud.model.clients.Pet;
import com.example.petprojectcrud.repository.address.AddressRepository;
import com.example.petprojectcrud.repository.address.CountryRepository;
import com.example.petprojectcrud.repository.clients.OwnerRepository;
import com.example.petprojectcrud.repository.clients.PetRepository;
import com.example.petprojectcrud.service.address.CountryService;
import com.example.petprojectcrud.service.address.CountryServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@AllArgsConstructor
@Service
@Transactional
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;




    @Override
    public List<OwnerDto> getAllOwners() {
        return ownerRepository.findAll().stream()
                .sorted(Comparator.comparing(Owner::getId))
                .map(owner -> owner.toDto())
                .toList();
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
    public OwnerDto updateOwner(Integer id, OwnerDto ownerDto) {
        //получили старого owner по id
        Optional<Owner> ownerOptional = ownerRepository.findById(id);

        if (ownerOptional.isPresent()) {

            Owner oldOwner = ownerOptional.get();
            List<Pet> petsListOwnerEntity = oldOwner.getPets();

            setOwnerNameEmailPhone(ownerDto, oldOwner);
            petsCreateOrUpdate(ownerDto, oldOwner, petsListOwnerEntity);
            addressCreateOrUpdate(ownerDto, oldOwner);

            Owner updatedOwner = ownerRepository.save(oldOwner);

            return updatedOwner.toDto();

        } else {
            // бросить свое исключение notFound
            throw new EntityNotFoundException("Owner not found");
        }
    }

    @Override
    public OwnerDto createOwner(OwnerDto ownerDto) {
        //создаем нового Owner
        Owner newOwner = new Owner();

        setOwnerNameEmailPhone(ownerDto, newOwner);

        ownerRepository.save(newOwner);

        List<Pet> petsOwnerList = new ArrayList<Pet>();



        if (!ownerDto.getPets().isEmpty()) {
            for (PetDto pet : ownerDto.getPets()) {

                Pet newPet = getNewPet(pet);

                newPet.setOwner(newOwner);

                newOwner.getPets().add(newPet);
            }
        }

        addressCreateOrUpdate(ownerDto, newOwner);

        /*Owner save = */ownerRepository.save(newOwner);


        return newOwner.toDto();
    }

    //Вместо удаления теперь меняется поле is_active(сделать)
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

    // получение списка имен Owner по имени питомца
    @Override
    public List<OwnerDto> getOwnerByPetName(String petName) {
        List<Owner> ownerByPetsName = ownerRepository.findOwnerByPetsName(petName);
        return ownerByPetsName.stream().map(owner -> owner.toDto()).toList();
    }

    @Override
    public OwnerDto getOwnerByEmail(String email) {
        List<Owner> byEmail = ownerRepository.findByEmail(email);
        return  byEmail.stream().findFirst().get().toDto();
    }





    private Pet getNewPet(PetDto petDto) {
        Pet petNew = new Pet();
        petNew.setName(petDto.getName());
        petNew.setAnimalType(petDto.getAnimalType());
        petNew.setBreed(petDto.getBreed());
        petNew.setAge(petDto.getAge());
        petNew.setVaccinations(petDto.getVaccinations());

        return petNew;
    }

    private Pet getUpdarePetDtoFromPet(Pet pet, PetDto petDto) {
        pet.setName(petDto.getName());
        pet.setAnimalType(petDto.getAnimalType());
        pet.setBreed(petDto.getBreed());
        pet.setAge(petDto.getAge());
        pet.setVaccinations(petDto.getVaccinations());
        return pet;
    }

    private void setOwnerNameEmailPhone(OwnerDto ownerDto, Owner oldOwner) {
        if (ownerDto.getName() != null) {
            oldOwner.setName(ownerDto.getName());
        }
        if (ownerDto.getEmail() != null) {
            oldOwner.setEmail(ownerDto.getEmail());
        }
        if (ownerDto.getPhone() != null) {
            oldOwner.setPhone(ownerDto.getPhone());
        }
    }



    private void petsCreateOrUpdate(OwnerDto ownerDto,
                                    Owner oldOwner,
                                    List<Pet> petsListOwnerEntity ) {
        if (!ownerDto.getPets().isEmpty()) {

            ownerDto.getPets().forEach(petDto -> {

                if (petDto.getId() == null){
                    Pet newPet = getNewPet(petDto);

                    newPet.setOwner(oldOwner);
                    oldOwner.getPets().add(newPet);
                } else {
                    Integer idPetDto = petDto.getId();
                    Optional<Pet> optionalPet = petsListOwnerEntity.stream()
                            .filter(pet -> Objects.equals(pet.getId(), idPetDto))
                            .findFirst();
                    //.ifPresent(pet -> getUpdarePetDtoFromPet(pet, petDto));
                    if (optionalPet.isPresent()) {
                        getUpdarePetDtoFromPet(optionalPet.get(), petDto);
                    } else {
                        throw new EntityNotFoundException("Pets with this Id not found");
                    }
                }
            });
        }

    }

    private void addressCreateOrUpdate(OwnerDto ownerDto, Owner oldOwner){
        Address address = null;
        if (ownerDto.getAddress() != null){

            address = new Address();

            if (ownerDto.getAddress().getCountry() != null) {
                Country country = new Country(ownerDto.getAddress().getCountry());
                country.setAddress(address);
                address.setCountry(country);
            } else {
                Country countryNew = new Country();
                countryNew.setCountryName(ownerDto.getAddress().getCountry());
                countryNew.setAddress(address);
                address.setCountry(countryNew);
            }

            if (ownerDto.getAddress().getCity() != null) {
                City city = new City(ownerDto.getAddress().getCity());
                city.setAddress(address);
                address.setCity(city);
            } else {
                City cityNew = new City();
                cityNew.setCityName(ownerDto.getAddress().getCity());
                cityNew.setAddress(address);
                address.setCity(cityNew);
            }

            if (ownerDto.getAddress().getStreet() != null) {
                Street street = new Street(ownerDto.getAddress().getStreet());
                street.setAddress(address);
                address.setStreet(street);
            } else {
                Street streetNew = new Street();
                streetNew.setStreetName(ownerDto.getAddress().getStreet());
                streetNew.setAddress(address);
                address.setStreet(streetNew);
            }

            if (ownerDto.getAddress().getHouseNumber() != null) {
                address.setHouseNumber(ownerDto.getAddress().getHouseNumber());
            } else {
                address.setHouseNumber(ownerDto.getAddress().getHouseNumber());
            }

            if (ownerDto.getAddress().getApartmentNumber() != null) {
                address.setApartmentNumber(ownerDto.getAddress().getApartmentNumber());
            } else {
                address.setApartmentNumber(ownerDto.getAddress().getApartmentNumber());
            }

            oldOwner.setAddress(address);
        }
    }
}
