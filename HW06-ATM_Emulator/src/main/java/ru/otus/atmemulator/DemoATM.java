package ru.otus.atmemulator;

public class DemoATM {
    public static void main(String[] args) {
        AtmImpl atm = AtmImpl.newBuilder()
                .setCountB100(2)
                .setCountB200(4)
                .setCountB500(6)
                .setCountB1000(3)
                .setCountB2000(6)
                .setCountB5000(10)
                .build();

        ATMService atmService = new ATMService(atm);

        for(BanknoteEnum banknot: BanknoteEnum.values()) {
            System.out.println("banknot = " + banknot.value);
        }

        System.out.println("Balance: " + atmService.getBalance());

        atmService.getCash(25689);

        System.out.println("Balance: " + atmService.getBalance());
    }
}
