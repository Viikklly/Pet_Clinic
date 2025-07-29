package com.example.petprojectcrud.model.clients;


import com.example.petprojectcrud.DTO.clients.OwnerDto;
import com.example.petprojectcrud.DTO.clients.PetDto;
import com.example.petprojectcrud.model.address.Address;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<Pet> pets = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

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
                .id(id)
                .name(name)
                .email(email)
                .phone(phone)
                .address(address == null ? null : address.toDto())
                .pets(petsDto)
                .build();
    }
}
