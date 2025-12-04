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
@Schema(description = "DTO для ответа от банка")
public class BankPaymentResponseDto {

    @Schema(description = "Статус платежа", example = "SUCCESS")
    private String status;

    @Schema(description = "ID транзакции", example = "TRX123456")
    private String transactionId;

    @Schema(description = "Сообщение", example = "Платеж успешно выполнен")
    private String message;
}
