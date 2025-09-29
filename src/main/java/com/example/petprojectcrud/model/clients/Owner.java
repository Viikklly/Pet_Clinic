package com.example.petprojectcrud.model.clients;


import com.example.petprojectcrud.DTO.billingDetails.BillingDetailsFactoryDto;
import com.example.petprojectcrud.DTO.billingDetails.BillingDetailsResponseDto;
import com.example.petprojectcrud.DTO.clients.OwnerDto;
import com.example.petprojectcrud.DTO.clients.OwnerResponseDto;
import com.example.petprojectcrud.DTO.clients.PetDto;
import com.example.petprojectcrud.model.address.Address;
import com.example.petprojectcrud.model.billingDetails.BillingDetails;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.*;
import java.util.stream.Collectors;


@Setter
@Getter
@Entity
@Table(name = "owners")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "owner",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    List<Pet> pets = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;


    @OneToMany(mappedBy = "owner",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Set<BillingDetails> billingDetails = new HashSet<>();

    @Column(name = "update_time")
    @UpdateTimestamp
    private Date updateTime;

    @Column(name = "create_time")
    @CreationTimestamp
    private Date createTime;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;



    //
    public OwnerDto toDto() {
        List<PetDto> petsDto = new ArrayList<>();
        petsDto = pets.stream().map(Pet::toDto).toList();

        return OwnerDto.builder()
                .id(Long.valueOf(id))  // ПОСМОТРЕТЬ ПОЧЕМУ ЛОНГ
                .name(name)
                .email(email)
                .phone(phone)
                .address(address == null ? null : address.toDto())
                .pets(petsDto)
                .build();
    }


    public OwnerResponseDto toResponceDto() {
        List<PetDto> petsDto = new ArrayList<>();
        petsDto = pets.stream().map(pet -> pet.toDto()).toList();

        List<BillingDetailsResponseDto> billingDetailsDto = new ArrayList<>();
        billingDetailsDto = billingDetails.stream().map(billingDetails1 -> new BillingDetailsFactoryDto()
                .createBillingDetailsDto(billingDetails1))
                .toList();


        return OwnerResponseDto.builder()
                .id(Long.valueOf(id))
                .name(name)
                .email(email)
                .phone(phone)
                .address(address == null ? null : address.toDto())
                .pets(petsDto)
                .billingDetails(billingDetailsDto)
                .build();
    }
}
