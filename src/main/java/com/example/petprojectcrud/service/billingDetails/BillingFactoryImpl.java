/*
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

// Помечает класс как утилитный.
// Это класс, который предоставляет статические
// методы для выполнения общих задач, не имеет состояния и не может быть создан
//@UtilityClass
@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class BillingFactoryImpl implements BillingFactory {



    @Autowired
    OwnerRepository ownerRepository;


    public BillingDetails createBillingDetailsEntity(BillingDetailsCreateDto billingDetailsCreateDto) {
        BillingType billingType = billingDetailsCreateDto.getBillingType();

        Owner owner = ownerRepository.findById(billingDetailsCreateDto.getIdOwner())
                .orElseThrow(() -> new EntityNotFoundException(
                        "User не найден по id: " + billingDetailsCreateDto.getIdOwner()
                ));

        return switch (billingType) {
            case CREDIT_CARD ->
                    CreditCard.builder()
                            .owner(owner)  // ДОБАВЛЕНО
                            .billingType(billingType)  // ДОБАВЛЕНО
                            .cardNumber(billingDetailsCreateDto.getParam1())
                            .expiryYear(billingDetailsCreateDto.getParam2())
                            .expiryMonth(billingDetailsCreateDto.getParam3())
                            //.cardBalance(BigDecimal.ZERO)  // Добавьте значение по умолчанию
                            //.isActiveCard(true)
                            .build();

            case BANK_ACCOUNT ->
                    BankAccount.builder()
                            .owner(owner)  // ДОБАВЛЕНО
                            .billingType(billingType)  // ДОБАВЛЕНО
                            .accountNumber(billingDetailsCreateDto.getParam1())
                            .bankName(billingDetailsCreateDto.getParam2())
                            .swiftCode(billingDetailsCreateDto.getParam3())
                            //.walletBalance(BigDecimal.ZERO)  // Добавьте значение по умолчанию
                            //.isActiveAccount(true)
                            .build();

            default -> throw new IllegalArgumentException("Нет такого типа банковской операции: " + billingType);
        };
    }
}
*/
