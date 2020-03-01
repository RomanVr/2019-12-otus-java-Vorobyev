package ru.otus.atmemulator;

import java.util.Map;
import java.util.TreeMap;

public class ATM_Service {
    private final ATM_Interface atm;

    public ATM_Service(ATM_Interface atm){
        if(atm == null) {
            throw new IllegalArgumentException("Cannot be null!");
        }
        this.atm = atm;
    }

    public void addCountBanknot(Banknote banknot, int count) {
        int countOld = this.atm.getCountBanknot(banknot);
        this.atm.setCountBanknote(banknot, count + countOld);
    }

    public int getBalance() {
        int balance = 0;
        for(Map.Entry<Banknote, Integer> item: this.atm.getBanknotes().entrySet()) {
            balance += item.getKey().value * item.getValue();
        }
        return balance;
    }

    public Map<Banknote, Integer> getCash(int amount) {
        if(this.getBalance() - amount < 0) {
            throw new RuntimeException("limit is exceeded");
        }
        final Map<Banknote, Integer> banknotesNew = new TreeMap<>();
        int rest = amount;
        int factor = 0;
        int currentCount = 0;

        for(Map.Entry<Banknote, Integer> item: atm.getBanknotes().entrySet()) {
            if(item.getValue() == 0) continue;
            currentCount = item.getValue();
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
