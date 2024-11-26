package tech.reliab.course.kutcyirr.service;

import tech.reliab.course.kutcyirr.entity.PaymentAccount;
import tech.reliab.course.kutcyirr.model.PaymentAccountRequest;

import java.util.List;

public interface PaymentAccountService {

    PaymentAccount createPaymentAccount(PaymentAccountRequest paymentAccountRequest);

    PaymentAccount getPaymentAccountById(int id);

    PaymentAccount getPaymentAccountDtoById(int id);

    List<PaymentAccount> getAllPaymentAccounts();

    PaymentAccount updatePaymentAccount(int id, int bankId);

    void deletePaymentAccount(int id);
}