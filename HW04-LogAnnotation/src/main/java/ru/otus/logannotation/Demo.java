package ru.otus.logannotation;

public class Demo {
    public static void main(String[] args) {
        ILogger testLogger = IoCLogger.createLog();
        testLogger.calculation(6);
        testLogger.doSmth("testNAME");
    }
}
