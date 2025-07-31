package com.example.petprojectcrud.model.employee;

import com.example.petprojectcrud.DTO.employee.MedicalServiceTypeDto;
import com.example.petprojectcrud.enums.ServicesTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "service_types")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MedicalServiceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "service_type", unique = true)
    @Enumerated(EnumType.STRING)
    private ServicesTypeEnum servicesTypeEnum;

    @Column(name = "update_time")
    @UpdateTimestamp
    private Date updateTime;

    @Column(name = "create_time")
    @CreationTimestamp
    private Date createTime;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;


    public MedicalServiceTypeDto toDto() {
        return MedicalServiceTypeDto.builder()
                .id(this.id)
                .servicesTypeEnum(this.servicesTypeEnum)
                .build();
    }
}
