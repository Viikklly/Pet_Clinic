package com.example.petprojectcrud.DTO.clients;

import com.example.petprojectcrud.DTO.employee.EmployeeDto;
import com.example.petprojectcrud.enums.AnimalType;
import com.example.petprojectcrud.model.clients.Owner;
import com.example.petprojectcrud.model.clients.Pet;
import com.example.petprojectcrud.model.employee.Employee;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

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
