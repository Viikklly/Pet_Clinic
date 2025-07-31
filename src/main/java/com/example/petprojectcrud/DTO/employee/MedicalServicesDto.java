package com.example.petprojectcrud.DTO.employee;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder // строит без оператора new
@Schema
public class MedicalServicesDto {
    private Integer id;

    private MedicalServiceTypeDto serviceType;

    private BigDecimal price;

    private String description;

    private Set<EmployeeDto> employees =  new HashSet<>();
}
