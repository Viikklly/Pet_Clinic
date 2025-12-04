package com.example.petprojectcrud.service.bankPayment;


import com.example.petprojectcrud.DTO.bankPayment.BankPaymentRequestDto;
import com.example.petprojectcrud.DTO.bankPayment.TransactionalResponseDto;

public interface BankPaymentService {
    TransactionalResponseDto createSendBankPayment(BankPaymentRequestDto bankPaymentRequestDto);
}
