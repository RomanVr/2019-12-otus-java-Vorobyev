package ru.otus.atmemulator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AtmImplTest {
    private ATM atm;

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
    }

    @Test
    void getBalance() {
        final int expectedAmount = 69000;
        assertEquals(expectedAmount, ATMService.getBalance(this.atm));
        ATM atmEmpty = AtmImpl.newBuilder()
                .setCountB100(0)
                .setCountB200(0)
                .setCountB500(0)
                .setCountB1000(0)
                .setCountB2000(0)
                .setCountB5000(0)
                .build();

        assertEquals(0, ATMService.getBalance(atmEmpty));
    }

    @ParameterizedTest
    @CsvSource({
            "68900, 100",
            "0, 69000",
            "43400, 25689"
    })
    void getCash(int expectedRestBalance, int cash) {
        ATMService.getCash(this.atm, cash);
        int actualRestAmount = ATMService.getBalance(this.atm);
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
}
