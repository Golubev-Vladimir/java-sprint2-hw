package service;

import model.Task;
import model.TaskLinkedList;

import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    public final TaskLinkedList<Task> taskListHistory = new TaskLinkedList<>();

    @Override
    public void add(Task newTask) {
        if (taskListHistory.mapFastGet.containsKey(newTask.getTaskId())) {
            remove(newTask.getTaskId());
        }
        taskListHistory.linkLast(newTask);
        taskListHistory.mapFastGet.put(newTask.getTaskId(), taskListHistory.last);
    }

    @Override
    public void remove(long id) {
        taskListHistory.removeNode(taskListHistory.mapFastGet.get(id));
    }

    @Override
    public List<Task> getHistory() {
        taskListHistory.getTasks();
        return taskListHistory.finalLastViewedTasks;
    }
}