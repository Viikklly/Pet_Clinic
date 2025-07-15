package com.example.petprojectcrud.DTO.clients;

import com.example.petprojectcrud.enums.AnimalType;
import com.example.petprojectcrud.model.clients.Pet;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder // строит без оператора new
@Schema
public class PetDto {

    private Integer id;

    private Integer ownerId;

    private String name;

    private AnimalType animalType;

    private String breed;

    private Integer age;

    private String vaccinations;

}
