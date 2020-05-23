package ru.otus;

public class SequenceCount {
    private final String nameTread1 = "Поток 1";
    private final String nameTread2 = "Поток 2";

    volatile int i = 1;
    boolean seq = true;
    final int finishCount = 100;
    int count = 1;

    volatile boolean isPrinted = false;

    public static void main(String[] args) {
        SequenceCount sq = new SequenceCount();
        sq.go();
    }

    void increment1() {
        while(count < finishCount) {
            if (!isPrinted) {
                System.out.println(Thread.currentThread().getName() + " : " + i);
                isPrinted = true;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void increment2 () {
        final int mod = 9;
        while(count < finishCount) {
            if (isPrinted) {
                System.out.println(Thread.currentThread().getName() + " : " + i);
                if (seq) {
                    ++i;
                    if (i > mod) seq = false;
                } else {
                    --i;
                    if (i < 2) seq = true;
                }
                count++;
                isPrinted = false;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void go() {
        Thread tr1 = new Thread(this::increment1, nameTread1);
        Thread tr2 = new Thread(this::increment2, nameTread2);

        tr1.start();
        tr2.start();
    }
}
