package com.example.petprojectcrud.repository.address;

import com.example.petprojectcrud.model.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
