package com.example.petprojectcrud.service.clients;

import com.example.petprojectcrud.DTO.billingDetails.BillingDetailsCreateDto;
import com.example.petprojectcrud.DTO.clients.OwnerDto;
import com.example.petprojectcrud.DTO.clients.OwnerRequestDto;
import com.example.petprojectcrud.DTO.clients.OwnerResponseDto;
import com.example.petprojectcrud.DTO.clients.PetDto;
import com.example.petprojectcrud.enums.BillingType;
import com.example.petprojectcrud.model.address.Address;
import com.example.petprojectcrud.model.address.City;
import com.example.petprojectcrud.model.address.Country;
import com.example.petprojectcrud.model.address.Street;
import com.example.petprojectcrud.model.billingDetails.BankAccount;
import com.example.petprojectcrud.model.billingDetails.BillingDetails;
import com.example.petprojectcrud.model.billingDetails.CreditCard;
import com.example.petprojectcrud.model.clients.Owner;
import com.example.petprojectcrud.model.clients.Pet;
import com.example.petprojectcrud.repository.clients.OwnerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@AllArgsConstructor
@Service
@Transactional
public class OwnerServiceImpl implements OwnerService{

    private final OwnerRepository ownerRepository;

    /// +BD
    @Override
    public List<OwnerResponseDto> getAllOwners() {
        return ownerRepository.findAll().stream()
                .sorted(Comparator.comparing(Owner::getId))
                .map(owner -> owner.toResponceDto())
                .toList();
    }

    /// +BD
    @Override
    public OwnerResponseDto getOwnerById(Integer id) {
        Optional<Owner> ownerOptional = ownerRepository.findById(id);
        if (ownerOptional.isPresent()) {
            return ownerOptional.get().toResponceDto();
        } else {
            // бросить свое исключение notFound
            // сейчас возвращается пустой объект
            return OwnerResponseDto.builder().build();
        }
    }

    /// +BD
    @Override
    public OwnerResponseDto updateOwner(Integer id, OwnerRequestDto ownerDto) {
        //получили старого owner по id
        Optional<Owner> ownerOptional = ownerRepository.findById(id);

        if (ownerOptional.isPresent()) {

            Owner oldOwner = ownerOptional.get();

            List<Pet> petsListOwnerEntity = oldOwner.getPets();
            Set<BillingDetails> oldOwnerBillingDetails = oldOwner.getBillingDetails();

            setOwnerNameEmailPhone(ownerDto, oldOwner);
            petsCreateOrUpdate(ownerDto, oldOwner);
            addressCreateOrUpdate(ownerDto, oldOwner);

            billingDetailsCreateOrUpdate(ownerDto, oldOwner);

            Owner updatedOwner = ownerRepository.save(oldOwner);

            return updatedOwner.toResponceDto();

        } else {
            // бросить свое исключение notFound
            throw new EntityNotFoundException("Owner not found");
        }
    }

    /// +BD
    @Override
    public OwnerResponseDto createOwner(OwnerRequestDto ownerRequestDto) {
        //создаем нового Owner
        Owner newOwner = new Owner();

        setOwnerNameEmailPhone(ownerRequestDto, newOwner);

        ownerRepository.save(newOwner);

        List<Pet> petsOwnerList = new ArrayList<Pet>();



        if (!ownerRequestDto.getPets().isEmpty()) {
            for (PetDto pet : ownerRequestDto.getPets()) {

                Pet newPet = getNewPet(pet);

                newPet.setOwner(newOwner);

                newOwner.getPets().add(newPet);
            }
        }


        if (!ownerRequestDto.getBillingDetails().isEmpty()) {

            List<BillingDetailsCreateDto> billingDetails = ownerRequestDto.getBillingDetails();
            for (BillingDetailsCreateDto billingDetail : billingDetails) {

                BillingDetails billingDetailsEntity = createBillingDetailsForOwner(billingDetail);

                billingDetailsEntity.setOwner(newOwner);

                newOwner.getBillingDetails().add(billingDetailsEntity);
            }
        }


        addressCreateOrUpdate(ownerRequestDto, newOwner);

        /*Owner save = */ownerRepository.save(newOwner);


        return newOwner.toResponceDto();
    }

    @Override
    public void deleteOwner(Integer id) {
        ownerRepository.deleteById(id);
    }


    /// +BD
    @Override
    public List<OwnerResponseDto> getOwnerByName(String name) {
        List<Owner> byNameLikeOwner = ownerRepository.findByNameContainsIgnoreCase(name);
        //List<OwnerDto> listNameLikeOwnerDto = byNameLikeOwner.stream().map(owner -> owner.toDto()).toList();
        List<OwnerResponseDto> listNameLikeOwnerDto = byNameLikeOwner.stream().map(owner -> owner.toResponceDto()).toList();
        return listNameLikeOwnerDto;
    }

    // +BD
    @Override
    public List<OwnerResponseDto> getOwnerByPetName(String petName) {
        List<Owner> ownerByPetsName = ownerRepository.findOwnerByPetsName(petName);
        return ownerByPetsName.stream().map(owner -> owner.toResponceDto()).toList();
    }

