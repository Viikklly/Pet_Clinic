package com.example.petprojectcrud.DTO.bankPayment;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO для отправки платежа в банковское приложение")
public class SendPaymentToBankDto {

    @Schema(
            description = "Платежные реквизиты счета от куда",
            example = "CC_5111-2222-3333-4444"
    )
    private String fromAccountPaymentNumberUser;


    @Schema(
            description = "Платежные реквизиты счета клиники",
            example = "BA_40817810100001234567"
    )
    private String toAccountPaymentNumberUser;


    @Schema(
            description = "Сумма операции. Должна быть положительным числом.",
            example = "1500.75",
            minimum = "0.01"
    )
    private BigDecimal amount;

    @Schema(
            description = "Примечание к операции",
            example = "Оплата ветеринарных услуг ООО Клиника ЛАПКИ ЦАРАПКИ "
    )
    private String description;
}


