package ru.otus.atmemulator;

import java.util.Map;
import java.util.TreeMap;

public class ATMService {
    public static int getBalance(ATM atm) {
        int balance = 0;
        for(Map.Entry<BanknoteEnum, Cassette> item: atm.getCassetteMap().entrySet()) {
            balance += item.getKey().value * item.getValue().getCount();
        }
        return balance;
    }

    public static Map<BanknoteEnum, Integer> getCash(ATM atm, int amount) {
        if(getBalance(atm) - amount < 0) {
            throw new RuntimeException("limit is exceeded");
        }
        final Map<BanknoteEnum, Integer> banknotesNew = new TreeMap<>();
        int rest = amount;
        int factor = 0;
        int currentCount = 0;

        for(Map.Entry<BanknoteEnum, Cassette> item: atm.getCassetteMap().entrySet()) {
            if(item.getValue().getCount() == 0) continue;
            currentCount = item.getValue().getCount();
            if(rest >= item.getKey().value) {
                factor = rest / item.getKey().value;
                if(factor > currentCount) {
                    factor = currentCount;
                }
                rest = rest - item.getKey().value * factor;
                atm.setCountBanknote(item.getKey(), currentCount - factor);
                banknotesNew.put(item.getKey(), factor);
                if(rest == 0) return banknotesNew;
            }
        }
        return banknotesNew;
    }
}
