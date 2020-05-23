package ru.otus.department;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.atm.ATM;
import ru.otus.atm.AtmImpl;
import ru.otus.atm.BanknoteEnum;

import java.lang.reflect.InvocationTargetException;

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
        this.department = new Department(AtmServiceImpl.getInstance());
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
        try {
            this.atm1 = AtmImpl.newBuilder()
                    .setBanknote(BanknoteEnum.B100, 3)
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