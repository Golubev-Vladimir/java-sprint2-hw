package service;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    List<Subtask> getEpicSubtask(long epicId);

    List<Task> history();
}