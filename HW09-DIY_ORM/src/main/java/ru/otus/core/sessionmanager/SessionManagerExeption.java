package ru.otus.core.sessionmanager;

public class SessionManagerExeption extends RuntimeException {
    public SessionManagerExeption(String s) {
        super(s);
    }

    public SessionManagerExeption(Exception ex) {
        super(ex);
    }
}
