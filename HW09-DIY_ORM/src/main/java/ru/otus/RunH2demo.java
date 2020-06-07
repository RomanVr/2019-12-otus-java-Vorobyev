package ru.otus;

import ru.otus.h2.H2demo;

import java.sql.SQLException;

public class RunH2demo {
    public static void main(String[] args) throws SQLException {
        System.out.println("Hello Word!");

        H2demo h2 = new H2demo();
        h2.createTable();
        int id = 1;
        String nameValue = "NameValue";
        h2.insertRecord(id, nameValue);
        h2.selectRecord(id);
        h2.close();
    }
}
