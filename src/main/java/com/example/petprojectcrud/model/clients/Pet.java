package com.example.petprojectcrud.model.clients;


import com.example.petprojectcrud.DTO.clients.PetDto;
import com.example.petprojectcrud.enums.AnimalType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Entity
@Table(name = "pets")
@Getter
@Builder
public class Pet {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "owner_id")
    private Owner owner;

    @Column(name = "name")
    private String name;


    @Column(name = "animal_type")
    @Enumerated(EnumType.STRING)
    private AnimalType animalType;

    @Column(name = "breed")
    private String breed;

    @Column(name = "age")
    private Integer age;

    @Column(name = "vaccinations")
    private String vaccinations;

    @Column(name = "update_time")
    @UpdateTimestamp
    private Date updateTime;

    @Column(name = "create_time")
    @CreationTimestamp
    private Date createTime;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;


    public PetDto toDto() {
        return PetDto.builder()
                .id(id)
                .name(name)
                .ownerId(owner.getId() == null ? null : owner.getId())
                .animalType(animalType)
                .breed(breed)
                .age(age)
                .vaccinations(vaccinations)
                .build();
    }

}
