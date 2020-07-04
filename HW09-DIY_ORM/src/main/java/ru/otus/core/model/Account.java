package ru.otus.core.model;

import ru.otus.jdbc.Id;

public class Account {
    @Id
    private final long no;
    private final int rest;
    private final String type;

    public Account(long no, int rest, String type) {
        this.no = no;
        this.rest = rest;
        this.type = type;
    }

    public long getNo() {
        return no;
    }

    public int getRest() {
        return rest;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Account {" +
                "no=" + no +
                ", rest=" + rest +
                ", type=" + type +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Account that = (Account) o;
        return this.rest == that.rest &&
                this.type == that.type;
    }
}
