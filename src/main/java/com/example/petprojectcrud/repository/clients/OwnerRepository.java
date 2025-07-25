package com.example.petprojectcrud.repository.clients;

import com.example.petprojectcrud.model.clients.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
* Здесь при помощи аннотации @Repository мы просим Spring
*  автоматически реализовать интерфейс репозитория для нашего
*  класса Owner.
*  OwnerRepository нужен для обеспечения доступа к данным в БД,
*  связанным с сущностью Owner в нашем приложении:
* */

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {


    // находим хозяина по части имени
    List<Owner> findByNameContainsIgnoreCase(String name);

    // находим хозяина по имени питомца
    List<Owner> findOwnerByPetsName(String petName);

    // находим хозяина по имени EMAIL
    List<Owner> findByEmail(String email);
}
