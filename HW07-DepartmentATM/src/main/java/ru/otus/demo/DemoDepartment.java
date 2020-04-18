package ru.otus.demo;

import ru.otus.atm.ATM;
import ru.otus.atm.AtmImpl;
import ru.otus.atm.BanknoteEnum;
import ru.otus.department.AtmService;
import ru.otus.department.AtmServiceImpl;
import ru.otus.department.Department;

import java.lang.reflect.InvocationTargetException;

public class DemoDepartment {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ATM atm = AtmImpl.newBuilder()
                .setCountB100(2)
                .setCountB200(2)
                .setCountB500(1)
                .setCountB1000(3)
                .setCountB2000(1)
                .setCountB5000(1)
                .build();
        ATM atm1 = AtmImpl.newBuilder()
                .setBanknote(BanknoteEnum.B100, 3)
                .setBanknote(BanknoteEnum.B200 ,2)
                .setBanknote(BanknoteEnum.B500 ,1)
                .setBanknote(BanknoteEnum.B1000 ,3)
                .setBanknote(BanknoteEnum.B2000 ,1)
                .setBanknote(BanknoteEnum.B5000 ,1)
                .build();
        ATM atm2 = ((AtmImpl) atm1).clone();

        AtmService atmService = AtmServiceImpl.getInstance();

        atm.setCountBanknote(BanknoteEnum.B100, 0);

        System.out.println("Balance: " + atmService.getBalance(atm));

        Department department = new Department(atmService);
        department.addAtm(atm);
        System.out.println("Balance: " + department.getAllBalance());
        department.addAtm(atm1);
        System.out.println("Balance: " + department.getAllBalance());
        department.addAtm(atm2);

        System.out.println("Balance: " + department.getAllBalance());

        department.toReset();

        System.out.println("Balance: " + department.getAllBalance());
    }
}
