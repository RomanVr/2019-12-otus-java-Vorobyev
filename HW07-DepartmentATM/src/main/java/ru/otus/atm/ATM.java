package ru.otus.atm;

import ru.otus.department.Listener;

import java.util.Map;

public interface ATM {
    public Map<BanknoteEnum, Cassette> getCassetteMap();
    public boolean setCountBanknote(BanknoteEnum banknot, int count);
    public int getCountBanknot(BanknoteEnum banknot);
    public Listener getListener();

}
