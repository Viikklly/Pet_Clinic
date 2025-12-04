/*
package com.example.petprojectcrud.service.billingDetails;


import com.example.petprojectcrud.DTO.billingDetails.BillingDetailsCreateDto;
import com.example.petprojectcrud.DTO.billingDetails.BillingDetailsFactoryDto;
import com.example.petprojectcrud.DTO.billingDetails.BillingDetailsResponseDto;
import com.example.petprojectcrud.enums.BillingType;
import com.example.petprojectcrud.model.billingDetails.BankAccount;
import com.example.petprojectcrud.model.billingDetails.BillingDetails;
import com.example.petprojectcrud.model.billingDetails.CreditCard;
import com.example.petprojectcrud.repository.billingDetails.BankAccountRepository;
import com.example.petprojectcrud.repository.billingDetails.BillingDetailsRepository;
import com.example.petprojectcrud.repository.billingDetails.CreditCardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;



@AllArgsConstructor
@Service
@Transactional
public class BillingDetailsServiceImpl implements BillingDetailsService{

    private BillingDetailsRepository billingDetailsRepository;
    private BankAccountRepository bankAccountRepository;
    private CreditCardRepository creditCardRepository;
    private BillingDetailsFactoryDto billingDetailsFactoryDto;
    private BillingFactoryImpl billingFactoryImpl;


    @Override
    public BillingDetailsResponseDto createBillingDetails(BillingDetailsCreateDto billingDetailsCreateDto) {
        // получаем Billing type
        BillingType billingType = billingDetailsCreateDto.getBillingType();

        if (billingType == BillingType.BANK_ACCOUNT) {
            BankAccount bankAccount = (BankAccount) billingFactoryImpl.createBillingDetailsEntity(billingDetailsCreateDto);
            BankAccount bankAccountEntity = bankAccountRepository.save(bankAccount);
            return billingDetailsFactoryDto.createBillingDetailsDto(bankAccountEntity);
        } else if (billingType == BillingType.CREDIT_CARD) {
            CreditCard creditCard = (CreditCard) billingFactoryImpl.createBillingDetailsEntity(billingDetailsCreateDto);
            CreditCard creditCardEntity = creditCardRepository.save(creditCard);
            return billingDetailsFactoryDto.createBillingDetailsDto(creditCardEntity);
        } else {
            throw new IllegalArgumentException("Invalid billing type");
        }
    }


    @Override
    public List<BillingDetailsResponseDto> getAllBillingDetails() {
        List<BillingDetails> billingDetailsList = billingDetailsRepository.findAll();

        return billingDetailsList.stream()
                .map(billingDetails -> {
                    try {
                        return billingDetailsFactoryDto.createBillingDetailsDto(billingDetails);
                    } catch (IllegalArgumentException e) {
                        //возвращаем null или ПОДУМАТЬ
                        throw new IllegalArgumentException("Invalid billing type with id " + billingDetails.getId());
                    }
                })
                .filter(Objects::nonNull) // Фильтруем null значения
                .collect(Collectors.toList());
    }
}
*/
