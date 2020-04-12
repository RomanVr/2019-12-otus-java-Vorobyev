package ru.otus.atm;

public enum BanknoteEnum {
    B100(100),
    B200(200),
    B500(500),
    B1000(1000),
    B2000(2000),
    B5000(5000),
    B10000(10000);

    public int value;

    BanknoteEnum(int value) {
        this.value = value;
    }
}
