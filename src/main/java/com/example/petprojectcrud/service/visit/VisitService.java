package com.example.petprojectcrud.service.visit;

import com.example.petprojectcrud.DTO.priem.VisitDtoResponse;
import com.example.petprojectcrud.DTO.priem.VisitDtoRequest;

import java.util.List;

public interface VisitService {
    public VisitDtoResponse createVisit(VisitDtoRequest visitDtoRequest);
    public List<VisitDtoResponse> getAllVisits();
}
