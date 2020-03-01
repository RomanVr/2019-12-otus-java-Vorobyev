package ru.otus.atmemulator;

import java.util.Map;

public interface ATM_Interface {
    public Map<Banknote, Integer> getBanknotes();
    public void setCountBanknote(Banknote banknot, int count);
    public int getCountBanknot(Banknote banknot);
}
