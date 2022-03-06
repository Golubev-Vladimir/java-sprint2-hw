package service;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static final List<Task> historyViewedTasks = new ArrayList<>();

    @Override
    public void add(Task task) {
        for (Task task1 : historyViewedTasks) {
            if (task1.equals(task)) {
                historyViewedTasks.remove(task1);
                break;
            }
        }
        historyViewedTasks.add(task);
        if (historyViewedTasks.size() > 10) {
            historyViewedTasks.remove(0);
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyViewedTasks;
    }
}