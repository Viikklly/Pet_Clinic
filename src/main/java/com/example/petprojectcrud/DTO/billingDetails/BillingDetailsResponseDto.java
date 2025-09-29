package com.example.petprojectcrud.DTO.billingDetails;

import com.example.petprojectcrud.enums.BillingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class BillingDetailsResponseDto {
    private Long id;
    private Integer ownerId;
    private BillingType type;
    /// ???
    private String number;
}
