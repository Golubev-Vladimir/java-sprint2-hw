package test.service;

import main.model.Epic;
import main.model.Subtask;
import main.model.Task;
import main.service.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static main.model.StatusTask.*;
import static org.junit.jupiter.api.Assertions.*;
import static test.service.TestDataUtils.*;

public class TaskManagerTest {

    TaskManager taskManager;

    public TaskManagerTest(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @AfterEach
    void deleteAllData() {
        taskManager.deleteAllTasks();
        taskManager = null;
    }

    @Test
    void saveTaskGetTaskByIdGetTasks() {
        Task taskOne = createTaskOne(NEW);
        taskManager.saveTask(taskOne);

        final Task savedTaskOne = taskManager.getTasks().get(ID_TASK_ONE);

        assertNotNull(savedTaskOne, TASK_NOT_FOUND);
        assertEquals(taskOne, savedTaskOne, TASKS_NOT_EQUAL);

        final Map<Long, Task> tasks = taskManager.getTasks();

        assertNotNull(tasks, TASKS_NOT_RETURN);
        assertEquals(1, tasks.size(), INCORRECT_NUMBER_TASKS);
        assertEquals(taskOne, tasks.get(ID_TASK_ONE), TASKS_NOT_EQUAL);
    }

    @Test
    void updateTask() {
        Task taskOne = createTaskOne(DONE);
        taskManager.saveTask(taskOne);

        Task taskUpdate = createTaskUpdate(NEW);
        taskManager.updateTask(taskUpdate);

        final Map<Long, Task> tasks = taskManager.getTasks();

        assertNotNull(tasks, TASKS_NOT_RETURN);
        assertEquals(1, tasks.size(), INCORRECT_NUMBER_TASKS);
        assertEquals(taskUpdate, tasks.get(ID_TASK_ONE), TASKS_NOT_EQUAL);
    }

    @Test
    void deleteTaskById() {
        Task taskOne = createTaskOne(IN_PROGRESS);
        taskManager.saveTask(taskOne);

        taskManager.deleteTaskById(ID_TASK_ONE);

        final Map<Long, Task> tasks = taskManager.getTasks();

        assertEquals(0, tasks.size(), TASKS_NOT_DELETED);
    }

    @Test
    void getIdLastTask() {
        taskManager.saveTask(createTaskOne(IN_PROGRESS));
        taskManager.saveTask(createTaskTwo(NEW));
        taskManager.saveEpic(createEpicTwo(IN_PROGRESS));
        taskManager.saveSubtask(createSubtaskOne(DONE));
        taskManager.saveSubtask(createSubtaskTwo(DONE));

        taskManager.findMaxIdTask();

        assertEquals(6, taskManager.getIdLastTask(), "Не возвращает последний ID");
    }

    @Test
    void saveEpicGetEpicByIdGetEpics() {
        Epic epicOne = createEpicOne(NEW);
        taskManager.saveEpic(epicOne);

        final Task savedEpicOne = taskManager.getEpics().get(ID_EPIC_ONE);

        assertNotNull(savedEpicOne, TASK_NOT_FOUND);
        assertEquals(epicOne, savedEpicOne, TASKS_NOT_EQUAL);

        final Map<Long, Epic> epics = taskManager.getEpics();

        assertNotNull(epics, TASKS_NOT_RETURN);
        assertEquals(1, epics.size(), INCORRECT_NUMBER_TASKS);
        assertEquals(epicOne, epics.get(ID_EPIC_ONE), TASKS_NOT_EQUAL);
    }

    @Test
    void updateEpic() {
        taskManager.saveEpic(createEpicOne(DONE));

        Epic epicUpdate = createEpicUpdate(IN_PROGRESS);
        taskManager.updateEpic(epicUpdate);

        final Map<Long, Epic> epics = taskManager.getEpics();

        assertNotNull(epics, TASKS_NOT_RETURN);
        assertEquals(1, epics.size(), INCORRECT_NUMBER_TASKS);
        assertEquals(epicUpdate, epics.get(ID_EPIC_ONE), TASKS_NOT_EQUAL);
    }

    @Test
    void deleteEpicById() {
        taskManager.saveEpic(createEpicTwo(DONE));
        taskManager.saveSubtask(createSubtaskOne(NEW));
        taskManager.saveSubtask(createSubtaskTwo(IN_PROGRESS));

        taskManager.deleteEpicById(ID_EPIC_TWO);

        final Map<Long, Epic> epics = taskManager.getEpics();
        final Map<Long, Subtask> subtasks = taskManager.getSubtasks();

        assertEquals(0, epics.size(), TASKS_NOT_DELETED);
        assertEquals(0, subtasks.size(), TASKS_NOT_DELETED);
    }

    @Test
    void getSubtasksOfEpic() {
        taskManager.saveEpic(createEpicTwo(IN_PROGRESS));

        Subtask subtaskOne = createSubtaskOne(NEW);
        taskManager.saveSubtask(subtaskOne);

        Subtask subtaskTwo = createSubtaskTwo(NEW);
        taskManager.saveSubtask(subtaskTwo);

        final List<Subtask> subtasks = taskManager.getSubtasksOfEpic(ID_EPIC_TWO);

        assertNotNull(subtasks, TASKS_NOT_RETURN);
        assertEquals(2, subtasks.size(), INCORRECT_NUMBER_TASKS);
        assertTrue(subtasks.contains(subtaskOne), TASKS_NOT_EQUAL);
        assertTrue(subtasks.contains(subtaskTwo), TASKS_NOT_EQUAL);
    }

    @Test
    void saveSubtaskGetSubtaskByIdGetSubtasks() {
        taskManager.saveEpic(createEpicTwo(IN_PROGRESS));
        Subtask subtaskOne = createSubtaskOne(NEW);
        taskManager.saveSubtask(subtaskOne);

        final Subtask savedSubtaskOne = taskManager.getSubtasks().get(ID_SUBTASK_ONE);

        assertNotNull(savedSubtaskOne, TASK_NOT_FOUND);
        assertEquals(subtaskOne, savedSubtaskOne, TASKS_NOT_EQUAL);

        final Map<Long, Subtask> subtasks = taskManager.getSubtasks();

        assertNotNull(subtasks, TASKS_NOT_RETURN);
        assertEquals(1, subtasks.size(), INCORRECT_NUMBER_TASKS);
        assertEquals(subtaskOne, subtasks.get(ID_SUBTASK_ONE), TASKS_NOT_EQUAL);
    }

    @Test
    void updateSubtask() {
        taskManager.saveEpic(createEpicTwo(NEW));
        taskManager.saveSubtask(createSubtaskOne(DONE));

        Subtask subtaskUpdate = createSubtaskUpdate(NEW);
        taskManager.updateSubtask(subtaskUpdate);

        final Map<Long, Subtask> subtasks = taskManager.getSubtasks();

        assertNotNull(subtasks, TASKS_NOT_RETURN);
        assertEquals(1, subtasks.size(), INCORRECT_NUMBER_TASKS);
        assertEquals(subtaskUpdate, subtasks.get(ID_SUBTASK_ONE), TASKS_NOT_EQUAL);
    }

    @Test
    void deleteSubtaskById() {
        taskManager.saveEpic(createEpicTwo(DONE));
        taskManager.saveSubtask(createSubtaskOne(NEW));

        taskManager.deleteSubtaskById(ID_SUBTASK_ONE);

        final Map<Long, Subtask> subtasks = taskManager.getSubtasks();

        assertEquals(0, subtasks.size(), TASKS_NOT_DELETED);
    }

    @Test
    void deleteAllTasks() {
        taskManager.saveTask(createTaskOne(NEW));
        taskManager.saveTask(createTaskTwo(IN_PROGRESS));
        taskManager.saveEpic(createEpicOne(IN_PROGRESS));
        taskManager.saveEpic(createEpicTwo(DONE));
        taskManager.saveSubtask(createSubtaskOne(NEW));
        taskManager.saveSubtask(createSubtaskTwo(IN_PROGRESS));

        taskManager.deleteAllTasks();

        final Map<Long, Task> tasks = taskManager.getTasks();
        final Map<Long, Epic> epics = taskManager.getEpics();
        final Map<Long, Subtask> subtasks = taskManager.getSubtasks();
        final Set<Task> allTasks = taskManager.getAllTasks();

        assertEquals(0, tasks.size(), "Не совпадает кол-во задач в списке Task");
        assertEquals(0, epics.size(), "Не совпадает кол-во задач в списке Epic");
        assertEquals(0, subtasks.size(), "Не совпадает кол-во задач в списке Subtask");
        assertEquals(0, allTasks.size(), "Не совпадает кол-во всех задач в общем списке");
    }
}