package tech.reliab.course.kutcyirr.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import tech.reliab.course.kutcyirr.entity.CreditAccount;
import tech.reliab.course.kutcyirr.model.CreditAccountRequest;

import java.util.List;

public interface CreditAccountController {
    ResponseEntity<CreditAccount> createCreditAccount(@RequestBody CreditAccountRequest creditAccountRequest);

    ResponseEntity<Void> deleteCreditAccount(@PathVariable int id);

    ResponseEntity<CreditAccount> updateCreditAccount(@PathVariable int id, @RequestParam(name = "bankId") int bankId);

    ResponseEntity<CreditAccount> getBankByCreditAccount(@PathVariable int id);

    ResponseEntity<List<CreditAccount>> getAllCreditAccounts();
}