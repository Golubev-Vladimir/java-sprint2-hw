package service;

import model.*;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

import static service.Printer.println;

public class InMemoryTaskManager implements TaskManager {

    static Comparator<Task> comparatorIdTask = (task1, task2) -> (int) (task1.getTaskId() - task2.getTaskId());
    static Comparator<Task> comparatorTimeTask = (task1, task2) -> {
        if (task1.getTaskStarTime().isAfter(task2.getTaskStarTime())) {
            return 1;
        } else {
            return -1;
        }
    };

    protected static Set<Task> allTasks = new TreeSet<>(comparatorTimeTask);
    protected static Map<Long, Task> tasks = new HashMap<>();
    protected static Map<Long, Epic> epics = new HashMap<>();
    protected static Map<Long, Subtask> subtasks = new HashMap<>();
    protected static InMemoryHistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();
    protected static long idLastTask = 0;

    private static final String ERROR_GET_TASK = "Такого элемента c id = ";
    private static final String ERROR_DELETE_TASK = "- Задачи с данной ID в списках не найдено";

    public Set<Task> getAllTasks() {
        return allTasks;
    }

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
    public Optional<Task> getTaskById(long id) {
        Optional<Task> taskOptional = Optional.ofNullable(tasks.get(id));
        taskOptional.ifPresentOrElse(inMemoryHistoryManager::add,
                () -> println(ERROR_GET_TASK + id + " в коллекции Tasks нет"));
        return taskOptional;
    }

    @Override
    public Optional<Epic> getEpicById(long id) {
        Optional<Epic> epicOptional = Optional.ofNullable(epics.get(id));
        epicOptional.ifPresentOrElse(inMemoryHistoryManager::add,
                () -> println(ERROR_GET_TASK + id + " в коллекции Epic нет"));
        return epicOptional;
    }

    @Override
    public Optional<Subtask> getSubtaskById(long id) {
        Optional<Subtask> subtaskOptional = Optional.ofNullable(subtasks.get(id));
        subtaskOptional.ifPresentOrElse(inMemoryHistoryManager::add,
                () -> println(ERROR_GET_TASK + id + " в коллекции Subtask нет"));
        return subtaskOptional;
    }

    @Override
    public Set<Subtask> getEpicSubtask(long epicId) {
        Set<Subtask> listEpicSubtask = new TreeSet<>(comparatorIdTask);
        subtasks.values().stream().filter(subtask -> subtask.getTaskEpicId() == epicId).forEach(listEpicSubtask::add);
        return listEpicSubtask;
    }

    @Override
    public void saveTask(Task task) {
        tasks.put(task.getTaskId(), task);
        allTasks.removeIf(taskDel -> taskDel.getTaskId() == task.getTaskId());
        allTasks.add(task);
    }

    @Override
    public void saveEpic(Epic epic) {
        epics.put(epic.getTaskId(), epic);
        allTasks.removeIf(taskDel -> taskDel.getTaskId() == epic.getTaskId());
        allTasks.add(epic);

    }

