package ru.otus.department;

import ru.otus.atm.ATM;

import java.util.HashSet;
import java.util.Set;

public class Department {
    private final Set<ATM> atms;
    private final AtmService atmService;

    public Department(AtmService atmService) {
        this.atms = new HashSet<>();
        this.atmService = atmService;
    }

    public boolean addAtm(ATM atm) {
        return this.atms.add(atm);
    };

    public boolean remove(ATM atm) {
        return this.atms.remove(atm);
    }

    public int getAllBalance() {
        int balanceAll = 0;
        for(ATM atm: atms) {
            balanceAll += this.atmService.getBalance(atm);
        }
        return balanceAll;
    }

    //Listener pattern
    public void toReset() {
        atms.forEach(atm -> atm.getListener().onReset());
    }

}
