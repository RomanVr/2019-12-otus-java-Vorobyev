
package ru.otus.atmemulator;

import java.util.*;

public class AtmImpl implements ATM {
    private final Map<BanknoteEnum, Cassette> cassetteMap;

    private AtmImpl(){
        this.cassetteMap = new TreeMap<BanknoteEnum, Cassette>(Collections.reverseOrder());
    }

    private Cassette getCassette(BanknoteEnum banknote) {
        return this.cassetteMap.getOrDefault(banknote, null);
    }

    @Override
    public Map<BanknoteEnum, Cassette> getCassetteMap() {
        Map<BanknoteEnum, Cassette> newCassetteMap = new TreeMap<BanknoteEnum, Cassette>(Collections.reverseOrder());
        newCassetteMap.putAll(this.cassetteMap);
        return newCassetteMap;
    };

    @Override
    public int getCountBanknot(BanknoteEnum banknote) {
        Cassette currentCassette = getCassette(banknote);
        if(currentCassette == null) return 0;
        return currentCassette.getCount();
    }

    @Override
    public boolean setCountBanknote(BanknoteEnum banknote, int count) {
        Cassette currentCassette = getCassette(banknote);
        if(currentCassette == null) return false;
        return currentCassette.setCount(count);
    }

    public static Builder newBuilder() {
        return new AtmImpl().new Builder();
    }

    public class Builder {
        private Builder() {}

        public Builder setCountB100(int countB100) {
            Cassette currentCassette = new Cassette();
            AtmImpl.this.cassetteMap.put(BanknoteEnum.B100, currentCassette);
            AtmImpl.this.setCountBanknote(BanknoteEnum.B100, countB100);
            return this;
        }

        public Builder setCountB200(int countB200) {
            Cassette currentCassette = new Cassette();
            AtmImpl.this.cassetteMap.put(BanknoteEnum.B200, currentCassette);
            AtmImpl.this.setCountBanknote(BanknoteEnum.B200, countB200);
            return this;
        }

        public Builder setCountB500(int countB500) {
            Cassette currentCassette = new Cassette();
            AtmImpl.this.cassetteMap.put(BanknoteEnum.B500, currentCassette);
            AtmImpl.this.setCountBanknote(BanknoteEnum.B500, countB500);
            return this;
        }

        public Builder setCountB1000(int countB1000) {
            Cassette currentCassette = new Cassette();
            AtmImpl.this.cassetteMap.put(BanknoteEnum.B1000, currentCassette);
            AtmImpl.this.setCountBanknote(BanknoteEnum.B1000, countB1000);
            return this;
        }

        public Builder setCountB2000(int countB2000) {
            Cassette currentCassette = new Cassette();
            AtmImpl.this.cassetteMap.put(BanknoteEnum.B2000, currentCassette);
            AtmImpl.this.setCountBanknote(BanknoteEnum.B2000, countB2000);
            return this;
        }

        public Builder setCountB5000(int countB5000) {
            Cassette currentCassette = new Cassette();
            AtmImpl.this.cassetteMap.put(BanknoteEnum.B5000, currentCassette);
            AtmImpl.this.setCountBanknote(BanknoteEnum.B5000, countB5000);
            return this;
        }

        public AtmImpl build() {
            return AtmImpl.this;
        }
    }
}
