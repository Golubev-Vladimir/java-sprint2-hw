package service;

public class Counter {
    private static long id;

    private Counter() {}

    public static long generateId() {
        id++;
        return id;
    }
}