package main.service;

import main.model.Epic;
import main.model.Subtask;
import main.model.Task;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface TaskManager {
    Map<Long, Task> getTasks();

    Map<Long, Epic> getEpics();

    Map<Long, Subtask> getSubtasks();

    void deleteAllTasks();

    Optional<Task> getTaskById(long id);

    Optional<Epic> getEpicById(long id);

    Optional<Subtask> getSubtaskById(long id);

    void saveTask(Task task);

    void saveEpic(Epic epic);

    void saveSubtask(Subtask subtask);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    void deleteTaskById(long idDelete);

    void deleteEpicById(long idDelete);

    void deleteSubtaskById(long idDelete);

    List<Subtask> getSubtasksOfEpic(long epicId);

    List<Task> history();

    long getIdLastTask();

    void findMaxIdTask();

    Set<Task> getAllTasks();
}
