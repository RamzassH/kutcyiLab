package tech.reliab.course.kutcyirr.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.reliab.course.kutcyirr.entity.Bank;
import tech.reliab.course.kutcyirr.entity.BankOffice;
import tech.reliab.course.kutcyirr.entity.BankOfficeStatus;
import tech.reliab.course.kutcyirr.model.BankOfficeRequest;
import tech.reliab.course.kutcyirr.repository.BankOfficeRepository;
import tech.reliab.course.kutcyirr.service.BankOfficeService;
import tech.reliab.course.kutcyirr.service.BankService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class BankOfficeServiceImpl implements BankOfficeService {

    private final BankOfficeRepository bankOfficeRepository;
    private final BankService bankService;

    public BankOffice createBankOffice(BankOfficeRequest bankOfficeRequest) {
        Bank bank = bankService.getBankById(bankOfficeRequest.getBankId());
        BankOffice bankOffice = new BankOffice(bankOfficeRequest.getName(), bankOfficeRequest.getAddress(),
                bankOfficeRequest.isCanPlaceAtm(), bankOfficeRequest.isCanIssueLoan(),
                bankOfficeRequest.isCashWithdrawal(), bankOfficeRequest.isCashDeposit(),
                bankOfficeRequest.getRentCost(), bank);
        bankOffice.setStatus(BankOfficeStatus.randomStatus());
        bankOffice.setOfficeMoney(generateOfficeMoney(bank));
        return bankOfficeRepository.save(bankOffice);
    }

    private double generateOfficeMoney(Bank bank) {
        return new Random().nextDouble(bank.getTotalMoney());
    }


    public BankOffice getBankOfficeById(int id) {
        return bankOfficeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("BankOffice was not found"));
    }

    public BankOffice getBankDtoOfficeById(int id) {
        return getBankOfficeById(id);
    }


    public List<BankOffice> getAllBankOffices() {
        return bankOfficeRepository.findAll();
    }


    public BankOffice updateBankOffice(int id, String name) {
        BankOffice bankOffice = getBankOfficeById(id);
        bankOffice.setName(name);
        return bankOfficeRepository.save(bankOffice);
    }


    public void deleteBankAtm(int id) {
        bankOfficeRepository.deleteById(id);
    }
}