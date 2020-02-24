
package ru.otus.atmemulator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ATM {
    private final Map<Banknotes, Integer> banknotesCount;

    private ATM(){
        this.banknotesCount = new TreeMap<>(Collections.reverseOrder());
    }

    public enum Banknotes {
        B100(100), B200(200), B500(500), B1000(1000), B2000(2000), B5000(5000);

        int value;

        Banknotes(int value) {
            this.value = value;
        }
    }

    public Map<Banknotes, Integer> getBanknotesCount() {
        return new TreeMap<ATM.Banknotes, Integer>(this.banknotesCount);
    };

    public int getBalance() {
        int balance = 0;
        for(Map.Entry<ATM.Banknotes, Integer> item: this.banknotesCount.entrySet()) {
            balance += item.getKey().value * item.getValue();
        }
        return balance;
    }

    public Map<Banknotes, Integer> getCash(int amount) {
        if(this.getBalance() - amount < 0) {
            throw new RuntimeException("limit is exceeded");
        }
        final Map<Banknotes, Integer> banknotesNew = new HashMap<>();
        int rest = amount;
        int factor = 0;
        int currentCount = 0;

        for(Map.Entry<ATM.Banknotes, Integer> item: this.banknotesCount.entrySet()) {
            if(item.getValue() == 0) continue;
            currentCount = item.getValue();
            if(rest >= item.getKey().value) {
                factor = rest / item.getKey().value;
                if(factor > currentCount) {
                    factor = currentCount;
                }
                rest = rest - item.getKey().value * factor;
                this.setCountBanknot(item.getKey(), currentCount - factor);
                banknotesNew.put(item.getKey(), factor);
                if(rest == 0) return banknotesNew;
            }
        }
        return banknotesNew;
    }

    public static Builder newBuilder() {
        return new ATM().new Builder();
    }

    public int getCountBanknot(Banknotes banknot) {
        return this.banknotesCount.get(banknot);
    }

    public void setCountBanknot(Banknotes banknot, int count) {
        if(count < 0) return;
        this.banknotesCount.put(banknot, count);
    }

    public void addCountBanknot(Banknotes banknot, int count) {
        if(count < 0) return;
        int countOld = this.banknotesCount.getOrDefault(banknot, 0);
        this.banknotesCount.put(banknot, count + countOld);
    }


    public class Builder {
        private Builder() {}

        public Builder setCountB100(int countB100) {
            ATM.this.setCountBanknot(Banknotes.B100, countB100);
            return this;
        }

        public Builder setCountB200(int countB200) {
            ATM.this.setCountBanknot(Banknotes.B200, countB200);
            return this;
        }

        public Builder setCountB500(int countB500) {
            ATM.this.setCountBanknot(Banknotes.B500, countB500);
            return this;
        }

        public Builder setCountB1000(int countB1000) {
            ATM.this.setCountBanknot(Banknotes.B1000, countB1000);
            return this;
        }

        public Builder setCountB2000(int countB2000) {
            ATM.this.setCountBanknot(Banknotes.B2000, countB2000);
            return this;
        }

        public Builder setCountB5000(int countB5000) {
            ATM.this.setCountBanknot(Banknotes.B5000, countB5000);
            return this;
        }

        public ATM build() {
            return ATM.this;
        }
    }
}
