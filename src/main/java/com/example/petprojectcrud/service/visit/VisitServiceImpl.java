package com.example.petprojectcrud.service.visit;


import com.example.petprojectcrud.DTO.visit.VisitDto;
import com.example.petprojectcrud.DTO.visit.VisitDtoRequest;
import com.example.petprojectcrud.apiClients.BankClient;
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
    private BankClient bankClient;


    public List<VisitDto> getAllVisits(){

        // String myBank = bankClient.getMyBank();
        List<Visit> visitsList = visitRepository.findAll();
        List<VisitDto> visitDtoResponse = new ArrayList<>();

        visitsList.stream().forEach(visit -> {

            List<Integer> idsServices = visit.getIdsServices();

            Set<MedicalServices> allByIdIn = medicalServicesRepository.findAllByIdIn(idsServices);

            // Формируем Map<String, BigDecimal> servicesAndPrices;
            Map<String, BigDecimal> servicesAndPrices = new HashMap<>();
            allByIdIn.stream().forEach(medicalService -> {
                String name = medicalService.getDescription();
                BigDecimal price = medicalService.getPrice();
                servicesAndPrices.put(name, price);
            });



            VisitDto visitDto = VisitDto
                    .builder()
                    .id(visit.getId())
                    .description(visit.getDescription())
                    .ownerName(visit.getOwner().getName())
                    .petName(visit.getPet().getName())
                    .employeeName(visit.getEmployee().getName())
                    .servicesAndPrices(servicesAndPrices)
                    .totalPrice(visit.getTotalPrice())
                    .createTime(visit.getCreateTime())
                    .build();

            visitDtoResponse.add(visitDto);
        });

        return visitDtoResponse;
    }

    @Override
    public VisitDto getVisitById(int id) {
        Optional<Visit> byId = visitRepository.findById(id);

        Visit visit = null;
        if (byId.isPresent()) {
            visit = byId.get();
        } else {
            throw new EntityNotFoundException("Visit with id " + id + " not found");
        }
        return visit.toVisitDto();
    }


    @Override
    public VisitDto createVisit(VisitDtoRequest visitDtoRequest) {
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


        // Формируем Map<String, BigDecimal> servicesAndPrices;
        Map<String, BigDecimal> servicesAndPrices = new HashMap<>();
        medicalServicesFromEmployee.stream().forEach(medicalService -> {
            String name = medicalService.getMedicalServiceType().getServicesTypeEnum().getName();
            BigDecimal price = medicalService.getPrice();
            servicesAndPrices.put(name, price);
        });

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

        VisitDto visitDto = newVisit.toVisitDto();
        visitDto.setServicesAndPrices(servicesAndPrices);

        return visitDto;
    }





    private void payAndFinishVisit(Integer visitId) {

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

