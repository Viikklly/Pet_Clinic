package com.example.petprojectcrud.controller.employee;


import com.example.petprojectcrud.DTO.employee.ServiceTypeDto;
import com.example.petprojectcrud.service.employee.ServicesTypeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/service_type")
public class ServiceTypeController {

    private ServicesTypeService servicesTypeService;


    @GetMapping
    public List<ServiceTypeDto> getAllServiceTypes() {
        return servicesTypeService.getAllServiceTypes();
    }

    @GetMapping("/{id}")
    public ServiceTypeDto getServiceTypeId(@PathVariable Integer id) {
        return servicesTypeService.getServiceTypeById(id);
    }

}
