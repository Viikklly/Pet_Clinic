package com.example.petprojectcrud.service.billingDetails;


import com.example.petprojectcrud.DTO.billingDetails.BillingDetailsCreateDto;
import com.example.petprojectcrud.enums.BillingType;
import com.example.petprojectcrud.model.billingDetails.BankAccount;
import com.example.petprojectcrud.model.billingDetails.BillingDetails;
import com.example.petprojectcrud.model.billingDetails.CreditCard;
import com.example.petprojectcrud.model.clients.Owner;
import com.example.petprojectcrud.repository.clients.OwnerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

// Помечает класс как утилитный.
// Это класс, который предоставляет статические
// методы для выполнения общих задач, не имеет состояния и не может быть создан
//@UtilityClass
@Service
@AllArgsConstructor
@NoArgsConstructor
public class BillingFactoryImpl implements BillingFactory {


    OwnerRepository ownerRepository;


    public BillingDetails createBillingDetailsEntity(BillingDetailsCreateDto billingDetailsCreateDto) {

        // получаем billingType
        BillingType billingType = billingDetailsCreateDto.getBillingType();

        // получаем Owner по id
        Owner ownerById = ownerRepository.findById(billingDetailsCreateDto.getIdOwner())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Owner not found with id: " + billingDetailsCreateDto.getIdOwner()
                ));


        return switch (billingType){
            case CREDIT_CARD ->
                CreditCard.builder()
                        .cardNumber(billingDetailsCreateDto.getParam1())
                        .expiryYear(billingDetailsCreateDto.getParam2())
                        .expiryMonth(billingDetailsCreateDto.getParam3())
                        //.owner(ownerById)
                        .build();

            case BANK_ACCOUNT -> BankAccount.builder()
                    .accountNumber(billingDetailsCreateDto.param1)
                    .bankName(billingDetailsCreateDto.param2)
                    .swiftCode(billingDetailsCreateDto.param3)
                    //.owner(ownerById)
                    .build();

            default -> throw new IllegalArgumentException("Unknown billing type: " + billingType);
        };
    }
}
