package com.example.petprojectcrud.DTO.billingDetails;

import com.example.petprojectcrud.enums.BillingType;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BankAccountResponseDto extends BillingDetailsResponseDto{
    private String accountNumber;
    private String bankName;
    private String swiftCode;

    {
        setType(BillingType.BANK_ACCOUNT);
    }
}
