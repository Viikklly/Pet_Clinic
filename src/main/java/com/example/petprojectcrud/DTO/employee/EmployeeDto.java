package com.example.petprojectcrud.DTO.employee;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder // строит без оператора new
@Schema
public class EmployeeDto {
    private Integer id;

    private String name;

    private String specialization;

    private Set<MedicalServicesDto> services = new HashSet<>();
}
