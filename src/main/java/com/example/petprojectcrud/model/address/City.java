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
@Table(name = "city")
public class City {

    public City(String cityName) {
        this.cityName = cityName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Integer id;


    @Column(name = "city_name")
    private String cityName;


    @OneToOne(mappedBy = "city")
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
