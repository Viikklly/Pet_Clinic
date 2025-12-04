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
            description = "Имя обладателя счета от куда",
            example = "Воронцов Артем Александрович"
    )
    private String fromAccountNameUser;

    @Schema(
            description = "Телефон обладателя счета от куда",
            example = "+79033334455"
    )
    private String fromAccountPhoneUser;

    @Schema(
            description = "Платежные реквизиты счета от куда",
            example = "5111-2222-3333-4444"
    )
    private String fromAccountPaymentNumberUser;


    /// TODO перенести данные в конфиг
    @Schema(
            description = "Имя обладателя счета куда. Клиника",
            example = "ООО Клиника ЛАПКИ ЦАРАПКИ"
    )
    private String toAccountNameUser = "ООО Клиника ЛАПКИ ЦАРАПКИ";

    /// TODO перенести данные в конфиг
    @Schema(
            description = "Телефон клиники. куда",
            example = "+79161234567"
    )
    private String toAccountPhoneUser = "+79161234567";

    /// TODO перенести данные в конфиг
    @Schema(
            description = "Платежные реквизиты счета клиники",
            example = "40817810100001234567"
    )
    private String toAccountPaymentNumberUser = "40817810100001234567";


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


