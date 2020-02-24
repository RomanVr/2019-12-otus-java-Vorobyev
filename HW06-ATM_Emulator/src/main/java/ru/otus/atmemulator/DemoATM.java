package ru.otus.atmemulator;

import java.util.*;

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

        for(ATM.Banknotes banknot: ATM.Banknotes.values()) {
            System.out.println("banknot = " + banknot.value);
        }

        System.out.println("Balance: " + atm.getBalance());

        atm.getCash(25689);

        System.out.println("Balance: " + atm.getBalance());
    }
}
