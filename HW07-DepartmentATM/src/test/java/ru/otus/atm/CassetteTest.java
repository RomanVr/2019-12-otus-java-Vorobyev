package ru.otus.atm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CassetteTest {

    @Test
    void getCount() {
        int size = 10;
        int expectedCount = 10;
        Cassette cassette = new CassetteImpl(size);
        cassette.setCount(expectedCount);
        assertEquals(expectedCount, cassette.getCount());
    }

    @ParameterizedTest
    @CsvSource({
            "-1",
            "300",
            "20"
    })
    void getCountWrong(int countWrong) {
        int size = 10;
        int expectedCount = 0;
        Cassette cassette = new CassetteImpl(size);
        cassette.setCount(countWrong);
        assertEquals(expectedCount, cassette.getCount());
    }

    @ParameterizedTest
    @CsvSource({
            "-1",
            "300",
            "20"
    })
    void setCountWrong(int countWrong) {
        int size = 10;
        Cassette cassette = new CassetteImpl(size);
        assertFalse(cassette.setCount(countWrong));
    }

    @Test
    void setCount() {
        int size = 10;
        int count = 10;
        Cassette cassette = new CassetteImpl(size);
        assertTrue(cassette.setCount(count));
    }

    @ParameterizedTest
    @CsvSource({
            "-1",
            "300",
            "11"
    })
    void addCountWrong(int countWrong) {
        int size = 10;
        Cassette cassette = new CassetteImpl(size);
        assertFalse(cassette.addCount(countWrong));
    }

    @Test
    void addCount() {
        int size = 10;
        int count = 10;
        Cassette cassette = new CassetteImpl(size);
        assertTrue(cassette.addCount(count));
    }
}
