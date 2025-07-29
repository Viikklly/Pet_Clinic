package com.example.petprojectcrud.controller.employee;


import com.example.petprojectcrud.DTO.employee.ServicesDto;
import com.example.petprojectcrud.service.employee.ServicesService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/services")
public class ServicesController {

    private ServicesService servicesService;

    @GetMapping
    public List<ServicesDto> getAllServices() {
        return servicesService.getAllServices();
    }

    @PostMapping
    public ServicesDto createService(@RequestBody ServicesDto servicesDto) {
        return servicesService.createService(servicesDto);
    }

    @PutMapping("/{id}")
    public ServicesDto updateServices(@PathVariable Integer id, @RequestBody ServicesDto servicesDto) {
        return servicesService.updateService(id, servicesDto);
    }


    @DeleteMapping("/{id}")
    public void deleteService(@PathVariable Integer id) {
        servicesService.deleteService(id);
    }

}
