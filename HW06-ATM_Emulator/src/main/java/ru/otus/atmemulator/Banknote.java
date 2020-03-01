package ru.otus.atmemulator;

public enum Banknote {
    B100(100),
    B200(200),
    B500(500),
    B1000(1000),
    B2000(2000),
    B5000(5000);

    int value;

    Banknote(int value) {
        this.value = value;
    }
}
