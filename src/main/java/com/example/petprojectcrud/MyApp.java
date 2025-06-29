package com.example.petprojectcrud;


import com.example.petprojectcrud.service.clients.OwnerService;
import com.example.petprojectcrud.service.clients.PetService;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@AllArgsConstructor
@SpringBootApplication
/*
* Она сообщает Spring, что это главный класс приложения,
* и Spring автоматически сканирует компоненты и настраивает
*  контекст.
* */
public class MyApp {

    private final OwnerService ownerService;

    private final PetService petService;

    public static void main(String[] args) {
        SpringApplication.run(MyApp.class, args);
    }

}
