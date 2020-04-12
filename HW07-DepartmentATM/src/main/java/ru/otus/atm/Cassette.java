package ru.otus.atm;

public class Cassette {
    private static final int SIZEINIT = 200;

    private final int size;
    public int count;

    public Cassette(final int size) {
        this.count = 0;
        if (size < 1) {
            this.size = SIZEINIT;
            return;
        }
        this.size = size;
    }

    public Cassette() {
        this(SIZEINIT);
    }

    public int getSize() {
        return size;
    }

    public int getCount() {
        return count;
    }

    public boolean setCount(final int count) {
        if(count < 0 || count > size) return false;
        this.count = count;
        return true;
    }

    public boolean addCount(final int count) {
        if(count < 0) return false;
        final int newCount = this.count + count;
        if(newCount > size) return false;
        this.count = newCount;
        return true;
    }
}
