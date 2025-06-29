package com.example.petprojectcrud.DTO.clients;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder // строит без оператора new
public class OwnerDto {

    private Integer id;


    private String name;


    private String email;


    private String phone;


    List<PetDto> pets;


}
