package com.example.petprojectcrud.apiClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "bankClientService", url = "http://localhost:8082/my-bank")
public interface BankClient {

    @GetMapping("/payment/make-payment")
    String getMyBank();
}
