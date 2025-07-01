package com.example.petprojectcrud.DTO.clients;

import com.example.petprojectcrud.enums.AnimalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder // строит без оператора new
public class PetDto {


    private Integer id;


    private OwnerDto owner;


    private String name;



    //private String animalType;
    private AnimalType animalType;



    private String breed;

    private Integer age;


    private String vaccinations;


}
