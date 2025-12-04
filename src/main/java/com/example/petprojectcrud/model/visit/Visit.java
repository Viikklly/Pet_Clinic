package com.example.petprojectcrud.model.visit;


import com.example.petprojectcrud.DTO.visit.VisitDto;
import com.example.petprojectcrud.DTO.visit.VisitDtoResponse;
import com.example.petprojectcrud.DTO.visit.VisitDtoRequest;
import com.example.petprojectcrud.model.clients.Owner;
import com.example.petprojectcrud.model.clients.Pet;
import com.example.petprojectcrud.model.employee.Employee;
import com.example.petprojectcrud.model.employee.MedicalServices;
import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "visit")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "description")
    private String description;

    @JoinColumn(name = "pet_id",
            referencedColumnName = "pet_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Pet pet;


    @JoinColumn(name = "owner_id",
            referencedColumnName = "owner_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Owner owner;

    @JoinColumn(name = "employee_id",
            referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee employee;


    @Type(ListArrayType.class)
    @Column(
            name = "services",
            columnDefinition = "integer[]"
    )
    private List<Integer> idsServices; // TODO serviceIds

    @Column(name = "total_price",
            precision = 10,
            scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "update_time")
    @UpdateTimestamp
    private Date updateTime;

    @Column(name = "create_time")
    @CreationTimestamp
    private Date createTime;

    /// TODO дописать статус визита оплачен/не оплачен. статус берется из bankPayment


    public VisitDtoResponse toDto() {

        return VisitDtoResponse
                .builder()
                .id(id)
                .description(description)
                .pet(pet != null ? pet.toDto() : null)
                .owner(owner != null ? owner.toDto() : null)
                .employee(employee != null ? employee.toDto() : null)
                ///.services(services.stream().map(MedicalServices::toDto).collect(Collectors.toSet()))
                .totalPrice(totalPrice)
                .createTime(createTime)
                .build();
    }

    //Может и не надо
    public VisitDtoRequest toDtoRequest() {

        return VisitDtoRequest
                .builder()
                .id(id)
                .description(description)
                .petOwnerName(pet.getName())
                .ownerName(owner.getName())
                .ownerPhone(owner.getPhone())
                .employeeName(employee.getName())
                .totalPrice(totalPrice)
                .build();
    }


    // Необходимо сформировать и засетить Map<String, BigDecimal> servicesAndPrices;
    public VisitDto toVisitDto() {

        return VisitDto
                .builder()
                .id(id)
                .description(description)
                .ownerName(owner.getName())
                .petName(pet.getName())
                .employeeName(employee.getName())
                .totalPrice(totalPrice)
                .createTime(createTime)
                .build();
    }
}
