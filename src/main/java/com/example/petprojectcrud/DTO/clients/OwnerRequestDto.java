package com.example.petprojectcrud.DTO.clients;

import com.example.petprojectcrud.DTO.billingDetails.BillingDetailsCreateDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OwnerRequestDto extends OwnerDto {
    private List<BillingDetailsCreateDto> billingDetails;
}
