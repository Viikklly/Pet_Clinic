package com.example.petprojectcrud.model.clients;


import com.example.petprojectcrud.DTO.clients.PetDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Setter
@Entity
@Table(name = "pets")
@Getter
public class Pet {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "owner_id")
    private Owner owner;

    @Column(name = "name")
    private String name;


    @Column(name = "animal_type")
    private String animalType;


    @Column(name = "breed")
    private String breed;


    @Column(name = "age")
    private Integer age;

    @Column(name = "vaccinations")
    private String vaccinations;


    public PetDto toDto() {
        return PetDto.builder()
                .id(id)
                .name(name)
                .animalType(animalType)
                .breed(breed)
                .age(age)
                .vaccinations(vaccinations)
                .build();
    }

}
