package ru.otus.demo;

import ru.otus.atm.ATM;
import ru.otus.atm.AtmImpl;
import ru.otus.department.AtmService;
import ru.otus.department.AtmServiceImpl;

public class DemoDepartment {
    public static void main(String[] args) {
        ATM atm = AtmImpl.newBuilder()
                .setCountB100(2)
                .setCountB200(2)
                .setCountB500(1)
                .setCountB1000(3)
                .setCountB2000(1)
                .setCountB5000(1)
                .build();
        AtmService atmService = new AtmServiceImpl();

        System.out.println("Balance: " + atmService.getBalance(atm));
    }
}
