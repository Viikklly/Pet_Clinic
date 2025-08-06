package com.example.petprojectcrud.DTO.visit;

import com.example.petprojectcrud.DTO.clients.OwnerDto;
import com.example.petprojectcrud.DTO.clients.PetDto;
import com.example.petprojectcrud.DTO.employee.EmployeeDto;
import com.example.petprojectcrud.DTO.employee.MedicalServicesDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;



@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Schema
public class VisitDto {
    private int id;

    private String description;

    private String petName;

    private String ownerName;

    private String employeeName;

    private Map<String, BigDecimal> servicesAndPrices;

    private BigDecimal totalPrice;

    private Date createTime;
}
