package main.service;

public class Managers {
    private Managers() {
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static FileBackedTasksManager getFileBackedTasksManager(String pathFile) {
        return new FileBackedTasksManager(pathFile);
    }

    public static InMemoryHistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}