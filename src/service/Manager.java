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

public class Manager {
    private final Map<Long, Task> tasks = new HashMap<>();
    private final Map<Long, Epic> epics = new HashMap<>();
    private final Map<Long, Subtask> subtasks = new HashMap<>();

    public Map<Long, Task> getTasks() {
        return tasks;
    }

    public Map<Long, Epic> getEpics() {
        return epics;
    }

    public Map<Long, Subtask> getSubtasks() {
        return subtasks;
    }

    public void deleteAllTasks() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
    }

    public Task getTaskById(long id) {
        return tasks.get(id);
    }

    public Task getEpicById(long id) {
        return epics.get(id);
    }

    public Task getSubtaskById(long id) {
        return subtasks.get(id);
    }

    public void saveTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void saveEpic(Epic epic) {
        epics.put(epic.getId(), epic);
        epic.setStatus(Status.NEW);
    }

    public void saveSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        updateEpicStatus(subtask.getEpicId());
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
        updateEpicStatus(epic.getId());
    }

    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        updateEpicStatus(subtask.getEpicId());
    }

    private void updateEpicStatus(long epicId) {
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
    public String toString() {
        return tasks.values() + "\n" + epics.values() + "\n" + subtasks.values();
    }
}