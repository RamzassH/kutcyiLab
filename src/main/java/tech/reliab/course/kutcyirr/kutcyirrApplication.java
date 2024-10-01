package tech.reliab.course.kutcyirr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tech.reliab.course.kutcyirr.builders.BankBuilder;
import tech.reliab.course.kutcyirr.entity.BankAtm;
import tech.reliab.course.kutcyirr.entity.BankOffice;
import tech.reliab.course.kutcyirr.entity.CreditAccount;
import tech.reliab.course.kutcyirr.entity.Employee;
import tech.reliab.course.kutcyirr.entity.PaymentAccount;
import tech.reliab.course.kutcyirr.entity.User;
import tech.reliab.course.kutcyirr.service.BankAtmService;
import tech.reliab.course.kutcyirr.service.BankOfficeService;
import tech.reliab.course.kutcyirr.service.BankService;
import tech.reliab.course.kutcyirr.service.CreditAccountService;
import tech.reliab.course.kutcyirr.service.EmployeeService;
import tech.reliab.course.kutcyirr.service.PaymentAccountService;
import tech.reliab.course.kutcyirr.service.UserService;
import tech.reliab.course.kutcyirr.service.impl.BankAtmServiceImpl;
import tech.reliab.course.kutcyirr.service.impl.BankOfficeServiceImpl;
import tech.reliab.course.kutcyirr.service.impl.BankServiceImpl;
import tech.reliab.course.kutcyirr.service.impl.CreditAccountServiceImpl;
import tech.reliab.course.kutcyirr.service.impl.EmployeeServiceImpl;
import tech.reliab.course.kutcyirr.service.impl.PaymentAccountServiceImpl;
import tech.reliab.course.kutcyirr.service.impl.UserServiceImpl;

import java.time.LocalDate;

@SpringBootApplication
public class kutcyirrApplication {

    public static void main(String[] args) {
        SpringApplication.run(kutcyirrApplication.class, args);
        var bankBuilder = new BankBuilder();
        var bank = bankBuilder.setBankName("Лукиту Банк").createBank();


        UserService userService = new UserServiceImpl();
        User user = userService.createUser("Куцый Роман Русланович", LocalDate.now(), "Дед/Доест/Хочу_спать/дайте_денег");

        BankService bankService = new BankServiceImpl(userService);
        bankService.registerBank(bank);

        BankOfficeService bankOfficeService = new BankOfficeServiceImpl(bankService);
        BankOffice bankOffice = bankOfficeService.createBankOffice(
                "Arasaka tower",
                "Майская 69",
                true,
                true,
                true,
                true,
                1000,
                bank
        );

        EmployeeService employeeService = new EmployeeServiceImpl(bankService);
        Employee employee = employeeService.createEmployee(
                "Шамраев Александр Анатольевич",
                LocalDate.now(),
                "Говорит много",
                bank,
                false,
                bankOffice,
                true,
                30000
        );

        BankAtmService bankAtmService = new BankAtmServiceImpl(bankService);
        BankAtm bankAtm = bankAtmService.createBankAtm(
                "Худший банкомат",
                "Майская 69",
                bank,
                bankOffice,
                employee,
                true,
                true,
                500
        );

        PaymentAccountService paymentAccountService = new PaymentAccountServiceImpl(userService, bankService);
        PaymentAccount paymentAccount = paymentAccountService.createPaymentAccount(user, bank);

        CreditAccountService creditAccountService = new CreditAccountServiceImpl(userService, bankService);
        CreditAccount creditAccount = creditAccountService.createCreditAccount(
                user,
                bank,
                LocalDate.now(),
                8,
                500000,
                14,
                employee,
                paymentAccount
        );

        System.out.println(bank);
        System.out.println(user);
        System.out.println(bankOffice);
        System.out.println(employee);
        System.out.println(bankAtm);
        System.out.println(paymentAccount);
        System.out.println(creditAccount);
    }
}
