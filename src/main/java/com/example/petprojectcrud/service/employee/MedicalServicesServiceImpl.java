package com.example.petprojectcrud.service.employee;


import com.example.petprojectcrud.DTO.employee.EmployeeDto;
import com.example.petprojectcrud.DTO.employee.MedicalServiceTypeDto;
import com.example.petprojectcrud.DTO.employee.MedicalServicesDto;
import com.example.petprojectcrud.enums.ServicesTypeEnum;
import com.example.petprojectcrud.model.employee.Employee;
import com.example.petprojectcrud.model.employee.MedicalServices;
import com.example.petprojectcrud.model.employee.MedicalServiceType;
import com.example.petprojectcrud.repository.employee.EmployeeRepository;
import com.example.petprojectcrud.repository.employee.MedicalServiceTypeRepository;
import com.example.petprojectcrud.repository.employee.MedicalServicesRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@AllArgsConstructor
@Service
@Transactional
public class MedicalServicesServiceImpl implements MedicalServicesService {
    private MedicalServicesRepository medicalServicesRepository;
    private EmployeeRepository employeeRepository;
    private MedicalServiceTypeRepository medicalServiceTypeRepository;


    @Override
    public List<MedicalServicesDto> getAllServices() {
        List<MedicalServicesDto> medicalServicesDtoList = medicalServicesRepository.findAll().stream().map(services -> services.toDto()).toList();
        return medicalServicesDtoList;
    }


    @Override
    public MedicalServicesDto createService(MedicalServicesDto medicalServicesDto) {
        // Создаем Service
        MedicalServices medicalServices = MedicalServices
                .builder()
                .medicalServiceType(getServiceTypeWithEmployeeDto(medicalServicesDto.getServiceType()))
                .price(medicalServicesDto.getPrice())
                .description(medicalServicesDto.getDescription())
                .isActive(true)
                .build();
        // Сохраняем Service без Set <Employee> что бы получить id Services
        medicalServicesRepository.save(medicalServices);

        // получаем Set<EmployeeDto>
        Set<Employee> employeeSet = getEmployeeList(medicalServicesDto, medicalServices);

        // Добавляем employeeList в Services
        medicalServices.setEmployees(employeeSet);

        return medicalServices.toDto();
    }


    @Override
    public MedicalServicesDto updateService(Integer id, MedicalServicesDto medicalServicesDto) {

        //Получить Service по id
        Optional<MedicalServices> servicesRepositoryById = medicalServicesRepository.findById(id);

        // Если такой Service есть
        if (servicesRepositoryById.isPresent()) {
            MedicalServices service = servicesRepositoryById.get();

            if (medicalServicesDto.getServiceType() != null) {
                // используем метод получения ServiceTypeWithEmployeeDto
                service.setMedicalServiceType(getServiceTypeWithEmployeeDto(medicalServicesDto.getServiceType()));
            }
            if (medicalServicesDto.getPrice() != null) {
                service.setPrice(medicalServicesDto.getPrice());
            }
            if (medicalServicesDto.getDescription() != null) {
                service.setDescription(medicalServicesDto.getDescription());
            }

            // Лист Employee из сущности
            Set<Employee> employeesList = servicesRepositoryById.get().getEmployees();

            // лист Employee из ДТО
            Set<EmployeeDto> servicesDtoEmployeesList = medicalServicesDto.getEmployees();

            // Временный лист
            Set<Employee> temporaryList = new HashSet<>();

            // Сверяем лист employeeList по id с листом из ДТО
            employeesList.forEach(employee -> {
                Integer employeeId = employee.getId();

                servicesDtoEmployeesList.forEach(servicesDtoEmployeeFromList -> {
                    Integer idEmployeeFromDto = servicesDtoEmployeeFromList.getId();

                    // Если не одинаковые id, получаем Employee по id и добавляем
                    // во временный лист
                    if (employeeId != idEmployeeFromDto) {

                        Optional<Employee> employeeRepositoryById = employeeRepository.findById(idEmployeeFromDto);

                        // Если такой Employee существует добавляем во временный лист
                        if (employeeRepositoryById.isPresent()) {
                            temporaryList.add(employeeRepositoryById.get());
                        } else {
                            throw new EntityNotFoundException("Employee with id " + employeeId + " not found");
                        }
                    }
                });

            });

            employeesList.addAll(temporaryList);
            medicalServicesRepository.save(service);

            return service.toDto();
        } else {
            throw new EntityNotFoundException("Service not found. Такого сервиса не существует!");
        }

    }


    // Подумать(Потренироваться) как разорвать связи employee_services
    // Переделать на isActive
    @Override
    @Transactional
    public void deleteService(Integer id) {
        MedicalServices medicalServices = medicalServicesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Service not found"));


        // удаляем связи
        for (Employee employee : new HashSet<>(medicalServices.getEmployees())){
            employee.getServices().remove(medicalServices);
            employeeRepository.save(employee);
        }

        medicalServicesRepository.deleteById(id);
    }


    // Получаем ServiceType
    public MedicalServiceType getServiceTypeWithEmployeeDto(MedicalServiceTypeDto medicalServiceTypeDto) {
        ServicesTypeEnum servicesTypeEnum = medicalServiceTypeDto.getServicesTypeEnum();

        Optional<MedicalServiceType> byServicesTypeEnum = medicalServiceTypeRepository.findByServicesTypeEnum(servicesTypeEnum);


        if (byServicesTypeEnum.isPresent()) {
            return byServicesTypeEnum.get();
        } else {
            throw new EntityNotFoundException("ServiceType not found. Такого ServiceType не существует!");
        }
    }

    // Получаем Set Employee
    public Set<Employee> getEmployeeList(MedicalServicesDto medicalServicesDto, MedicalServices medicalServices) {

        // Получаем Set Employees из serviceDto
        Set<EmployeeDto> employeeDtoList = medicalServicesDto.getEmployees();

        Set<Employee> employeesListFromDto = new HashSet<>();

        // Если список не пустой, надо получить Employee по имени и добавить к списку Services
        if (!employeeDtoList.isEmpty()) {
            for (EmployeeDto employeeDto : employeeDtoList) {// из списка Employee достаем имя
                String employeeDtoName = employeeDto.getName();

                // Ищем в бд Employee с таким именем
                Optional<Employee> employeeByName = employeeRepository.findFirstByNameContainsIgnoreCase(employeeDtoName);


                employeeByName.ifPresentOrElse(
                        employee -> {
                    // добавляем Service в Employee
                    employee.getServices().add(medicalServices);

                    // Пополняем Set состоящий из Employee
                    employeesListFromDto.add(employee);

                    //Сохраняем Employee в репозитории
                    employeeRepository.save(employee);
                }, () -> {
                            throw new EntityNotFoundException("Employee not found. Сотрудника с таким именем не существует!");
                        });

            }
        } else {
            throw new EntityNotFoundException("The list is empty. Выберете сотрудника или создайте сотрудника для этой услуги");
        }
        return employeesListFromDto;
    }


    public Employee createNewEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setName(employeeDto.getName());
        employee.setSpecialization(employeeDto.getSpecialization());
        employeeRepository.save(employee);
        return employee;
    }

}
