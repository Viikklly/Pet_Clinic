package com.example.petprojectcrud.model.address;


import com.example.petprojectcrud.DTO.address.AddressDto;
import com.example.petprojectcrud.model.clients.Owner;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "address_owners")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "country_id", referencedColumnName = "country_id")
    private Country country;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "city_id", referencedColumnName = "city_id")
    private City city;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "street_id", referencedColumnName = "street_id")
    private Street street;

    @OneToOne(mappedBy = "address")
    //@JoinColumn(name = "owner_id")
    private Owner owner;

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
    private Boolean isActive = true;


    public AddressDto toDto() {
        return AddressDto.builder()
                .id(id)
                .country(country == null ? null : country.getCountryName())
                .city(city == null ? null : city.getCityName())
                .street(street == null ? null : street.getStreetName())
                .houseNumber(houseNumber)
                .apartmentNumber(apartmentNumber)
                .build();
    }
}
