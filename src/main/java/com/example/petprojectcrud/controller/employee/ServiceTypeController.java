package com.example.petprojectcrud.controller.employee;


import com.example.petprojectcrud.DTO.employee.MedicalServiceTypeDto;
import com.example.petprojectcrud.service.employee.MedicalServicesTypeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/service_type")
public class ServiceTypeController {

    private MedicalServicesTypeService medicalServicesTypeService;


    @GetMapping
    public List<MedicalServiceTypeDto> getAllServiceTypes() {
        return medicalServicesTypeService.getAllServiceTypes();
    }

    @GetMapping("/{id}")
    public MedicalServiceTypeDto getServiceTypeId(@PathVariable Integer id) {
        return medicalServicesTypeService.getServiceTypeById(id);
    }

}
