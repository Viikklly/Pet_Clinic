package com.example.petprojectcrud.service.employee;


import com.example.petprojectcrud.DTO.employee.EmployeeDto;
import com.example.petprojectcrud.DTO.employee.ServiceTypeDto;
import com.example.petprojectcrud.DTO.employee.ServicesDto;
import com.example.petprojectcrud.enums.ServicesTypeEnum;
import com.example.petprojectcrud.model.employee.Employee;
import com.example.petprojectcrud.model.employee.ServiceType;
import com.example.petprojectcrud.model.employee.Services;
import com.example.petprojectcrud.repository.employee.EmployeeRepository;
import com.example.petprojectcrud.repository.employee.ServiceTypeRepository;
import com.example.petprojectcrud.repository.employee.ServicesRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@AllArgsConstructor
@Service
@Transactional
public class ServicesServiceImpl implements ServicesService {
    private ServicesRepository servicesRepository;
    private EmployeeRepository employeeRepository;
    private ServiceTypeRepository serviceTypeRepository;


    @Override
    public List<ServicesDto> getAllServices() {
        List<ServicesDto> servicesDtoList = servicesRepository.findAll().stream().map(services -> services.toDto()).toList();
        return servicesDtoList;
    }


    @Override
    public ServicesDto createService(ServicesDto servicesDto) {
        // Создаем Service
        Services services = Services
                .builder()
                .serviceType(getServiceTypeWithEmployeeDto(servicesDto.getServiceType()))
                .price(servicesDto.getPrice())
                .description(servicesDto.getDescription())
                .isActive(true)
                .build();
        // Сохраняем Service без Set <Employee> что бы получить id Services
        servicesRepository.save(services);

        // получаем Set<EmployeeDto>
        Set<Employee> employeeSet = getEmployeeList(servicesDto, services);

        // Добавляем employeeList в Services
        services.setEmployees(employeeSet);

        return services.toDto();
    }


    @Override
    public ServicesDto updateService(Integer id, ServicesDto servicesDto) {

        //Получить Service по id
        Optional<Services> servicesRepositoryById = servicesRepository.findById(id);

        // Если такой Service есть
        if (servicesRepositoryById.isPresent()) {
            Services service = servicesRepositoryById.get();

            if (servicesDto.getServiceType() != null) {
                // используем метод получения ServiceTypeWithEmployeeDto
                service.setServiceType(getServiceTypeWithEmployeeDto(servicesDto.getServiceType()));
            }
            if (servicesDto.getPrice() != null) {
                service.setPrice(servicesDto.getPrice());
            }
            if (servicesDto.getDescription() != null) {
                service.setDescription(servicesDto.getDescription());
            }

            // Лист Employee из сущности
            Set<Employee> employeesList = servicesRepositoryById.get().getEmployees();

            // лист Employee из ДТО
            Set<EmployeeDto> servicesDtoEmployeesList = servicesDto.getEmployees();

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
            servicesRepository.save(service);

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
        Services services = servicesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Service not found"));


        // удаляем связи
        for (Employee employee : new HashSet<>(services.getEmployees())){
            employee.getServices().remove(services);
            employeeRepository.save(employee);
        }

        servicesRepository.deleteById(id);
    }


    // Получаем ServiceType
    public ServiceType getServiceTypeWithEmployeeDto(ServiceTypeDto serviceTypeDto) {
        ServicesTypeEnum servicesTypeEnum = serviceTypeDto.getServicesTypeEnum();

        Optional<ServiceType> byServicesTypeEnum = serviceTypeRepository.findByServicesTypeEnum(servicesTypeEnum);


        if (byServicesTypeEnum.isPresent()) {
            return byServicesTypeEnum.get();
        } else {
            throw new EntityNotFoundException("ServiceType not found. Такого ServiceType не существует!");
        }
    }

    // Получаем Set Employee
    public Set<Employee> getEmployeeList(ServicesDto servicesDto, Services services) {

        // Получаем Set Employees из serviceDto
        Set<EmployeeDto> employeeDtoList = servicesDto.getEmployees();

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
                    employee.getServices().add(services);

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
