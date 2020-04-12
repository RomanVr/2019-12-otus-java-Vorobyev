package ru.otus.atm;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class AtmImpl implements ATM {
    private final Map<BanknoteEnum, Cassette> cassetteMap;
    private final Map<BanknoteEnum, Integer> banknotesBegin;

    private AtmImpl(){
        this.banknotesBegin = null;
        this.cassetteMap = null;
    }

    private AtmImpl(Map<BanknoteEnum, Integer> banknotesBegin) {
        this.cassetteMap = new TreeMap<BanknoteEnum, Cassette>(Collections.reverseOrder());
        this.banknotesBegin = banknotesBegin;
        setBanknotesDefault();
    }

    private void setBanknotesDefault() {
        this.banknotesBegin.forEach((banknote, count) -> {
            Cassette currentCassette = new Cassette();
            currentCassette.setCount(count);
            cassetteMap.put(banknote, currentCassette);
        });
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

    private final SetToDefault listener = this::setBanknotesDefault;

    @Override
    public SetToDefault getListener() {
        return listener;
    }

    public static Builder newBuilder() {
        return new AtmImpl().new Builder();
    }

    public static AtmImpl newBuilderDefault() {
        return new AtmImpl().new Builder().build();
    }

    public class Builder {
        private final Map<BanknoteEnum, Integer> banknotesBegin;

        private Map<BanknoteEnum, Integer> initDefault() {
            Map<BanknoteEnum, Integer> banknotesDefault = new TreeMap<>();
            banknotesDefault.put(BanknoteEnum.B100, 0);
            banknotesDefault.put(BanknoteEnum.B200, 0);
            banknotesDefault.put(BanknoteEnum.B500, 0);
            banknotesDefault.put(BanknoteEnum.B1000, 0);
            banknotesDefault.put(BanknoteEnum.B2000, 0);
            banknotesDefault.put(BanknoteEnum.B5000, 0);
            return banknotesDefault;
        }

        private Builder() {
            this.banknotesBegin = this.initDefault();
        }

        public Builder setCountB100(int countB100) {
            this.banknotesBegin.put(BanknoteEnum.B100, countB100);
            return this;
        }

        public Builder setCountB200(int countB200) {
            this.banknotesBegin.put(BanknoteEnum.B200, countB200);
            return this;
        }

        public Builder setCountB500(int countB500) {
            this.banknotesBegin.put(BanknoteEnum.B500, countB500);
            return this;
        }

        public Builder setCountB1000(int countB1000) {
            this.banknotesBegin.put(BanknoteEnum.B1000, countB1000);
            return this;
        }

        public Builder setCountB2000(int countB2000) {
            this.banknotesBegin.put(BanknoteEnum.B2000, countB2000);
            return this;
        }

        public Builder setCountB5000(int countB5000) {
            this.banknotesBegin.put(BanknoteEnum.B5000, countB5000);
            return this;
        }

        public AtmImpl build() {
            return new AtmImpl(this.banknotesBegin);
        }
    }
}
