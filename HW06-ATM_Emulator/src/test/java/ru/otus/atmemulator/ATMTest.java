package ru.otus.atmemulator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ATMTest {
    private ATM atm;

    @BeforeEach
    void setAtm() {
        atm = ATM.newBuilder()
               .setCountB100(2)
               .setCountB200(4)
               .setCountB500(6)
               .setCountB1000(3)
               .setCountB2000(6)
               .setCountB5000(10)
               .build();
    }

    @Test
    void getBalance() {
        final int expectedAmount = 69000;
        assertEquals(expectedAmount, atm.getBalance());
        ATM atmEmpty = ATM.newBuilder()
                .setCountB100(0)
                .setCountB200(0)
                .setCountB500(0)
                .setCountB1000(0)
                .setCountB2000(0)
                .setCountB5000(0)
                .build();
        assertEquals(0, atmEmpty.getBalance());
    }

    @ParameterizedTest
    @CsvSource({
            "68900, 100",
            "0, 69000",
            "43400, 25689"
    })
    void getCash(int expectedRestBalance, int cash) {
        atm.getCash(cash);
        int actualRestAmount = atm.getBalance();
        assertEquals(expectedRestBalance, actualRestAmount);
    }

    @ParameterizedTest
    @CsvSource({
            "B100, 2",
            "B200, 4",
            "B500, 6",
            "B1000, 3",
            "B2000, 6",
            "B5000, 10"
    })
    void getCountBanknot(ATM.Banknotes banknot, int count) {
        assertEquals(atm.getCountBanknot(banknot), count);
    }

    @ParameterizedTest
    @CsvSource({
            "B100, 10, 70000",
            "B5000, 1, 74000"
    })
    void addCountBanknot(ATM.Banknotes banknot, int count, int amountExpected) {
        atm.addCountBanknot(banknot, count);
        int amountActual = atm.getBalance();
        assertEquals(amountExpected, amountActual);
    }
}