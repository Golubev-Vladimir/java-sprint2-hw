package service;

import java.io.IOException;

public class Managers {
    private Managers() {}

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static TaskManager getSaveFile(String pathFile) throws IOException {
        return new FileBackedTasksManager(pathFile);
    }

    public static InMemoryHistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}