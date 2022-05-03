package service;

import model.Epic;
import model.StatusTask;
import model.Subtask;
import model.Task;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTaskManager implements TaskManager {
    private final Map<Long, Task> tasks = new HashMap<>();
    private final Map<Long, Epic> epics = new HashMap<>();
    private final Map<Long, Subtask> subtasks = new HashMap<>();
    private final InMemoryHistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();

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
    public Optional<Task> getTaskById(long id) {
        Optional<Task> taskOptional = Optional.ofNullable(tasks.get(id));
        taskOptional.ifPresentOrElse(inMemoryHistoryManager::add,
                () -> System.out.println("Такого элемента c id = " + id + " в коллекции Tasks нет"));
        return taskOptional;
    }

    @Override
    public Optional<Epic> getEpicById(long id) {
        Optional<Epic> epicOptional = Optional.ofNullable(epics.get(id));
        epicOptional.ifPresentOrElse(inMemoryHistoryManager::add,
                () -> System.out.println("Такого элемента c id = " + id + " в коллекции Epic нет"));
        return epicOptional;
    }

    @Override
    public Optional<Subtask> getSubtaskById(long id) {
        Optional<Subtask> subtaskOptional = Optional.ofNullable(subtasks.get(id));
        subtaskOptional.ifPresentOrElse(inMemoryHistoryManager::add,
                () -> System.out.println("Такого элемента c id = " + id + " в коллекции Subtask нет"));
        return subtaskOptional;
    }

    @Override
    public void saveTask(Task task) {
        tasks.put(task.getTaskId(), task);
    }

    @Override
    public void saveEpic(Epic epic) {
        epics.put(epic.getTaskId(), epic);
        epic.setTaskStatus(StatusTask.NEW);
    }

    @Override
    public void saveSubtask(Subtask subtask) {
        if (epics.containsKey(subtask.getTaskEpicId())) {
            subtasks.put(subtask.getTaskId(), subtask);
            updateEpicStatus(subtask.getTaskEpicId());
        }
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getTaskId())) {
            tasks.put(task.getTaskId(), task);
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getTaskId())) {
            epics.put(epic.getTaskId(), epic);
            updateEpicStatus(epic.getTaskId());
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getTaskId())) {
            subtasks.put(subtask.getTaskId(), subtask);
            updateEpicStatus(subtask.getTaskEpicId());
        }
    }

    public void updateEpicStatus(long epicId) {
        List<Subtask> epicSubtasks = subtasks.values()
                .stream().filter(task -> task.getTaskEpicId() == epicId).collect(Collectors.toList());
        for (Subtask epicSubtask : epicSubtasks) {
            if (epicSubtask.getTaskStatus().equals(epicSubtasks.get(0).getTaskStatus())) {
                if (epicSubtask.getTaskStatus().equals(StatusTask.NEW)) {
                    epics.get(epicSubtask.getTaskEpicId()).setTaskStatus(StatusTask.NEW);
                } else {
                    epics.get(epicSubtask.getTaskEpicId()).setTaskStatus(StatusTask.DONE);
                }
            } else {
                epics.get(epicSubtask.getTaskEpicId()).setTaskStatus(StatusTask.IN_PROGRESS);
            }
        }
    }

    @Override
    public void deleteTaskById(long idDelete) {
        tasks.keySet().removeIf(id -> id == idDelete);
    }

    @Override
    public void deleteEpicById(long idDelete) {
        epics.keySet().removeIf(id -> id == idDelete);
        subtasks.values().removeIf(task -> task.getTaskEpicId() == idDelete);
    }

    @Override
    public void deleteSubtaskById(long idDelete) {
        long epicId = subtasks.get(idDelete).getTaskEpicId();
        subtasks.keySet().removeIf(id -> id == idDelete);
        if (epics.containsKey(epicId)) {
            updateEpicStatus(epicId);
        }
    }

    @Override
    public List<Subtask> getEpicSubtask(long epicId) {
        List<Subtask> listEpicSubtask = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getTaskEpicId() == epicId) {
                listEpicSubtask.add(subtask);
            }
        }
        return listEpicSubtask;
    }

    @Override
    public List<Task> history() {
        return inMemoryHistoryManager.getHistory();
    }
}