/*
package com.example.petprojectcrud.model.billingDetails;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
//callSuper = true в аннотации @EqualsAndHashCode позволяет
// включить методы equals() и hashCode() суперкласса в сгенерированные методы.
@EqualsAndHashCode(callSuper = true)
//Аннотация @SuperBuilder в библиотеке Lombok — это инструмент для реализации шаблона
// Builder в классах, которые расширяют другие классы, с учётом полей суперкласса.
@SuperBuilder
@Entity
@DiscriminatorValue("CreditCard")
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard extends BillingDetails {

    @NotNull
    @Column(name = "card_number")
    protected String cardNumber;

    @NotNull
    @Column(name = "expiry_year")
    protected String expiryYear;

    @NotNull
    @Column(name = "expiry_month")
    protected String expiryMonth;

}
*/
