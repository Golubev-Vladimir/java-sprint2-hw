package main.service;

import main.model.Task;
import main.model.TaskLinkedList;

import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    public final TaskLinkedList<Task> taskListHistory = new TaskLinkedList<>();

    @Override
    public void add(Task newTask) {
        remove(newTask.getTaskId());
        taskListHistory.linkLast(newTask);
        taskListHistory.mapFastGet.put(newTask.getTaskId(), taskListHistory.last);
    }

    @Override
    public void remove(long id) {
        if (taskListHistory.mapFastGet.containsKey(id)) {
            taskListHistory.removeNode(taskListHistory.mapFastGet.get(id));
        }
    }

    @Override
    public List<Task> getHistory() {
        taskListHistory.getTasks();
        return taskListHistory.finalLastViewedTasks;
    }
}