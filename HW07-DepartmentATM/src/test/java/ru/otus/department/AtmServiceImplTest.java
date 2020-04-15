package ru.otus.department;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.otus.atm.ATM;
import ru.otus.atm.AtmImpl;

import static org.junit.jupiter.api.Assertions.*;

class AtmServiceImplTest {
    private AtmService atmService;
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
    @BeforeEach
    void setAtmService() {
        this.atmService = AtmServiceImpl.getInstance();
    }

    @Test
    void getBalance() {
        final int expectedAmount = 69000;
        assertEquals(expectedAmount, atmService.getBalance(this.atm));
        ATM atmEmpty = AtmImpl.newBuilder()
                .setCountB100(0)
                .setCountB200(0)
                .setCountB500(0)
                .setCountB1000(0)
                .setCountB2000(0)
                .setCountB5000(0)
                .build();

        assertEquals(0, atmService.getBalance(atmEmpty));
    }

    @ParameterizedTest
    @CsvSource({
            "68900, 100",
            "0, 69000",
            "43400, 25689"
    })
    void getCash(int expectedRestBalance, int cash) {
        atmService.getCash(this.atm, cash);
        int actualRestAmount = atmService.getBalance(this.atm);
        assertEquals(expectedRestBalance, actualRestAmount);
    }
}
