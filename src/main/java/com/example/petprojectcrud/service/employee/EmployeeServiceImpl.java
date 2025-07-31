package com.example.petprojectcrud.service.employee;


import com.example.petprojectcrud.DTO.employee.EmployeeDto;
import com.example.petprojectcrud.DTO.employee.MedicalServiceTypeDto;
import com.example.petprojectcrud.DTO.employee.MedicalServicesDto;
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

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;
    private MedicalServicesRepository medicalServicesRepository;
    private MedicalServiceTypeRepository medicalServiceTypeRepository;


    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employeeRepository
                .findAll()
                .stream()
                .map(Employee::toDto)
                .toList();
    }



    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        Employee employee = Employee
                .builder()
                .name(employeeDto.getName())
                .specialization(employeeDto.getSpecialization())
                .build();

        // Сохраняем без листа Services
        employeeRepository.save(employee);

        Set<MedicalServicesDto> medicalServicesDtos = employeeDto.getServices();

        Set<MedicalServices> medicalServicesDtoFromEmployee = createServicesDtoFromEmployee(medicalServicesDtos, employee);

        // добавляем Сервисы в Employee
        employee.setServices(medicalServicesDtoFromEmployee);

        Employee save = employeeRepository.save(employee);

        return employee.toDto();
    }



    // Вообще не уверена. Проверить и подумать!

    @Override
    public EmployeeDto updateEmployee(Integer id, EmployeeDto employeeDto) {
        // Получаем Employee по id
        Optional<Employee> employee = employeeRepository.findById(id);

        // Если Employee существует
        if (employee.isPresent()) {

            Employee employeeToUpdate = employee.get();
            employeeToUpdate.setName(employeeDto.getName());
            employeeToUpdate.setSpecialization(employeeDto.getSpecialization());
            // сохраняем без листа
            employeeRepository.save(employeeToUpdate);

            // проверяем лист сервисов по описанию
            Set<MedicalServices> services = employeeToUpdate.getServices();

            Set<MedicalServicesDto> servicesWithDto = employeeDto.getServices();

            Set<MedicalServicesDto> temporaryList = new HashSet<>();

            if (services.isEmpty()) {
                services.stream().forEach(service -> {

                    String serviceDescription = service.getDescription();

                    servicesWithDto.stream().forEach(servicesDto -> {
                        String servicesDtoDescription = servicesDto.getDescription();

                        // Если описания не равны, добавляем во временный лист
                        if (!serviceDescription.equals(servicesDtoDescription)) {
                            temporaryList.add(servicesDto);
                        }
                    });
                });
            }

            // Формируем Service в Employee
            Set<MedicalServices> medicalServicesDtoFromEmployee = createServicesDtoFromEmployee(temporaryList, employeeToUpdate);

            // Добавляем сервисы из ДТО в список сервисов из Employee
            services.addAll(medicalServicesDtoFromEmployee);

            // Сохраняем Employee
            employeeRepository.save(employeeToUpdate);

            return employeeToUpdate.toDto();

        } else {
            throw new EntityNotFoundException("Employee with id " + id + " not found");
        }
    }


    // Подумать(Потренироваться) как разорвать связи employee_services
    // Переделать на isActive
    @Transactional
    @Override
    public void deleteEmployee(Integer id) {
        Optional<Employee> employee = employeeRepository.findById(id);

        if (employee.isPresent()) {

            Set<MedicalServices> services = employee.get().getServices();

            services.forEach(service -> {
                service.getEmployees().remove(service);
                medicalServicesRepository.save(service);
            });
        }
        employeeRepository.deleteById(id);

    }




    //Формируем Services из EmployeeDto
    private Set<MedicalServices> createServicesDtoFromEmployee(Set<MedicalServicesDto> medicalServicesDtosSet, Employee employee) {

        Set<MedicalServices> medicalServicesSet = new HashSet<>();

        // Если список не пустой, надо получить Services по description и добавить к списку Employee
        if (medicalServicesDtosSet.isEmpty()) {


            // достаем description(описание) Сервиса
            medicalServicesDtosSet.forEach(servicesDto -> {
                String description = servicesDto.getDescription();

                // Если описание не пустое
                if (description != null) {
                    // ищем сервис по описанию description
                    Optional<MedicalServices> servicesByDescription = medicalServicesRepository
                            .findByDescriptionContainsIgnoreCase(description);

                    if (servicesByDescription.isPresent()) {
                        //добавляем в список Сервисов наш сервис
                        medicalServicesSet.add(servicesByDescription.get());

                        // добавляем в сервис наш Employee
                        servicesByDescription.get().getEmployees().add(employee);

                        // Сохраняем обновленный Сервис
                        medicalServicesRepository.save(servicesByDescription.get());
                    } else {
                        throw new EntityNotFoundException("Services not found. Сервиса с таким описанием не существует!");
                    }
                } else {
                    throw new EntityNotFoundException("description null. Создайте сервис с таким описанием Description");
                }
            });


        }
        return medicalServicesSet;
    }


    private MedicalServiceType serviceTypeFromServiceDto(MedicalServiceTypeDto medicalServiceTypeDto) {
        MedicalServiceType medicalServiceType = MedicalServiceType
                .builder()
                .id(medicalServiceTypeDto.getId())
                .servicesTypeEnum(medicalServiceTypeDto.getServicesTypeEnum())
                .build();

        return medicalServiceType;
    }




}
