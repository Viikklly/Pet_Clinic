package com.example.petprojectcrud.model.address;

import com.example.petprojectcrud.DTO.address.AddressDto;
import com.example.petprojectcrud.DTO.address.CityDto;
import com.example.petprojectcrud.DTO.address.CountryDto;
import com.example.petprojectcrud.DTO.address.StreetDto;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private int address_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "country_id",  referencedColumnName = "country_id")
    private Country country_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "city_id",  referencedColumnName = "city_id")
    private City city_id;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "street_id ",  referencedColumnName = "street_id ")
    private Street street_id ;

    @Column(name = "house_number")
    private Integer houseNumber;

    @Column(name = "apartment_number")
    private Integer apartmentNumber;



    @Column(name = "update_time")
    @UpdateTimestamp
    private Date updateTime;

    @Column(name = "create_time")
    @CreationTimestamp
    private Date createTime;

    @Column(name = "is_active", nullable = false)
    @ColumnDefault("true")
    private Boolean isActive;


    public AddressDto toDto() {
        CountryDto countryDto = country_id.toDto();
        CityDto cityDto = city_id.toDto();
        StreetDto streetDto = street_id.toDto();

        return AddressDto.builder()
                .address_id(address_id)
                .country_id(countryDto)
                .city_id(cityDto)
                .street_id(streetDto)
                .house_number(houseNumber)
                .apartment_number(apartmentNumber)
                .build();
    }



}
