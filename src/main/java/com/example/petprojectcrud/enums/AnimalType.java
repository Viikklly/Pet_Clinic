package com.example.petprojectcrud.enums;


import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;



@AllArgsConstructor
@Getter
public enum AnimalType {
    OTHER_TYPE(0, "Неизвестный тип животного"),
    DOG(1, "Собака"),
    CAT(2, "Кошка"),
    DRAGON(3, "Дракон"),
    MONKEY(4, "Обезьяна");

    private final Integer id;
    private final String description;

}
