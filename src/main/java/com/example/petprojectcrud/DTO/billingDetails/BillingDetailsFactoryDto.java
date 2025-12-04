/*
package com.example.petprojectcrud.DTO.billingDetails;

import com.example.petprojectcrud.model.billingDetails.BankAccount;
import com.example.petprojectcrud.model.billingDetails.BillingDetails;
import com.example.petprojectcrud.model.billingDetails.CreditCard;
import lombok.AllArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Service;


*/
/*
Помечает класс как утилитный.
Утилитный класс — это класс, который предоставляет набор статических методов
 для выполнения общих задач, не имеет состояния и не позволяет создавать экземпляры
 *//*

//@UtilityClass
@Service
@AllArgsConstructor
public class BillingDetailsFactoryDto {

    public BillingDetailsResponseDto createBillingDetailsDto(BillingDetails billingDetails) {


        if (billingDetails instanceof BankAccount bankAccount) {
            return BankAccountResponseDto.builder()
                    .id(bankAccount.getId())
                    .ownerId(bankAccount.getOwner().getId())
                    .accountNumber(bankAccount.getAccountNumber())
                    .bankName(bankAccount.getBankName())
                    .swiftCode(bankAccount.getSwiftCode())
                    .build();

        } else if (billingDetails instanceof CreditCard creditCard) {
            return CreditCardResponseDto.builder()
                    .id(creditCard.getId())
                    .ownerId(creditCard.getOwner().getId())
                    .cardNumber(creditCard.getCardNumber())
                    .expiryYear(creditCard.getExpiryYear())
                    .expiryMonth(creditCard.getExpiryMonth())
                    .build();
        }
        throw new IllegalArgumentException("Unknown BillingDetails type: " + billingDetails.getClass().getName());
    }
}
*/
