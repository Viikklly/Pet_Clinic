package com.example.petprojectcrud.repository.address;

import com.example.petprojectcrud.model.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    Address findByOwnerId(int id);
    Address findByOwnerNameAndOwnerPhone(String ownerName, String phoneNumber);
}
