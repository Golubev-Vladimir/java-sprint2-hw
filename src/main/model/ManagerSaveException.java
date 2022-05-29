package main.model;

public class ManagerSaveException extends Exception {
    private final String pathFile;

    public ManagerSaveException(final String message, final String pathFile) {
        super(message);
        this.pathFile = pathFile;
    }

    public String getDetailMessage() {
        return getMessage() + pathFile;
    }
}