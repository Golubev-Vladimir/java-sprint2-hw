package test;

import model.Epic;
import model.StatusTask;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.Test;
import service.Managers;
import service.TaskManager;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static service.Counter.generateId;

class TaskManagerTest {
    Task task;
    Epic epic;
    Subtask subtask;

    static final String TASK_NAME = "Изучить теорию к ТЗ-6";
    static final String TASK_DESCRIPTION = "Прочитать, сделать конспекты, решить задачи";
    static final String EPIC_DESCRIPTION = "Сдать ТЗ-6";
    static final String EPIC_NAME = "Яндекс-Практикум";
    static final String SUBTASK_NAME_ONE = "JUnit5";
    static final String SUBTASK_NAME_TWO = "TimePriority";
    static final String SUBTASK_DESCRIPTION_ONE = "Протестировать трекер задач";
    static final String SUBTASK_DESCRIPTION_TWO = "Добавить функцию - расставлять задачи по приоритетам";

    TaskManager inMemoryTaskManager = Managers.getDefault();

    private void createEpic(StatusTask status) {
        epic = new Epic(generateId(), EPIC_NAME, EPIC_DESCRIPTION, status);
        inMemoryTaskManager.saveEpic(epic);
    }

    private void createSubtask(StatusTask status, Epic epic, String subtaskName, String subtaskDescription) {
        subtask = new Subtask(generateId(), epic.getTaskId(), subtaskName, subtaskDescription, status);
        inMemoryTaskManager.saveSubtask(subtask);
    }

    private void createTask(StatusTask status, String subtaskName, String subtaskDescription) {
        task = new Task(generateId(), subtaskName, subtaskDescription, status);
        inMemoryTaskManager.saveTask(task);
    }

    @Test
    void addNewTask() {
        createTask(StatusTask.IN_PROGRESS, TASK_NAME, TASK_DESCRIPTION);
        if (inMemoryTaskManager.getTaskById(task.getTaskId()).isPresent()) {
            final Task savedTask = inMemoryTaskManager.getTaskById(task.getTaskId()).get();
            assertNotNull(savedTask, "Задача не найдена.");
            assertEquals(task, savedTask, "Задачи не совпадают.");
        }

        final Map<Long, Task> tasks = inMemoryTaskManager.getTasks();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(task.getTaskId()), "Задачи не совпадают.");
    }

    @Test
    void getEpics() {
    }

    @Test
    void getSubtasks() {
    }

    @Test
    void deleteAllTasks() {
    }

    @Test
    void getEpicById() {
    }

    @Test
    void getSubtaskById() {
    }

    @Test
    void saveEpic() {
    }

    @Test
    void saveSubtask() {
    }

    @Test
    void updateTask() {
    }

    @Test
    void updateEpic() {
    }

    @Test
    void updateSubtask() {
    }

    @Test
    void deleteTaskById() {
    }

    @Test
    void deleteEpicById() {
    }

    @Test
    void deleteSubtaskById() {
    }

    @Test
    void getEpicSubtask() {
    }

    @Test
    void history() {
    }

    @Test
    void getIdLastTask() {
    }
}