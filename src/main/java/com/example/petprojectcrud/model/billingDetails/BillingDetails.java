package com.example.petprojectcrud.model.billingDetails;


import com.example.petprojectcrud.DTO.clients.PetDto;
import com.example.petprojectcrud.enums.BillingType;
import com.example.petprojectcrud.model.clients.Owner;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
//Поддерживает наследование в builder-паттерне.
//Работает с абстрактными классами
@SuperBuilder
@Entity
@Table(name = "billing_details")
// для отображения с одной таблицей для целой иерархии
// классов используем стратегию наследования SINGLE_TABLE
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// Нужен столбец например BD_TYPE для пометки,
// creditcard или bankAccount
@DiscriminatorColumn(name = "bd_type")
@AllArgsConstructor
@NoArgsConstructor
public abstract class BillingDetails {

    @Id
    // для SINGLE_TABLE другая стратегия генерации ключа
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "owner_id", nullable = false)
    private Owner owner;

    @Column(name = "billing_type")
    private BillingType billingType;
}