    @Override
    public void saveSubtask(Subtask subtask) {
        if (epics.containsKey(subtask.getTaskEpicId())) {
            subtasks.put(subtask.getTaskId(), subtask);
            updateEpicStatus(subtask.getTaskEpicId());
            updateEpicTime(subtask.getTaskEpicId());
        }
        allTasks.removeIf(taskDel -> taskDel.getTaskId() == subtask.getTaskId());
        allTasks.add(subtask);
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getTaskId())) {
            tasks.put(task.getTaskId(), task);
        }
        allTasks.removeIf(taskDel -> taskDel.getTaskId() == task.getTaskId());
        allTasks.add(task);
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getTaskId())) {
            epics.put(epic.getTaskId(), epic);
            updateEpicStatus(epic.getTaskId());
            updateEpicTime(epic.getTaskId());
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getTaskId())) {
            subtasks.put(subtask.getTaskId(), subtask);
            updateEpicStatus(subtask.getTaskEpicId());
            updateEpicTime(subtask.getTaskEpicId());
        }
        allTasks.removeIf(taskDel -> taskDel.getTaskId() == subtask.getTaskId());
        allTasks.add(subtask);
    }

    protected void updateEpicStatus(long epicId) {
        List<Subtask> epicSubtasks = subtasks.values()
                .stream().filter(task -> task.getTaskEpicId() == epicId).collect(Collectors.toList());
        for (Subtask epicSubtask : epicSubtasks) {
            if (epicSubtask.getTaskStatus().equals(epicSubtasks.get(0).getTaskStatus())) {
                if (epicSubtask.getTaskStatus().equals(StatusTask.NEW)) {
                    epics.get(epicSubtask.getTaskEpicId()).setTaskStatus(StatusTask.NEW);
                } else if (epicSubtask.getTaskStatus().equals(StatusTask.DONE)) {
                    epics.get(epicSubtask.getTaskEpicId()).setTaskStatus(StatusTask.DONE);
                } else {
                    epics.get(epicSubtask.getTaskEpicId()).setTaskStatus(StatusTask.IN_PROGRESS);
                }
            } else {
                epics.get(epicSubtask.getTaskEpicId()).setTaskStatus(StatusTask.IN_PROGRESS);
            }
        }
        allTasks.removeIf(taskDel -> taskDel.getTaskId() == epicId);
        allTasks.add(epics.get(epicId));
    }

    private void updateEpicTime(long epicId) {
        updateEpicStartTime(epicId);
        updateEpicEndTime(epicId);
        updateEpicDuration(epicId);
    }

    private void updateEpicStartTime(long epicId) {
        try {
            List<Subtask> epicSubtasks = subtasks.values()
                    .stream().filter(task -> task.getTaskEpicId() == epicId).collect(Collectors.toList());
            if (epicSubtasks.isEmpty()) {
                throw new TaskManagerException("StarTime таски не изменен, т.к. у Эпика нет подзадач");
            }
            epics.get(epicId).setTaskStarTime(epicSubtasks.get(0).getTaskStarTime());
            for (Subtask epicSubtask : epicSubtasks) {
                if (epicSubtask.getTaskStarTime().isBefore(epics.get(epicId).getTaskStarTime())) {
                    epics.get(epicId).setTaskStarTime(epicSubtask.getTaskStarTime());
                }
            }
        } catch (Exception exception) {
            println(exception.getMessage());
        }
    }

    private void updateEpicEndTime(long epicId) {
        try {
            List<Subtask> epicSubtasks = subtasks.values()
                    .stream().filter(task -> task.getTaskEpicId() == epicId).collect(Collectors.toList());
            if (epicSubtasks.isEmpty()) {
                throw new TaskManagerException("EndTime таски не изменен, т.к. у Эпика нет подзадач");
            }
            epics.get(epicId).setTaskEndTime(epicSubtasks.get(0).getTaskEndTime());
            for (Subtask epicSubtask : epicSubtasks) {
                if (epicSubtask.getTaskEndTime().isAfter(epics.get(epicId).getTaskEndTime())) {
                    epics.get(epicId).setTaskEndTime(epicSubtask.getTaskEndTime());
                }
            }
        } catch (Exception exception) {
            println(exception.getMessage());
        }
    }

    private void updateEpicDuration(long epicId) {
        epics.get(epicId).setTaskDuration(
                Duration.between(epics.get(epicId).getTaskStarTime(), epics.get(epicId).getTaskEndTime()).toMinutes());
        allTasks.removeIf(taskDel -> taskDel.getTaskId() == epicId);
        allTasks.add(epics.get(epicId));
    }

    @Override
    public void deleteTaskById(long idDelete) {
        try {
            if (!allTasks.contains(tasks.get(idDelete)) && !tasks.containsKey(idDelete)) {
                throw new TaskManagerException(idDelete + ERROR_DELETE_TASK);
            }
            allTasks.remove(tasks.get(idDelete));
            tasks.remove(idDelete);
        } catch (TaskManagerException exception) {
            println(exception.getMessage());
        }
    }

    @Override
    public void deleteEpicById(long idDelete) {
        try {
            if (!allTasks.contains(epics.get(idDelete)) && !epics.containsKey(idDelete)) {
                throw new TaskManagerException(idDelete + ERROR_DELETE_TASK);
            }
            allTasks.remove(epics.get(idDelete));
            subtasks.values().stream().filter(subtask -> subtask.getTaskEpicId() == idDelete).
                    collect(Collectors.toList()).forEach(allTasks::remove);
            epics.remove(idDelete);
            subtasks.values().removeIf(subtask -> subtask.getTaskEpicId() == idDelete);
        } catch (TaskManagerException exception) {
            println(exception.getMessage());
        }
    }

    @Override
    public void deleteSubtaskById(long idDelete) {
        try {
            if (!allTasks.contains(subtasks.get(idDelete)) && !subtasks.containsKey(idDelete)) {
                throw new TaskManagerException(idDelete + ERROR_DELETE_TASK);
            }
            long epicId = subtasks.get(idDelete).getTaskEpicId();
            subtasks.remove(idDelete);
            if (epics.containsKey(epicId)) {
                updateEpicStatus(epicId);
                updateEpicTime(epicId);
            }
        } catch (TaskManagerException exception) {
            println(exception.getMessage());
        }
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear(); // Надо ли удалять и историю просмотра задач после удаления самих задач??
        epics.clear();
        subtasks.clear();
        allTasks.clear();
    }

    @Override
    public List<Task> history() {
        return inMemoryHistoryManager.getHistory();
    }

    @Override
    public long getIdLastTask() {
        return idLastTask;
    }

    public void findMaxIdTask() {
        long max = 0;
        for (Task task : allTasks) {
            if (task.getTaskId() > max) {
                max = task.getTaskId();
            }
        }
        idLastTask = max;
    }
}