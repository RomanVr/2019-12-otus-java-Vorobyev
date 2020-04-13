package ru.otus.department;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.atm.ATM;
import ru.otus.atm.AtmImpl;
import ru.otus.atm.BanknoteEnum;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {
    private ATM atm;
    private ATM atm1;
    private Department department;

    private void addAtms() {
        assertTrue(department.addAtm(this.atm));
        assertTrue(department.addAtm(this.atm1));
    }

    @BeforeEach
    void setAtms() {
        this.department = new Department(new AtmServiceImpl());
        this.atm = AtmImpl.newBuilder()
                .setCountB100(2)
                .setCountB200(2)
                .setCountB500(1)
                .setCountB1000(3)
                .setCountB2000(1)
                .setCountB5000(1)
                .build();
        this.atm1 = AtmImpl.newBuilder()
                .setCountB100(3)
                .setCountB200(2)
                .setCountB500(1)
                .setCountB1000(3)
                .setCountB2000(1)
                .setCountB5000(1)
                .build();
    }

    @Test
    void addAtm() {
        assertTrue(this.department.addAtm(this.atm));
        assertTrue(this.department.addAtm(this.atm1));
    }

    @Test
    void remove() {
        this.addAtms();
        assertTrue(this.department.remove(this.atm1));
        assertTrue(this.department.remove(this.atm));
    }

    @Test
    void getAllBalance() {
        this.addAtms();
        int expectBalance = 22300;
        assertEquals(expectBalance, this.department.getAllBalance());
    }

    @Test
    void toReset() {
        this.addAtms();
        int balanceIntermediate = 21800;
        this.atm.setCountBanknote(BanknoteEnum.B100, 0);
        this.atm1.setCountBanknote(BanknoteEnum.B100, 0);
        assertEquals(balanceIntermediate, this.department.getAllBalance());

        int expectBalance = 22300;
        this.department.toReset();
        assertEquals(expectBalance, this.department.getAllBalance());
    }
}