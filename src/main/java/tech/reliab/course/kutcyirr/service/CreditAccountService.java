package tech.reliab.course.kutcyirr.service;

import tech.reliab.course.kutcyirr.entity.CreditAccount;
import tech.reliab.course.kutcyirr.model.CreditAccountRequest;

import java.util.List;

public interface CreditAccountService {

    CreditAccount createCreditAccount(CreditAccountRequest creditAccountRequest);

    CreditAccount getCreditAccountById(int id);

    CreditAccount getCreditAccountDtoById(int id);

    List<CreditAccount> getAllCreditAccounts();

    CreditAccount updateCreditAccount(int id, int bankId);

    void deleteCreditAccount(int id);
}