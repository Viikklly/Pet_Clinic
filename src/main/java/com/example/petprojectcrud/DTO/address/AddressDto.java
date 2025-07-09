package com.example.petprojectcrud.DTO.address;

import com.example.petprojectcrud.model.address.City;
import com.example.petprojectcrud.model.address.Country;
import com.example.petprojectcrud.model.address.Street;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder // строит без оператора new
public class AddressDto {

    private int address_id;

    private CountryDto country_id;

    private CityDto city_id;

    private StreetDto street_id ;

    private Integer house_number;

    private Integer apartment_number;

}
