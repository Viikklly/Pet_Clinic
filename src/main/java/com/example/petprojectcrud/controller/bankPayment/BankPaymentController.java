package com.example.petprojectcrud.controller.bankPayment;

import com.example.petprojectcrud.DTO.bankPayment.BankPaymentRequestDto;
import com.example.petprojectcrud.DTO.bankPayment.TransactionalResponseDto;
import com.example.petprojectcrud.service.bankPayment.BankPaymentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/bankPayment")
public class BankPaymentController {
    private BankPaymentService bankPaymentService;

    @PostMapping
    public TransactionalResponseDto createSendBankPayment(@RequestBody BankPaymentRequestDto bankPaymentRequestDto) {
        return bankPaymentService.createSendBankPayment(bankPaymentRequestDto);
    }
}
