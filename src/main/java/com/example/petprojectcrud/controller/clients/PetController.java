package com.example.petprojectcrud.controller.clients;

import com.example.petprojectcrud.DTO.clients.PetDto;
import com.example.petprojectcrud.enums.AnimalType;
import com.example.petprojectcrud.service.clients.PetService;
import com.example.petprojectcrud.service.clients.PetServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
* @RestController говорит Spring, что этот класс является
* RESTful-контроллером — будет обрабатывать HTTP-запросы
*  и возвращать данные в формате JSON или других форматах,
*  если это требуется.
* */

/*
* @RequestMapping() указывает, что все методы этого контроллера
* будут обрабатывать запросы, начинающиеся с пути /owners или /pets.
*  Например, GET /owners будет обрабатываться методом getAllOwners.
* */

@AllArgsConstructor
@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    @GetMapping
    public List<PetDto> getAllPets() {
        return petService.getAllPets();
    }

    /*
    * Аннотация @PathVariable используется для связывания значений из URL
    *  с параметрами метода. В приведенном выше примере, число 123 может
    *  быть извлечено с помощью @PathVariable.
    * ----
    * С другой стороны, @RequestParam используется для чтения значений
    *  из параметров запроса. Это обычно используется, когда параметры
    *   передаются в URL после символа ?, например, http://website.com/products?id=123.
    * */
    @GetMapping("/{id}")
    public PetDto getPetById(@PathVariable Integer id) {
        return petService.getPetById(id);
    }


    @PostMapping
    public PetDto createPet(@RequestBody PetDto pet) {
        return petService.createPet(pet);
    }

    @PutMapping("/{id}")
    public PetDto updatePet(@PathVariable Integer id, @RequestBody PetDto pet) {
        return petService.updatePet(id, pet);
    }

    @DeleteMapping("/{id}")
    public void deletePet(@PathVariable Integer id) {
        petService.deletePet(id);
    }

    @GetMapping("/petsByName/")
    public List<PetDto> getPetsByName(@RequestParam(value = "name") String name) {
        return petService.getPetsByName(name);
    }

    // найти всех питомцев по всем именам хозяев
    @GetMapping("/petsByNameOwner/")
    public List<PetDto> getPetsByNameOwner(@RequestParam(value = "name") String name) {
        return petService.getPetsByOwnerName(name);
    }

    //поиск питомца по номеру телефона хозяина, кличке животного и Animaltype
    @GetMapping("/getPetsByNumberOwnerAndNamePetsAndAnimalTypePets/")
    public PetDto getPetsByNumberOwnerAndNamePetsAndAnimalTypePets(@RequestParam(value = "phone") String phone,
                                                                         @RequestParam(value = "petsName") String petsName,
                                                                         @RequestParam(value = "animalType") AnimalType animalType) {
        return petService.findFirstByOwner_PhoneAndNameAndAnimalType(phone, petsName, animalType);
    }
}
