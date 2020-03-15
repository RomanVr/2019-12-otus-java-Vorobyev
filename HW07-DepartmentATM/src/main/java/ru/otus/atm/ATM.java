package ru.otus.atm;

import java.util.Map;

public interface ATM {
    public Map<BanknoteEnum, Cassette> getCassetteMap();
    public boolean setCountBanknote(BanknoteEnum banknot, int count);
    public int getCountBanknot(BanknoteEnum banknot);

}
