package ru.otus.core.service;

public class DBServiceExeption extends RuntimeException {
    public DBServiceExeption(Exception message) {
        super(message);
    }
}
