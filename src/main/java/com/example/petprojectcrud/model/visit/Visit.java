package com.example.petprojectcrud.model.visit;


import com.example.petprojectcrud.DTO.priem.VisitDto;
import com.example.petprojectcrud.model.clients.Owner;
import com.example.petprojectcrud.model.clients.Pet;
import com.example.petprojectcrud.model.employee.Employee;
import com.example.petprojectcrud.model.employee.Services;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "visit")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "description")
    private String description;

    @JoinColumn(name = "pet_id", referencedColumnName = "pet_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Pet pet;


    @JoinColumn(name = "owner_id", referencedColumnName = "owner_id")
    @OneToOne
    private Owner owner;

    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @OneToOne
    private Employee employee;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Set<Services> services;

    @Column(name = "total_price", precision = 10, scale = 2)
    private BigDecimal totalPrice;


    public VisitDto toDto() {

        return VisitDto
                .builder()
                .id(id)
                .description(description)
                .pet(pet)
                .owner(owner)
                .totalPrice(totalPrice)
                .build();
    }

}
