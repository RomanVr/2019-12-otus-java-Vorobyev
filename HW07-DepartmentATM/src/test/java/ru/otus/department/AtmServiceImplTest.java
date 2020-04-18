package ru.otus.department;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.otus.atm.ATM;
import ru.otus.atm.AtmImpl;
import ru.otus.atm.BanknoteEnum;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class AtmServiceImplTest {
    private AtmService atmService;
    private ATM atm;

    @BeforeEach
    void setAtm() {
        try {
            this.atm = AtmImpl.newBuilder()
                    .setBanknote(BanknoteEnum.B100, 2)
                    .setBanknote(BanknoteEnum.B200 ,4)
                    .setBanknote(BanknoteEnum.B500 ,6)
                    .setBanknote(BanknoteEnum.B1000 ,3)
                    .setBanknote(BanknoteEnum.B2000 ,6)
                    .setBanknote(BanknoteEnum.B5000 ,10)
                    .build();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    @BeforeEach
    void setAtmService() {
        this.atmService = AtmServiceImpl.getInstance();
    }

    @Test
    void getBalance() {
        final int expectedAmount = 69000;
        assertEquals(expectedAmount, atmService.getBalance(this.atm));
        ATM atmEmpty = null;
        try {
            atmEmpty = AtmImpl.newBuilder()
                    .setBanknote(BanknoteEnum.B100, 0)
                    .setBanknote(BanknoteEnum.B200 ,0)
                    .setBanknote(BanknoteEnum.B500 ,0)
                    .setBanknote(BanknoteEnum.B1000 ,0)
                    .setBanknote(BanknoteEnum.B2000 ,0)
                    .setBanknote(BanknoteEnum.B5000 ,0)
                    .build();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

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
