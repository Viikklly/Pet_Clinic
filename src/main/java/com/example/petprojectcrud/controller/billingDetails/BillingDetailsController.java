/*
package com.example.petprojectcrud.controller.billingDetails;


import com.example.petprojectcrud.DTO.billingDetails.BillingDetailsCreateDto;
import com.example.petprojectcrud.DTO.billingDetails.BillingDetailsResponseDto;
import com.example.petprojectcrud.service.billingDetails.BillingDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/billingDetails")
public class BillingDetailsController {
    BillingDetailsService billingDetailsService;


    @GetMapping
    public List<BillingDetailsResponseDto> getAllBillingDetails() {
        return billingDetailsService.getAllBillingDetails();
    }

    @PostMapping
    public BillingDetailsResponseDto createBillingDetails(@RequestBody BillingDetailsCreateDto dto) {
        return billingDetailsService.createBillingDetails(dto);
    }




}
*/
