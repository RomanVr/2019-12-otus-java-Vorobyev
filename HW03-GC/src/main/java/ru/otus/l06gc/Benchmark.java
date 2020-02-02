package ru.otus.l06gc;

import java.util.LinkedList;
import java.util.List;

public class Benchmark implements BenchmarkMBean {
    private final int loopCounter;
    private volatile int size = 0;
    private List linkedList;

    public Benchmark(final int loopCounter) {
        this.loopCounter = loopCounter;
    }

    void run() throws InterruptedException {
        this.linkedList = new LinkedList();
        for(int idx = 0; idx < this.loopCounter; idx += 1) {
            int local = this.size;
            Object[] array = new Object[local];
            for(int i = 0; i < local; i += 1) {
                array[i] = new String(new char[0]);
            }
            linkedList.add(array);
            Thread.sleep(10);
            if(idx % 10 == 9) {
                for(int i = 0; i < 5; i += 1) {
                    linkedList.remove(i);
                }
            }
        }
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public void setSize(final int size) {
        System.out.println("new size: " + size);
        this.size = size;
    }
}
