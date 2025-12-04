package com.example.petprojectcrud.DTO.bankPayment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO для создания банковского платежа")
public class BankPaymentRequestDto {

    @Schema(description = "id визита", example = "1")
    private Integer visitId;

    @Schema(description = "N платежных реквизитов", example = "5111-2222-3333-4444")
    private String paymentNumberUser;

}
