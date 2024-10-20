package tech.reliab.course.kutcyirr;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tech.reliab.course.kutcyirr.builders.BankBuilder;
import tech.reliab.course.kutcyirr.entity.*;
import tech.reliab.course.kutcyirr.service.*;
import tech.reliab.course.kutcyirr.service.impl.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

@SpringBootApplication
public class KutcyirrApplication implements CommandLineRunner {
    private static final int BANKS_COUNT = 5;

    static UserService userService = new UserServiceImpl();
    static BankService bankService = new BankServiceImpl(userService);
    static EmployeeService employeeService = new EmployeeServiceImpl(bankService);
    static BankAtmService bankAtmService = new BankAtmServiceImpl(bankService);
    static PaymentAccountService paymentAccountService = new PaymentAccountServiceImpl(userService, bankService);
    static CreditAccountService creditAccountService = new CreditAccountServiceImpl(userService, bankService);
    static BankOfficeService bankOfficeService = new BankOfficeServiceImpl(bankService);

    public static void main(String[] args) {
        SpringApplication.run(KutcyirrApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        initializeData();
        showMenu();
    }

    private void initializeData() {
        List<Bank> banks = initializeBanks();
        banks.forEach(this::initializeBankInfo);
    }

    private List<Bank> initializeBanks() {
        return IntStream.range(0, BANKS_COUNT)
                .mapToObj(index -> new BankBuilder().setBankName(String.format("Лукиту Банк %d", index)).createBank())
                .toList();
    }

    private void initializeBankInfo(Bank bank) {
        bankService.registerBank(bank);

        int employeeCount = 5;
        int officeCount = 5;
        int atmCount = 5;

        List<User> users = Arrays.asList(
                userService.createUser("Григорьев Роман Шаманович", LocalDate.now(), "Программист/Духовник"),
                userService.createUser("Шаманов Григорий Романович", LocalDate.now(), "Фуллстек/Менеджер/Альпенист"),
                userService.createUser("Романов Шаман Григорьевич", LocalDate.now(), "Шаман/Дрон/Ярусский")
        );

        List<BankOffice> offices = IntStream.range(0, officeCount)
                .mapToObj(index -> bankOfficeService.createBankOffice(
                        String.format("Arasaka tower %d", index),
                        "Майская 69",
                        true,
                        true,
                        true,
                        true,
                        1000,
                        bank
                )).toList();

        List<Employee> employees = IntStream.range(0, employeeCount)
                .mapToObj(index -> employeeService.createEmployee(
                        String.format("Шамраев Александр%d Анатольевич", index),
                        LocalDate.now(),
                        "Говорит много",
                        bank,
                        false,
                        offices.get(index % offices.size()),
                        true,
                        30000
                )).toList();

        IntStream.range(0, atmCount).forEach(index -> bankAtmService.createBankAtm(
                "Худший банкомат",
                "Майская 69",
                bank,
                offices.get(index % offices.size()),
                employees.get(index % employees.size()),
                true,
                true,
                500
        ));

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            Employee assignedEmployee = employees.get(i % employees.size());

            PaymentAccount paymentAccount1 = paymentAccountService.createPaymentAccount(user, bank);
            PaymentAccount paymentAccount2 = paymentAccountService.createPaymentAccount(user, bank);

            creditAccountService.createCreditAccount(
                    user,
                    bank,
                    LocalDate.now(),
                    8,
                    500000,
                    14,
                    assignedEmployee,
                    paymentAccount1
            );
            creditAccountService.createCreditAccount(
                    user,
                    bank,
                    LocalDate.now(),
                    12,
                    50002,
                    14,
                    assignedEmployee,
                    paymentAccount2
            );
        }
    }

    private void showMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nВыберите опцию:");
            System.out.println("1. Вывести банк");
            System.out.println("2. Вывести информацию о пользователе");
            System.out.println("3. Выход");
            System.out.print("Ваш выбор: ");

            int option;
            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите корректный номер опции.");
                continue;
            }

            switch (option) {
                case 1 -> handleBankOut(scanner);
                case 2 -> handleUserInfoOut(scanner);
                case 3 -> {
                    System.out.println("Выход из программы.");
                    return;
                }
                default -> System.out.println("Неверная опция. Попробуйте снова.");
            }
        }
    }

    private void handleBankOut(Scanner scanner) {
        List<Bank> banks = bankService.getAllBanks();
        if (banks.isEmpty()) {
            System.out.println("Нет зарегистрированных банков.");
            return;
        }

        System.out.println("\nСписок банков:");
        banks.forEach(System.out::println);

        System.out.print("Введите ID банка: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ID.");
            return;
        }

        Bank bank = bankService.getBankById(id).orElse(null);
        if (bank == null) {
            System.out.println("Банк с таким ID не найден.");
            return;
        }

        System.out.println("\nИнформация о банке:");
        System.out.println(bank);

        System.out.println("\nБанкоматы:");
        List<BankAtm> atms = bankAtmService.getAllBankAtmsByBank(bank);
        atms.forEach(System.out::println);

        System.out.println("\nОфисы:");
        List<BankOffice> offices = bankOfficeService.getAllBankOfficesByBank(bank);
        offices.forEach(System.out::println);

        System.out.println("\nСотрудники:");
        List<Employee> employees = employeeService.getAllEmployeesByBank(bank);
        employees.forEach(System.out::println);

        System.out.println("\nКлиенты:");
        List<User> users = userService.getUsersByBank(bank);
        users.forEach(System.out::println);
    }

    private void handleUserInfoOut(Scanner scanner) {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("Нет зарегистрированных пользователей.");
            return;
        }

        System.out.println("\nСписок пользователей:");
        users.forEach(System.out::println);

        System.out.print("Введите ID пользователя: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ID.");
            return;
        }

        User user = userService.getUserById(id).orElse(null);
        if (user == null) {
            System.out.println("Пользователь с таким ID не найден.");
            return;
        }

        System.out.println("\nИнформация о пользователе:");
        System.out.println(user);

        System.out.println("\nКредитные счета:");
        List<CreditAccount> creditAccounts = creditAccountService.getCreditAccountByUserId(user.getId());
        creditAccounts.forEach(System.out::println);

        System.out.println("\nПлатёжные счета:");
        List<PaymentAccount> paymentAccounts = paymentAccountService.getAllPaymentAccountsByUserId(user.getId());
        paymentAccounts.forEach(System.out::println);
    }
}
