package com.example.petprojectcrud.DTO.priem;


import com.example.petprojectcrud.model.clients.Owner;
import com.example.petprojectcrud.model.clients.Pet;
import com.example.petprojectcrud.model.employee.Employee;
import com.example.petprojectcrud.model.employee.Services;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Schema
public class VisitDto {

    private int id;

    private String description;

    private Pet pet;

    private Owner owner;

    private Employee employee;

    private Set<Services> services;

    private Set<BigDecimal> servicePrices;

    private BigDecimal totalPrice;



}
