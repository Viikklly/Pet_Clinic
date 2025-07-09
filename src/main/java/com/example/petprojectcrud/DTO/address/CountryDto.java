package com.example.petprojectcrud.DTO.address;

import com.example.petprojectcrud.model.address.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder // строит без оператора new
public class CountryDto {

    private Integer country_id;


    private Address address_id;


    private String country_name;
}
