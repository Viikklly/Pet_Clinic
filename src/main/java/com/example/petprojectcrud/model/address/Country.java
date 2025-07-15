package com.example.petprojectcrud.model.address;


import jakarta.persistence.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@RequiredArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "country")
public class Country {

    public Country(String countryName) {
        this.countryName = countryName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    private Integer id;


    @Column(name = "country_name")
    private String countryName;


    @OneToOne(mappedBy = "country")
    private Address address;

    @Column(name = "update_time")
    @UpdateTimestamp
    private Date updateTime;

    @Column(name = "create_time")
    @CreationTimestamp
    private Date createTime;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

}
