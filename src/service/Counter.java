package service;

public class Counter {
    long id;

    public long generateId() {
        id++;
        return id;
    }
}