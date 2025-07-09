package com.example.petprojectcrud.model.address;


import com.example.petprojectcrud.DTO.address.AddressDto;
import com.example.petprojectcrud.DTO.address.CityDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Integer city_id;

    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private Address address_id;

    @Column(name = "city_name")
    private String city_name;


    @Column(name = "update_time")
    @UpdateTimestamp
    private Date updateTime;

    @Column(name = "create_time")
    @CreationTimestamp
    private Date createTime;

    @Column(name = "is_active", nullable = false)
    @ColumnDefault("true")
    private Boolean isActive;



    public CityDto toDto() {
        return CityDto.builder()
                .city_id(city_id)
                .city_name(city_name)
                .build();
    }
}
