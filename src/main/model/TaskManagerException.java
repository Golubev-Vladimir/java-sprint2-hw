package main.model;

public class TaskManagerException extends Exception {


    public TaskManagerException(String message) {
        super(message);
    }

    public String getDetailMessage() {
        return getMessage();
    }
}