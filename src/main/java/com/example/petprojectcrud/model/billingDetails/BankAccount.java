/*
package com.example.petprojectcrud.model.billingDetails;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
//callSuper = true в аннотации @EqualsAndHashCode позволяет
// включить методы equals() и hashCode() суперкласса в сгенерированные методы.
@EqualsAndHashCode(callSuper = true)
//Аннотация @SuperBuilder в библиотеке Lombok — это инструмент для реализации шаблона
// Builder в классах, которые расширяют другие классы, с учётом полей суперкласса.
@SuperBuilder
@Entity
@DiscriminatorValue("BankAccount")
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount extends BillingDetails {

    @NotNull
    @Column(name = "account_number")
    protected String accountNumber;

    @NotNull
    @Column(name = "bank_name")
    protected String bankName;

    @NotNull
    @Column(name = "swift_code")
    protected String swiftCode;
}
*/
