package service;

import model.Task;
import model.TaskLinkedList;

import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final TaskLinkedList<Task> taskLinkedList = new TaskLinkedList<>();

    @Override
    public void add(Task newTask) {
        if (taskLinkedList.mapFastGet.containsKey(newTask.getId())) {
            remove(newTask.getId());
        }
        taskLinkedList.linkLast(newTask);
        taskLinkedList.mapFastGet.put(newTask.getId(), taskLinkedList.last);
    }

    @Override
    public void remove(long id) {
        taskLinkedList.removeNode(taskLinkedList.mapFastGet.get(id));
    }

    @Override
    public List<Task> getHistory() {
        taskLinkedList.getTasks();
        return taskLinkedList.finalLastViewedTasks;
    }
}