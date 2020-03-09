package ru.otus.atmemulator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AtmImplTest {
    private ATM atm;
    private ATMService atmService;

    @BeforeEach
    void setAtm() {
        this.atm = AtmImpl.newBuilder()
               .setCountB100(2)
               .setCountB200(4)
               .setCountB500(6)
               .setCountB1000(3)
               .setCountB2000(6)
               .setCountB5000(10)
               .build();
        this.atmService = new ATMService(this.atm);
    }

    @Test
    void getBalance() {
        final int expectedAmount = 69000;
        assertEquals(expectedAmount, this.atmService.getBalance());
        ATM atmEmpty = AtmImpl.newBuilder()
                .setCountB100(0)
                .setCountB200(0)
                .setCountB500(0)
                .setCountB1000(0)
                .setCountB2000(0)
                .setCountB5000(0)
                .build();
        ATMService atmServiceEmpty = new ATMService(atmEmpty);
        assertEquals(0, atmServiceEmpty.getBalance());
    }

    @ParameterizedTest
    @CsvSource({
            "68900, 100",
            "0, 69000",
            "43400, 25689"
    })
    void getCash(int expectedRestBalance, int cash) {
        this.atmService.getCash(cash);
        int actualRestAmount = this.atmService.getBalance();
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
    void getCountBanknot(BanknoteEnum banknot, int count) {
        assertEquals(this.atm.getCountBanknot(banknot), count);
    }

    @ParameterizedTest
    @CsvSource({
            "B100, 10, 70000",
            "B5000, 1, 74000"
    })
    void addCountBanknot(BanknoteEnum banknot, int count, int amountExpected) {
        this.atmService.addCountBanknot(banknot, count);
        int amountActual = this.atmService.getBalance();
        assertEquals(amountExpected, amountActual);
    }
}