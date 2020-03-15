package ru.otus.atmemulator;

public class DemoATM {
    public static void main(String[] args) {
        ATM atm = AtmImpl.newBuilder()
                .setCountB100(2)
                .setCountB200(4)
                .setCountB500(6)
                .setCountB1000(3)
                .setCountB2000(6)
                .setCountB5000(10)
                .build();

        for(BanknoteEnum banknot: BanknoteEnum.values()) {
            System.out.println("banknot = " + banknot.value);
        }

        System.out.println("Balance: " + ATMService.getBalance(atm));

        ATMService.getCash(atm, 25689);

        System.out.println("Balance: " + ATMService.getBalance(atm));
    }
}
