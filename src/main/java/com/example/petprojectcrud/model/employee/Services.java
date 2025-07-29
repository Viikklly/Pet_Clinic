package com.example.petprojectcrud.model.employee;


import com.example.petprojectcrud.DTO.employee.EmployeeDto;
import com.example.petprojectcrud.DTO.employee.ServicesDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Entity
@Table(name = "services")
@Getter
@Builder
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "service_type_id", nullable = false)
    private ServiceType serviceType;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "services", fetch = FetchType.LAZY)
    Set<Employee> employees = new HashSet<>();


    @Column(name = "update_time")
    @UpdateTimestamp
    private Date updateTime;

    @Column(name = "create_time")
    @CreationTimestamp
    private Date createTime;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;



    public ServicesDto toDto(/*Services services*/) {
        /*
        // Подумать
        if (services == null) {
            return null;
        }

         */
        /*
        List<EmployeeDto> employeesDto = services.getEmployees() == null ?
                Collections.emptyList() :
                services.getEmployees().stream()
                        .map(Employee::toDto)
                        .toList();

         */

        Set<EmployeeDto> employeeDtoList = employees.stream()
                .map(employee -> EmployeeDto.builder()
                        .id(employee.getId())
                        .name(employee.getName())
                        .specialization(employee.getSpecialization())
                        .build()
                )
                .collect(Collectors.toSet());


        return ServicesDto
                .builder()
                .id(id)
                .serviceType(serviceType.toDto())
                .price(price)
                .description(description)
                .employees(employeeDtoList)
                .build();
    }



}
