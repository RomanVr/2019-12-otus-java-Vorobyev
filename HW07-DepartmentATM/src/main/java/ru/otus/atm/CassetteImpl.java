package ru.otus.atm;

public class CassetteImpl implements Cassette  {
    private static final int SIZEINIT = 200;

    private final int size;
    public int count;

    public CassetteImpl (final int size) {
        this.count = 0;
        if (size < 1) {
            this.size = SIZEINIT;
            return;
        }
        this.size = size;
    }

    public CassetteImpl() {
        this(SIZEINIT);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public boolean setCount(final int count) {
        if(count < 0 || count > size) return false;
        this.count = count;
        return true;
    }

    @Override
    public boolean addCount(final int count) {
        if(count < 0) return false;
        final int newCount = this.count + count;
        if(newCount > size) return false;
        this.count = newCount;
        return true;
    }
}
