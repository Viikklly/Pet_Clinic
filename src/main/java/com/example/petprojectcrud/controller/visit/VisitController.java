package com.example.petprojectcrud.controller.visit;

import com.example.petprojectcrud.DTO.priem.VisitDtoResponse;
import com.example.petprojectcrud.DTO.priem.VisitDtoRequest;
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
    public List<VisitDtoResponse> getAllVisits() {
        return visitService.getAllVisits();
    }

    @PostMapping
    public VisitDtoResponse createVisit(@RequestBody VisitDtoRequest visitDtoRequest) {
        return visitService.createVisit(visitDtoRequest);
    }

}



