package com.example.petprojectcrud.DTO.clients;

import com.example.petprojectcrud.DTO.address.AddressDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@NoArgsConstructor
@Data
@Schema
@SuperBuilder
public class OwnerDto {

    private Long id;

    private String name;

    private String email;

    private String phone;

    private AddressDto address;

    List<PetDto> pets;

    List<String> paymentNumber;
}
