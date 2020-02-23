package ru.otus.logannotation;

public class TestLogging implements ILogger {
    @Log
    public void calculation(int param) {};

    public void doSmth(String name){};
}
