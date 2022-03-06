package service;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryTaskManager implements TaskManager {
    private final Map<Long, Task> tasks = new HashMap<>();
    private final Map<Long, Epic> epics = new HashMap<>();
    private final Map<Long, Subtask> subtasks = new HashMap<>();
    InMemoryHistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();

    @Override
    public Map<Long, Task> getTasks() {
        return tasks;
    }

    @Override
    public Map<Long, Epic> getEpics() {
        return epics;
    }

    @Override
    public Map<Long, Subtask> getSubtasks() {
        return subtasks;
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
    }

    @Override
    public Task getTaskById(long id) {
        if (tasks.containsKey(id)) {
            inMemoryHistoryManager.add(tasks.get(id));
            return tasks.get(id);
        }
        return null;
    }

    @Override
    public Task getEpicById(long id) {
        if (epics.containsKey(id)) {
            inMemoryHistoryManager.add(epics.get(id));
            return epics.get(id);
        }
        return null;
    }

    @Override
    public Task getSubtaskById(long id) {
        if (subtasks.containsKey(id)) {
            inMemoryHistoryManager.add(subtasks.get(id));
            return subtasks.get(id);
        }
        return null;
    }

    @Override
    public void saveTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void saveEpic(Epic epic) {
        epics.put(epic.getId(), epic);
        epic.setStatus(Status.NEW);
    }

    @Override
    public void saveSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        updateEpicStatus(subtask.getEpicId());
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
        updateEpicStatus(epic.getId());
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        updateEpicStatus(subtask.getEpicId());
    }

    @Override
    public void updateEpicStatus(long epicId) {
        List<Subtask> epicSubtasks = subtasks.values()
                .stream().filter(task -> task.getEpicId() == epicId).collect(Collectors.toList());
        for (Subtask epicSubtask : epicSubtasks) {
            if (epicSubtask.getStatus().equals(epicSubtasks.get(0).getStatus())) {
                if (epicSubtask.getStatus().equals(Status.NEW)) {
                    epics.get(epicSubtask.getEpicId()).setStatus(Status.NEW);
                } else {
                    epics.get(epicSubtask.getEpicId()).setStatus(Status.DONE);
                }
            } else {
                epics.get(epicSubtask.getEpicId()).setStatus(Status.IN_PROGRESS);
            }
        }
    }

    @Override
    public void deleteTaskById(long idDelete) {
        for (long id : tasks.keySet()) {
            if (id == idDelete) {
                tasks.remove(id);
                return;
            }
        }
        for (long id : epics.keySet()) {
            if (id == idDelete) {
                for (Subtask subtask : subtasks.values()) {
                    if (epics.get(id).getId() == subtask.getEpicId()) {
                        subtasks.remove(subtask.getId());
                    }
                }
                epics.remove(id);
                return;
            }
        }
        for (long id : subtasks.keySet()) {
            if (id == idDelete) {
                subtasks.remove(id);
                return;
            }
        }
    }

    @Override
    public List<Subtask> getEpicSubtask(long epicId) {
        List<Subtask> listEpicSubtask = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpicId() == epicId) {
                listEpicSubtask.add(subtask);
            }
        }
        return listEpicSubtask;
    }

    @Override
    public List<Task> history() {
        return inMemoryHistoryManager.getHistory();
    }

    @Override
    public String toString() {
        return tasks.values() + "\n" + epics.values() + "\n" + subtasks.values();
    }
}