package com.example.petprojectcrud.DTO.employee;


import com.example.petprojectcrud.enums.ServicesTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder // строит без оператора new
@Schema
public class ServiceTypeDto {
    private Integer id;

    private ServicesTypeEnum servicesTypeEnum;
}
