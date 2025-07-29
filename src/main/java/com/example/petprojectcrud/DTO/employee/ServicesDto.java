package com.example.petprojectcrud.DTO.employee;


import com.example.petprojectcrud.model.employee.Employee;
import com.example.petprojectcrud.model.employee.ServiceType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder // строит без оператора new
@Schema
public class ServicesDto {
    private Integer id;

    private ServiceTypeDto serviceType;

    private BigDecimal price;

    private String description;

    private Set<EmployeeDto> employees =  new HashSet<>();
}
