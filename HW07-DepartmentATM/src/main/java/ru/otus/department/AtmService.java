package ru.otus.department;

import ru.otus.atm.ATM;
import ru.otus.atm.BanknoteEnum;

import java.util.Map;

public interface AtmService {
    public int getBalance(ATM atm);
    public Map<BanknoteEnum, Integer> getCash(ATM atm, int amount);
}
