package ru.otus.atm;

public class Cassette {
    private static final int SIZEINIT = 200;

    private final int size;
    public int count;

    private Cassette(final int size) {
        this.size = size;
    }

    public Cassette() {
        this(SIZEINIT);
    }

    public int getCount() {
        return count;
    }

    public boolean setCount(final int count) {
        if(count < 0 || count > size) return false;
        this.count = count;
        return true;
    }

    public void addCount(final int count) {
        if(count < 0) return;
        final int newCount = this.count + count;
        if(newCount > size) return;
        this.count = newCount;
    }
}
