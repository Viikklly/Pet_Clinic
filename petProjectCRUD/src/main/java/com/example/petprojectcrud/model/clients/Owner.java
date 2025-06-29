package com.example.petprojectcrud.model.clients;


import com.example.petprojectcrud.DTO.clients.OwnerDto;
import com.example.petprojectcrud.DTO.clients.PetDto;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import java.util.ArrayList;
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

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Pet> pets = new ArrayList<>();

    //
    public OwnerDto toDto() {
        List<PetDto> petsDto = new ArrayList<>();
        petsDto = pets.stream().map(Pet::toDto).toList();
        return OwnerDto.builder()
                .id(id)
                .name(name)
                .email(email)
                .phone(phone)
                .pets(petsDto)
                .build();
    }

}
