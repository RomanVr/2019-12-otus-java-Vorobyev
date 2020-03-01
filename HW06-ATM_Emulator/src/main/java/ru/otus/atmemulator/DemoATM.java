package ru.otus.atmemulator;

public class DemoATM {
    public static void main(String[] args) {
        ATM atm = ATM.newBuilder()
                .setCountB100(2)
                .setCountB200(4)
                .setCountB500(6)
                .setCountB1000(3)
                .setCountB2000(6)
                .setCountB5000(10)
                .build();

        ATM_Service atmService = new ATM_Service(atm);

        for(Banknote banknot: Banknote.values()) {
            System.out.println("banknot = " + banknot.value);
        }

        System.out.println("Balance: " + atmService.getBalance());

        atmService.getCash(25689);

        System.out.println("Balance: " + atmService.getBalance());
    }
}
