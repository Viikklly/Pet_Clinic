package com.example.petprojectcrud.controller.employee;


import com.example.petprojectcrud.DTO.employee.MedicalServicesDto;
import com.example.petprojectcrud.service.employee.MedicalServicesService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/services")
public class MedicalServicesController {

    private MedicalServicesService medicalServicesService;

    @GetMapping
    public List<MedicalServicesDto> getAllServices() {
        return medicalServicesService.getAllServices();
    }

    @PostMapping
    public MedicalServicesDto createService(@RequestBody MedicalServicesDto medicalServicesDto) {
        return medicalServicesService.createService(medicalServicesDto);
    }

    @PutMapping("/{id}")
    public MedicalServicesDto updateServices(@PathVariable Integer id, @RequestBody MedicalServicesDto medicalServicesDto) {
        return medicalServicesService.updateService(id, medicalServicesDto);
    }


    @DeleteMapping("/{id}")
    public void deleteService(@PathVariable Integer id) {
        medicalServicesService.deleteService(id);
    }

}
