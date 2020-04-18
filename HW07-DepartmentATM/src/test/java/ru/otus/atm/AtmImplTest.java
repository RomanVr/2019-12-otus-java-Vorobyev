package ru.otus.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class AtmImplTest {
    private ATM atm;

    @BeforeEach
    void setAtm() {
        try {
            this.atm = AtmImpl.newBuilder()
                    .setBanknote(BanknoteEnum.B100, 2)
                    .setBanknote(BanknoteEnum.B200 ,2)
                    .setBanknote(BanknoteEnum.B500 ,1)
                    .setBanknote(BanknoteEnum.B1000 ,3)
                    .setBanknote(BanknoteEnum.B2000 ,1)
                    .setBanknote(BanknoteEnum.B5000 ,1)
                    .build();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @CsvSource({
            "B100",
            "B200",
            "B500",
            "B1000",
            "B2000",
            "B5000"
    })
    void getCassetteMap(BanknoteEnum banknote) {
        assertNotNull(this.atm.getCassette(banknote));
    }

    @Test
    void getCassetteMapToNull() {
        assertNull(this.atm.getCassette(BanknoteEnum.B10000));
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
            "B100, 1, true",
            "B200, 0, true",
            "B1000, 3, true",
            "B2000, 6, true"
    })
    void setCountBanknoteToResultTrue(BanknoteEnum banknot, int count, String resultToSet) {
        assertTrue(this.atm.setCountBanknote(banknot, count), resultToSet);
    }

    @ParameterizedTest
    @CsvSource({
            "B10000, 6, false",
            "B5000, -1, false"
    })
    void setCountBanknoteToResultFalse(BanknoteEnum banknot, int count, String resultToSet) {
        assertFalse(this.atm.setCountBanknote(banknot, count), resultToSet);
    }
}