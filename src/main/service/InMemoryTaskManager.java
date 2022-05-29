package main.service;

import main.model.*;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

import static main.service.Printer.println;

public class InMemoryTaskManager implements TaskManager {

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
    private static final String ERROR_ABSENT_TASK = "Отсутствует задача с таким ID";
    private static final String ERROR_CROSS_TASK = " -  пересекается по времени с другой задачей";
    private static final String ERROR_EXISTS_TASK = " - Задача с данным ID уже существует";
    private static final String ERROR_ABSENT_EPIC = "Для данной subtask отсутствует Epic";

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
    public List<Subtask> getSubtasksOfEpic(long epicId) {
        List<Subtask> listSubtasksOfEpic = new ArrayList<>();
        subtasks.values().stream().filter(subtask -> subtask.getTaskEpicId() == epicId).forEach(listSubtasksOfEpic::add);
        return listSubtasksOfEpic;
    }

    @Override
    public void saveTask(Task task) {
        if (checkCrossTime(task) || checkCrossId(task)) {
            return;
        }
        tasks.put(task.getTaskId(), task);
        allTasks.removeIf(taskDel -> taskDel.getTaskId() == task.getTaskId());
        allTasks.add(task);
    }

    @Override
    public void saveEpic(Epic epic) {
        if (checkCrossTime(epic) || checkCrossId(epic)) {
            return;
        }
        epics.put(epic.getTaskId(), epic);
        allTasks.removeIf(taskDel -> taskDel.getTaskId() == epic.getTaskId());
        allTasks.add(epic);
    }

    @Override
    public void saveSubtask(Subtask subtask) {
        if (epics.containsKey(subtask.getTaskEpicId())) {
            subtasks.put(subtask.getTaskId(), subtask);
            updateEpicTime(subtask.getTaskEpicId());
            updateEpicStatus(subtask.getTaskEpicId());
            allTasks.removeIf(taskDel -> taskDel.getTaskId() == subtask.getTaskId());
            allTasks.add(subtask);
        } else {
            println(ERROR_ABSENT_EPIC);
        }
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getTaskId())) {
            tasks.put(task.getTaskId(), task);
            allTasks.removeIf(taskDel -> taskDel.getTaskId() == task.getTaskId());
            allTasks.add(task);
        } else {
            println(ERROR_ABSENT_TASK);
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getTaskId())) {
            epics.put(epic.getTaskId(), epic);
            updateEpicStatus(epic.getTaskId());
            updateEpicTime(epic.getTaskId());
        } else {
            println(ERROR_ABSENT_TASK);
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getTaskId())) {
            subtasks.put(subtask.getTaskId(), subtask);
            updateEpicStatus(subtask.getTaskEpicId());
            updateEpicTime(subtask.getTaskEpicId());
            allTasks.removeIf(taskDel -> taskDel.getTaskId() == subtask.getTaskId());
            allTasks.add(subtask);
        } else {
            println(ERROR_ABSENT_TASK);
        }
    }

    private boolean checkCrossTime(Task task) {
        if (allTasks.isEmpty()) {
            return false;
        }
        for (Task taskCheck : allTasks) {
            if (!(task.getTaskStarTime().isAfter(taskCheck.getTaskEndTime())
                    || task.getTaskStarTime().equals(taskCheck.getTaskEndTime()))

                    || (task.getTaskEndTime().isBefore(taskCheck.getTaskStarTime())
                    || task.getTaskEndTime().equals(taskCheck.getTaskStarTime()))) {
                println(task + ERROR_CROSS_TASK);
                return true;
            }
        }
        return false;
    }

    public boolean checkCrossId(Task task) {
        for (Task taskCheck : allTasks) {
            if (taskCheck.getTaskId() == task.getTaskId()) {
                println(task.getTaskId() + ERROR_EXISTS_TASK + ": " + taskCheck);
                return true;
            }
        }
        return false;
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
            List<Subtask> epicSubtasks = getSubtasksOfEpic(epicId);
            if (epicSubtasks.isEmpty()) {
                throw new TaskManagerException("StarTime Эпика <ID " + epics.get(epicId) + "> " +
                        "не изменен, т.к. отсутствуют подзадачи");
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
            List<Subtask> epicSubtasks = getSubtasksOfEpic(epicId);
            if (epicSubtasks.isEmpty()) {
                throw new TaskManagerException("EndTime Эпика <ID " + epics.get(epicId) + "> " +
                        "не изменен, т.к. отсутствуют подзадачи");
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
            inMemoryHistoryManager.remove(idDelete);
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

            allTasks.remove(epics.get(idDelete)); //удалили Епики отовсюду
            epics.remove(idDelete);
            inMemoryHistoryManager.remove(idDelete);

            getSubtasksOfEpic(idDelete).forEach(allTasks::remove); //удалили из сабтаски отовсюду
            for (Subtask subtask : getSubtasksOfEpic(idDelete)) {
                inMemoryHistoryManager.remove(subtask.getTaskId());
            }
            subtasks.values().removeAll(getSubtasksOfEpic(idDelete));

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
            inMemoryHistoryManager.remove(idDelete);
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
        tasks.clear();
        epics.clear();
        subtasks.clear();
        try {
            for (Task task : allTasks) {
                inMemoryHistoryManager.remove(task.getTaskId());
            }
        } catch (Exception e) {
            println(e.getMessage());
        }
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