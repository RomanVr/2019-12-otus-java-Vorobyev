
package ru.otus.atmemulator;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class ATM implements ATM_Interface{
    private final Map<Banknote, Integer> banknotes;

    private ATM(){
        this.banknotes = new TreeMap<>(Collections.reverseOrder());
    }

    @Override
    public int getCountBanknot(Banknote banknot) {
        return this.banknotes.getOrDefault(banknot, 0);
    }

    @Override
    public void setCountBanknote(Banknote banknot, int count) {
        if(count < 0) return;
        this.banknotes.put(banknot, count);
    }

    @Override
    public Map<Banknote, Integer> getBanknotes() {
        Map<Banknote, Integer> newBanknotes = new TreeMap<Banknote, Integer>(Collections.reverseOrder());
        newBanknotes.putAll(this.banknotes);
        return newBanknotes;
    };

    public static Builder newBuilder() {
        return new ATM().new Builder();
    }

    public class Builder {
        private Builder() {}

        public Builder setCountB100(int countB100) {
            ATM.this.setCountBanknote(Banknote.B100, countB100);
            return this;
        }

        public Builder setCountB200(int countB200) {
            ATM.this.setCountBanknote(Banknote.B200, countB200);
            return this;
        }

        public Builder setCountB500(int countB500) {
            ATM.this.setCountBanknote(Banknote.B500, countB500);
            return this;
        }

        public Builder setCountB1000(int countB1000) {
            ATM.this.setCountBanknote(Banknote.B1000, countB1000);
            return this;
        }

        public Builder setCountB2000(int countB2000) {
            ATM.this.setCountBanknote(Banknote.B2000, countB2000);
            return this;
        }

        public Builder setCountB5000(int countB5000) {
            ATM.this.setCountBanknote(Banknote.B5000, countB5000);
            return this;
        }

        public ATM build() {
            return ATM.this;
        }
    }
}
