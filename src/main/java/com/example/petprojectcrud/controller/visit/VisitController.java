package com.example.petprojectcrud.controller.visit;

import com.example.petprojectcrud.DTO.visit.VisitDto;
import com.example.petprojectcrud.DTO.visit.VisitDtoRequest;
import com.example.petprojectcrud.service.visit.VisitService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/visit")
public class VisitController {
    private VisitService visitService;


    @GetMapping
    public List<VisitDto> getAllVisits() {
        return visitService.getAllVisits();
    }


    @PostMapping
    public VisitDto createVisit(@RequestBody VisitDtoRequest visitDtoRequest) {
        return visitService.createVisit(visitDtoRequest);
    }

    @GetMapping("/{id}")
    public VisitDto getVisitById(@PathVariable int id) {
        return visitService.getVisitById(id);
    }

}



