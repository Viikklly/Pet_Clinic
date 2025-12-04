package com.example.petprojectcrud.model.employee;


import com.example.petprojectcrud.DTO.employee.EmployeeDto;
import com.example.petprojectcrud.DTO.employee.MedicalServicesDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Entity
@Table(name = "employee")
@Getter
@Builder

public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "specialization")
    private String specialization;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "employee_services",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    private Set<MedicalServices> services = new HashSet<>();

    @Column(name = "update_time")
    @UpdateTimestamp
    private Date updateTime;

    @Column(name = "create_time")
    @CreationTimestamp
    private Date createTime;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;



    public EmployeeDto toDto(/*Employee employee*/) {
       /*
        //Подумать
        if (employee == null) {
            return null;
        }
        */

        /*

        List<ServicesDto> servicesDto = employee.getServices() == null ?
                Collections.emptyList() :
                employee.getServices().stream()
                        .map(Services::toDto)
                        .toList();

         */

        Set<MedicalServicesDto> medicalServicesDtos = services.stream()
                .map(serv -> MedicalServicesDto.builder()
                        .id(serv.getId())
                        .serviceType(serv.getMedicalServiceType().toDto())
                        .price(serv.getPrice())
                        .description(serv.getDescription())
                        .build())
                .collect(Collectors.toSet());

        return EmployeeDto.builder()
                .id(id)
                .name(name)
                .specialization(specialization)
                .services(medicalServicesDtos)
                .build();
    }
}