    @Override
    public OwnerResponseDto getOwnerByEmail(String email) {
        List<Owner> byEmail = ownerRepository.findByEmail(email);
        Optional<Owner> optionalOwner = byEmail.stream().findFirst();
        if (optionalOwner.isPresent()) {
            return optionalOwner.get().toResponceDto();
        } else {
            // Подумать что тут выдавать, если по эмайл не найден
            throw new EntityNotFoundException("Owner not found");
        }
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


    private Pet getUpdatePetDtoFromPet(Pet pet, PetDto petDto) {
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

    private void petsCreateOrUpdate(OwnerRequestDto ownerRequestDto, Owner oldOwner) {

        List<PetDto> requestDtoPets = ownerRequestDto.getPets();
        List<Pet> oldOwnerPets = oldOwner.getPets();

        if (CollectionUtils.isNotEmpty(requestDtoPets)) {

            ownerRequestDto.getPets().forEach(petDto -> {

                if (petDto.getId() == null) {
                    Pet newPet = getNewPet(petDto);

                    newPet.setOwner(oldOwner);
                    oldOwner.getPets().add(newPet);

                } else if (petDto.getId() != null) {

                    Optional<Pet> optionalPet = oldOwnerPets.stream()
                            .filter(petsOwner -> petsOwner.getId().equals(petDto.getId())).findFirst();

                    if (optionalPet.isPresent()) {
                        getUpdatePetDtoFromPet(optionalPet.get(), petDto);
                    }
                } else {
                    throw new EntityNotFoundException("Pets with this Id not found");
                }
            });
        }
    }

    private void billingDetailsCreateOrUpdate(OwnerRequestDto ownerRequestDto, Owner oldOwner) {

        // получаем Сет BD нашего owner
        Set<BillingDetails> oldOwnerBillingDetails = oldOwner.getBillingDetails();
        List<BillingDetailsCreateDto> billingDetailsFromDto = ownerRequestDto.getBillingDetails();

        // не пустой ли
        if (CollectionUtils.isNotEmpty(billingDetailsFromDto)) {

            // проходим по списку из DTO и смотрим есть ли эл с id null
            billingDetailsFromDto.forEach(billingDetailsCreateDto -> {
                if (billingDetailsCreateDto.getId() == null){
                    //создаем новый BD
                    BillingDetails billingDetailsForOwner = createBillingDetailsForOwner(billingDetailsCreateDto);

                    // Добавляем Owner в поле нового BD
                    billingDetailsForOwner.setOwner(oldOwner);

                    // Добавляем в Сет oldOwner
                    oldOwner.getBillingDetails().add(billingDetailsForOwner);

                    // если id не равно null ищем в списке owner
                } else if (billingDetailsCreateDto.getId() != null) {

                    Optional<BillingDetails> foundDetails = oldOwnerBillingDetails.stream()
                            .filter(bd -> bd.getId().equals(billingDetailsCreateDto.getId()))
                            .findFirst();

                    if (foundDetails.isPresent()) {
                        if (billingDetailsCreateDto.getBillingType() == BillingType.BANK_ACCOUNT) {

                            BankAccount foundDetailsBA = (BankAccount) foundDetails.get();

                            foundDetailsBA.setBillingType(BillingType.BANK_ACCOUNT);

                            foundDetailsBA.setAccountNumber(billingDetailsCreateDto.param1);
                            foundDetailsBA.setBankName(billingDetailsCreateDto.param2);
                            foundDetailsBA.setSwiftCode(billingDetailsCreateDto.param3);

                        } else if (billingDetailsCreateDto.getBillingType() == BillingType.CREDIT_CARD) {

                            CreditCard foundDetailsCC = (CreditCard) foundDetails.get();

                            foundDetailsCC.setBillingType(BillingType.CREDIT_CARD);

                            foundDetailsCC.setCardNumber(billingDetailsCreateDto.param1);
                            foundDetailsCC.setExpiryYear(billingDetailsCreateDto.param2);
                            foundDetailsCC.setExpiryMonth(billingDetailsCreateDto.param3);

                        } else {
                            throw new EntityNotFoundException("Billing Details not found. Проверьте id");
                        }
                    }
                }
            });
        };

    }

    private void addressCreateOrUpdate(OwnerRequestDto ownerDto, Owner oldOwner){
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


    public BillingDetails createBillingDetailsForOwner(BillingDetailsCreateDto billingDetailsCreateDto) {
        // получаем billingType
        BillingType billingType = billingDetailsCreateDto.getBillingType();

        return switch (billingType){
            case CREDIT_CARD ->
                    CreditCard.builder()
                            .billingType(billingType)
                            .cardNumber(billingDetailsCreateDto.getParam1())
                            .expiryYear(billingDetailsCreateDto.getParam2())
                            .expiryMonth(billingDetailsCreateDto.getParam3())
                            //.owner(ownerById)
                            .build();

            case BANK_ACCOUNT -> BankAccount.builder()
                    .billingType(billingType)
                    .accountNumber(billingDetailsCreateDto.param1)
                    .bankName(billingDetailsCreateDto.param2)
                    .swiftCode(billingDetailsCreateDto.param3)
                    //.owner(ownerById)
                    .build();

            default -> throw new IllegalArgumentException("Unknown billing type: " + billingType);
        };
    }

}
