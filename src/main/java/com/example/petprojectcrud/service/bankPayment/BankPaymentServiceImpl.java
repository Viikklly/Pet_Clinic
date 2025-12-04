package com.example.petprojectcrud.service.bankPayment;

import com.example.petprojectcrud.DTO.bankPayment.BankPaymentRequestDto;
import com.example.petprojectcrud.DTO.bankPayment.SendPaymentToBankDto;
import com.example.petprojectcrud.DTO.bankPayment.TransactionalResponseDto;
import com.example.petprojectcrud.apiClients.BankClient;
import com.example.petprojectcrud.exception.NotFoundException;
import com.example.petprojectcrud.model.clients.Owner;
import com.example.petprojectcrud.model.visit.Visit;
import com.example.petprojectcrud.repository.visit.VisitRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
@Transactional
public class BankPaymentServiceImpl implements BankPaymentService {

    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private BankClient bankClient;

    public TransactionalResponseDto createSendBankPayment(BankPaymentRequestDto bankPaymentRequestDto) {

        if (bankPaymentRequestDto.getVisitId() == null || bankPaymentRequestDto.getVisitId() <= 0) {
            throw new IllegalArgumentException("Visit ID должен быть задан и быть больше 0");
        }
        Optional<Visit> byId = visitRepository.findById(bankPaymentRequestDto.getVisitId());

        Visit visit = byId.orElseThrow(() ->
                new NotFoundException("Визит с ID " + bankPaymentRequestDto.getVisitId() + " не найден")
        );

        Owner owner = visit.getOwner();


        /// надо для DTO спрашиваем имя, номер телефона и платежные данные
        String ownerName = owner.getName();
        String ownerPhone = owner.getPhone();
        List<String> paymentNumber = owner.getPaymentNumber();



        /// Сумма оплаты
        BigDecimal totalPrice = visit.getTotalPrice();

        /// Данные счета, с которого будет совершаться списание денег надо прописать ЯВНО в запросе
        /// данные аккаунта клиники заполняются в SendPaymentToBankDto
        SendPaymentToBankDto sendPaymentToBankDto = SendPaymentToBankDto.builder()
                .fromAccountNameUser(ownerName)
                .fromAccountPhoneUser(ownerPhone)
                .fromAccountPaymentNumberUser(bankPaymentRequestDto.getPaymentNumberUser())
                .amount(totalPrice)
                .description("Оплата услуг ООО Клиника ЛАПКИ ЦАРАПКИ")
                .build();

        TransactionalResponseDto transactionalResponseDto = bankClient.sendPaymentBankProj(sendPaymentToBankDto);

        /// ТУТ можно вытащить статус операции, что бы добавить в VISIT

        return bankClient.sendPaymentBankProj(sendPaymentToBankDto);
    }
}
