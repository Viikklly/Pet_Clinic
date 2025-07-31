package com.example.petprojectcrud.service.visit;


import com.example.petprojectcrud.DTO.priem.VisitDtoResponse;
import com.example.petprojectcrud.DTO.priem.VisitDtoRequest;
import com.example.petprojectcrud.model.clients.Owner;
import com.example.petprojectcrud.model.clients.Pet;
import com.example.petprojectcrud.model.employee.Employee;
import com.example.petprojectcrud.model.employee.MedicalServices;
import com.example.petprojectcrud.model.visit.Visit;
import com.example.petprojectcrud.repository.clients.OwnerRepository;
import com.example.petprojectcrud.repository.clients.PetRepository;
import com.example.petprojectcrud.repository.employee.EmployeeRepository;
import com.example.petprojectcrud.repository.employee.MedicalServicesRepository;
import com.example.petprojectcrud.repository.visit.VisitRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class VisitServiceImpl implements VisitService {
    private VisitRepository visitRepository;
    private MedicalServicesRepository medicalServicesRepository;
    private PetRepository petRepository;
    private OwnerRepository ownerRepository;
    private EmployeeRepository employeeRepository;



    public List<VisitDtoResponse> getAllVisits(){

        List<Visit> visitDto = visitRepository.findAll();
        List<VisitDtoResponse> visitDtoResponse = new ArrayList<>();

        visitDto.stream().forEach(visit -> {

            List<Integer> idsServices = visit.getIdsServices();

            Set<MedicalServices> allByIdIn = medicalServicesRepository.findAllByIdIn(idsServices);

            VisitDtoResponse dtoResponse = VisitDtoResponse
                    .builder()
                    .id(visit.getId())
                    .description(visit.getDescription())
                    .pet(visit.getPet().toDto())
                    .owner(visit.getOwner().toDto())
                    .employee(visit.getEmployee().toDto())
                    .services(allByIdIn.stream().map(medicalServices -> medicalServices.toDto()).collect(Collectors.toSet()))
                    .totalPrice(visit.getTotalPrice())
                    .createTime(visit.getCreateTime())
                    .build();

            visitDtoResponse.add(dtoResponse);
        });


        return visitDtoResponse;
    }


    //создать Visit метод должен на входе получить список id услуг, которые будут оказаны
    //и, одновременно, оказывает сотрудник, ведущий прием.
    @Override
    public VisitDtoResponse createVisit(VisitDtoRequest visitDtoRequest) {
        Visit newVisit = new Visit();


        // Получаем имя и телефон Owner, так как Pet без Owner мало имеет смысла
        String ownerName = visitDtoRequest.getOwnerName();
        String ownerPhone = visitDtoRequest.getOwnerPhone();

        // Получаем Owner по имени и телефону
        Owner ownerFromVisitDtoReceived = findOwnerFromVisitDtoReceived(ownerName, ownerPhone);


        // получаем Pet из листа owner по кличке животного
        String petOwnerName = visitDtoRequest.getPetOwnerName();
        Pet petFromOwnerVisitDtoReceived = findPetFromOwnerVisitDtoReceived(petOwnerName, ownerFromVisitDtoReceived);



        // Получаем сотрудника, который принимает питомца
        String employeeName = visitDtoRequest.getEmployeeName();
        Employee employeeFromVisitDtoReceived = findEmployeeFromVisitDtoReceived(employeeName);


        // Получаем список оказанных услуг именно Servises
        List<Integer> idsServicesList = visitDtoRequest.getIdsServices();
        Set<MedicalServices> medicalServicesFromEmployee = findServicesFromEmployeeFromVisitDtoReceived(idsServicesList);


        // Получаем список цен за оказанные услуги
        List<BigDecimal> servicePricesSet = getServicePrices(medicalServicesFromEmployee);

        //Общая цена за услуги, c проверкой на null
        BigDecimal totalPrice = servicePricesSet.stream()
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);



        newVisit.setDescription(visitDtoRequest.getDescription());
        newVisit.setPet(petFromOwnerVisitDtoReceived);
        newVisit.setOwner(ownerFromVisitDtoReceived);
        newVisit.setEmployee(employeeFromVisitDtoReceived);
        newVisit.setIdsServices(idsServicesList);
        newVisit.setTotalPrice(totalPrice);

        visitRepository.save(newVisit);

        return newVisit.toDto();
    }









    private Owner findOwnerFromVisitDtoReceived(String ownerName, String ownerPhone) {
        // Проверка на null для обоих параметров
        if (ownerName == null && ownerPhone == null) {
            throw new EntityNotFoundException("Не указано ни имя владельца, ни телефон");
        }

        // по имени и телефону
        if (ownerName != null && ownerPhone != null) {
            return ownerRepository
                    .findFirstByNameContainingIgnoreCaseAndPhoneContainingIgnoreCase(
                            ownerName.trim(),
                            ownerPhone.trim())
                    .orElseThrow(() -> new EntityNotFoundException(
                            String.format("Владелец с именем '%s' и телефоном '%s' не найден",
                                    ownerName, ownerPhone)));
        }

        // только по имени (если телефон не указан)
        if (ownerName != null) {
            return ownerRepository
                    .findFirstByNameContainingIgnoreCase(ownerName.trim())
                    .orElseThrow(() -> new EntityNotFoundException(
                            String.format("Владелец с именем '%s' не найден", ownerName)));
        }

        // только по телефону (если имя не указано)
        return ownerRepository
                .findFirstByPhoneContainingIgnoreCase(ownerPhone.trim())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Владелец с телефоном '%s' не найден", ownerPhone)));
    }


    // Поиск Pet в листе owner по кличке животного
    private Pet findPetFromOwnerVisitDtoReceived(String petOwnerName, Owner ownerFromVisitDtoReceived) {

        if (petOwnerName == null || petOwnerName.isBlank()) {
            throw new IllegalArgumentException("Имя питомца не может быть пустым");
        }

        return ownerFromVisitDtoReceived.getPets().stream()
                .filter(pet -> petOwnerName.equalsIgnoreCase(pet.getName().trim()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Питомец с именем '%s' не найден у владельца %s",
                                petOwnerName,
                                ownerFromVisitDtoReceived.getName())));
    }

    private Employee findEmployeeFromVisitDtoReceived(String employeeName) {
        if (employeeName == null || employeeName.isBlank()) {
            throw new IllegalArgumentException("Имя сотрудника не может быть пустым");
        }

        return employeeRepository
                .findFirstByNameContainsIgnoreCase(employeeName.trim())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Сотрудник с именем '%s' не найден", employeeName)
                ));
    }


    private Set<MedicalServices> findServicesFromEmployeeFromVisitDtoReceived(List<Integer> idsServices) {
        if (idsServices == null || idsServices.isEmpty()) {
            return Collections.emptySet();
        }

        // Удаляем дубликаты и null значения
        Set<Integer> uniqueIds = idsServices.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (uniqueIds.isEmpty()) {
            return Collections.emptySet();
        }

        // Получаем все услуги одним запросом
        List<MedicalServices> foundServices = medicalServicesRepository.findAllById(uniqueIds);

        // Проверяем, все ли услуги найдены
        if (foundServices.size() != uniqueIds.size()) {
            Set<Integer> foundIds = foundServices.stream()
                    .map(MedicalServices::getId)
                    .collect(Collectors.toSet());

            Set<Integer> missingIds = uniqueIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .collect(Collectors.toSet());

            throw new EntityNotFoundException(
                    "Не найдены услуги со следующими ID: " + missingIds);
        }
        return new HashSet<>(foundServices);
    }




    private List<BigDecimal> getServicePrices(Set<MedicalServices> services) {
        if (services == null) {
            return Collections.emptyList();
        }

        return services.stream()
                .filter(Objects::nonNull)
                .map(MedicalServices::getPrice)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}

