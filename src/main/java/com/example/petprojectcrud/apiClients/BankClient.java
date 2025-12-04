package com.example.petprojectcrud.apiClients;

import com.example.petprojectcrud.DTO.bankPayment.BankPaymentResponseDto;
import com.example.petprojectcrud.DTO.bankPayment.SendPaymentToBankDto;
import com.example.petprojectcrud.DTO.bankPayment.TransactionalResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/*
id 5
{
  "fio": "ООО Клиника ЛАПКИ ЦАРАПКИ",
  "phoneNumber": "+79161234567",
  "password": "securePass789",
  "pin": "9876",
  "userBillingDetails": [
    {
      "billingType": "BANK_ACCOUNT",
      "param1": "40817810100001234567",
      "param2": "АО ТИНЬКОФФ БАНК",
      "param3": "044525974",
      "idUser": null
    }
  ]
}
* */

/*
id 93 (petClinic)
id 6 (bank)
{
  "name": "Воронцов Артем Александрович",
  "email": "voroncov.artem@gmail.com",
  "phone": "+79033334455",
  "isActive": true,
  "address": {
    "city": "Москва",
    "street": "Тверская",
    "houseNumber": "25",
    "apartmentNumber": "120",
    "postalCode": "125009"
  },
  "pets": [
    {
      "name": "Игуан",
      "species": "Рептилия",
      "breed": "Зеленая игуана",
      "age": 2
    },
    {
      "name": "Немо",
      "species": "Рыба",
      "breed": "Клоун",
      "age": 1
    }
  ],
  "paymentNumber": ["5111-2222-3333-4444", "5555-1234-5678-9012"]
}

id 6
{
  "fio": "Воронцов Артем Александрович",
  "phoneNumber": "+79033334455",
  "password": "mySecurePassword123",
  "pin": "1234",
  "userBillingDetails": [
    {
      "billingType": "CREDIT_CARD",
      "param1": "5111-2222-3333-4444",
      "param2": "2020",
      "param3": "2027"
    },
    {
      "billingType": "BANK_ACCOUNT",
      "param1": "5555-1234-5678-9012",
      "param2": "Зеленый банк",
      "param3": "1548"
    }
  ]
}
*/

/// связь с банком
/// вызывается сервисом
@FeignClient(name = "bankClientService", url = "http://localhost:8082/my-bank")
public interface BankClient {

    @GetMapping("/payment/make-payment")
    String getMyBank();



    @PostMapping("/transaction/deposit")
    TransactionalResponseDto sendPaymentBankProj(@RequestBody SendPaymentToBankDto sendPaymentToBankDto);


}
