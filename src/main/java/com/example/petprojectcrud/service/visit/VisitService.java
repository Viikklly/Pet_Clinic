package com.example.petprojectcrud.service.visit;

import com.example.petprojectcrud.DTO.visit.VisitDto;
import com.example.petprojectcrud.DTO.visit.VisitDtoResponse;
import com.example.petprojectcrud.DTO.visit.VisitDtoRequest;

import java.util.List;

public interface VisitService {
    public VisitDto createVisit(VisitDtoRequest visitDtoRequest);
    public List<VisitDto> getAllVisits();
    public VisitDto getVisitById(int id);
}
