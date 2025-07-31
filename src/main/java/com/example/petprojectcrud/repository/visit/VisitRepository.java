package com.example.petprojectcrud.repository.visit;

import com.example.petprojectcrud.model.visit.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VisitRepository extends JpaRepository<Visit, Integer> {
}
