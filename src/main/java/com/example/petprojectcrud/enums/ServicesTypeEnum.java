
package com.example.petprojectcrud.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ServicesTypeEnum {
    OTHER_TYPE(0,"Другие услуги", "Прочие ветеринарные услуги"),
    VACCINATION(1, "Вакцинация", "Плановые и экстренные прививки"),
    CHIPPING(2, "Чипирование", "Имплантация микрочипов для идентификации"),
    DIAGNOSTICS(3, "Диагностика", "Комплексное обследование животных" );

    private final Integer id;
    private final String name;
    private final String description;
}


