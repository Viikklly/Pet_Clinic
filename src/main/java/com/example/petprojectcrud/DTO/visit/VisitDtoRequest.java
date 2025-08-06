package com.example.petprojectcrud.DTO.visit;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Schema
// Request Dto запрос из ui
public class VisitDtoRequest {

    private int id;

    private String description;

    private String petOwnerName;

    private String ownerName;

    private String ownerPhone;

    private String employeeName;

    private List<Integer> idsServices;

    //private Set<BigDecimal> servicePrices;

    private BigDecimal totalPrice;
}
