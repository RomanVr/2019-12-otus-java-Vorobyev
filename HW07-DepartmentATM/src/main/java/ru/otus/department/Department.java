package ru.otus.department;

import ru.otus.atm.ATM;

import java.util.HashSet;
import java.util.Set;

public class Department {
    private final Set<ATM> atms;

    private Department() {
        this.atms = new HashSet<>();
    }

    public boolean addAtm(ATM atm) {
        return this.atms.add(atm);
    };

    public boolean remove(ATM atm) {
        return this.atms.remove(atm);
    }
}
