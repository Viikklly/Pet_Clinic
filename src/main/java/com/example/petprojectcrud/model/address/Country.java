package com.example.petprojectcrud.model.address;

import com.example.petprojectcrud.DTO.address.CityDto;
import com.example.petprojectcrud.DTO.address.CountryDto;
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
@Table(name = "country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    private Integer country_id;

    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private Address address_id;

    @Column(name = "country_name")
    private String country_name;




    @Column(name = "update_time")
    @UpdateTimestamp
    private Date updateTime;

    @Column(name = "create_time")
    @CreationTimestamp
    private Date createTime;

    @Column(name = "is_active", nullable = false)
    @ColumnDefault("true")
    private Boolean isActive;


    public CountryDto toDto() {
        return CountryDto.builder()
                .country_id(country_id)
                .country_name(country_name)
                .build();
    }
}
