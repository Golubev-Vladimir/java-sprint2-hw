package main.service;

public class Counter {
    private static long i;

    private Counter() {}

    public static long generateId() {
        i++;
        return i;
    }
}