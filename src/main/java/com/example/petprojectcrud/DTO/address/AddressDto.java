package com.example.petprojectcrud.DTO.address;


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
public class AddressDto {
    private Integer id;
    private String country;
    private String city;
    private String street;
    private Integer houseNumber;
    private Integer apartmentNumber;

}
