package ru.otus.atm;

public interface Cassette {

    public int getSize();
    public int getCount();
    public boolean setCount(final int count);
    public boolean addCount(final int count);
}
