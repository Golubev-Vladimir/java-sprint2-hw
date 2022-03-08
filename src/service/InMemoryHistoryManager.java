package service;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int HISTORY_MAX_SIZE = 10;
    private final List<Task> lastViewedTasks = new ArrayList<>();

    @Override
    public void add(Task newTask) {
        for (Task task : lastViewedTasks) {
            if (task.equals(newTask)) {
                lastViewedTasks.remove(task);
                break;
            }
        }
        lastViewedTasks.add(newTask);
        if (lastViewedTasks.size() > HISTORY_MAX_SIZE) {
            lastViewedTasks.remove(0);
        }
    }

    @Override
    public List<Task> getHistory() {
        return lastViewedTasks;
    }
}