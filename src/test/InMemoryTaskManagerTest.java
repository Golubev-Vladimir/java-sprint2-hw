package test;

import model.Epic;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.Managers;
import service.TaskManager;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static test.DataClassForAllTestClasses.*;
import static model.StatusTask.*;

class InMemoryTaskManagerTest {
    TaskManager inMemoryManagerTest;
    Task taskOne;
    Task taskTwo;
    Task taskUpdate;
    Epic epicOne;
    Epic epicTwo;
    Epic epicUpdate;
    Subtask subtaskOne;
    Subtask subtaskTwo;
    Subtask subtaskUpdate;

    @BeforeEach
    void beforeEach() {
        inMemoryManagerTest = Managers.getDefault();
    }

    @AfterEach
    void deleteAllData() {
        inMemoryManagerTest.deleteAllTasks();
        taskOne = null;
        taskTwo = null;
        epicOne = null;
        epicTwo = null;
        subtaskOne = null;
        subtaskTwo = null;
        inMemoryManagerTest = null;
    }

    @Test
    void saveTaskGetTaskByIdGetTasks() {
        taskOne = createTaskOne(NEW);
        inMemoryManagerTest.saveTask(taskOne);

        final Task savedTaskOne = inMemoryManagerTest.getTasks().get(ID_TASK_ONE);

        assertNotNull(savedTaskOne, TASK_NOT_FOUND);
        assertEquals(taskOne, savedTaskOne, TASKS_NOT_EQUAL);

        final Map<Long, Task> tasks = inMemoryManagerTest.getTasks();

        assertNotNull(tasks, TASKS_NOT_RETURN);
        assertEquals(1, tasks.size(), INCORRECT_NUMBER_TASKS);
        assertEquals(taskOne, tasks.get(ID_TASK_ONE), TASKS_NOT_EQUAL);
    }

    @Test
    void updateTask() {
        taskOne = createTaskOne(DONE);
        inMemoryManagerTest.saveTask(taskOne);

        taskUpdate = createTaskUpdate(NEW);
        inMemoryManagerTest.updateTask(taskUpdate);

        final Map<Long, Task> tasks = inMemoryManagerTest.getTasks();

        assertNotNull(tasks, TASKS_NOT_RETURN);
        assertEquals(1, tasks.size(), INCORRECT_NUMBER_TASKS);
        assertEquals(taskUpdate, tasks.get(ID_TASK_ONE), TASKS_NOT_EQUAL);
    }

    @Test
    void deleteTaskById() {
        taskOne = createTaskOne(IN_PROGRESS);
        inMemoryManagerTest.saveTask(taskOne);

        inMemoryManagerTest.deleteTaskById(ID_TASK_ONE);

        final Map<Long, Task> tasks = inMemoryManagerTest.getTasks();

        assertEquals(0, tasks.size(), TASKS_NOT_DELETED);
    }

    @Test
    void getIdLastTask() {
        inMemoryManagerTest.saveTask(createTaskOne(IN_PROGRESS));
        inMemoryManagerTest.saveTask(createTaskTwo(NEW));
        inMemoryManagerTest.saveEpic(createEpicTwo(IN_PROGRESS));
        inMemoryManagerTest.saveSubtask(createSubtaskOne(DONE));
        inMemoryManagerTest.saveSubtask(createSubtaskTwo(DONE));

        inMemoryManagerTest.findMaxIdTask();

        assertEquals(6, inMemoryManagerTest.getIdLastTask(), "Не возвращает последний ID");
    }

    @Test
    void saveEpicGetEpicByIdGetEpics() {
        epicOne = createEpicOne(NEW);
        inMemoryManagerTest.saveEpic(epicOne);

        final Task savedEpicOne = inMemoryManagerTest.getEpics().get(ID_EPIC_ONE);

        assertNotNull(savedEpicOne, TASK_NOT_FOUND);
        assertEquals(epicOne, savedEpicOne, TASKS_NOT_EQUAL);

        final Map<Long, Epic> epics = inMemoryManagerTest.getEpics();

        assertNotNull(epics, TASKS_NOT_RETURN);
        assertEquals(1, epics.size(), INCORRECT_NUMBER_TASKS);
        assertEquals(epicOne, epics.get(ID_EPIC_ONE), TASKS_NOT_EQUAL);
    }

    @Test
    void updateEpic() {
        inMemoryManagerTest.saveEpic(createEpicOne(DONE));

        epicUpdate = createEpicUpdate(IN_PROGRESS);
        inMemoryManagerTest.updateEpic(epicUpdate);

        final Map<Long, Epic> epics = inMemoryManagerTest.getEpics();

        assertNotNull(epics, TASKS_NOT_RETURN);
        assertEquals(1, epics.size(), INCORRECT_NUMBER_TASKS);
        assertEquals(epicUpdate, epics.get(ID_EPIC_ONE), TASKS_NOT_EQUAL);
    }

    @Test
    void deleteEpicById() {
        inMemoryManagerTest.saveEpic(createEpicTwo(DONE));
        inMemoryManagerTest.saveSubtask(createSubtaskOne(NEW));
        inMemoryManagerTest.saveSubtask(createSubtaskTwo(IN_PROGRESS));

        inMemoryManagerTest.deleteEpicById(ID_EPIC_TWO);

        final Map<Long, Epic> epics = inMemoryManagerTest.getEpics();
        final Map<Long, Subtask> subtasks = inMemoryManagerTest.getSubtasks();

        assertEquals(0, epics.size(), TASKS_NOT_DELETED);
        assertEquals(0, subtasks.size(), TASKS_NOT_DELETED);
    }

    @Test
    void getEpicSubtask() {
        inMemoryManagerTest.saveEpic(createEpicTwo(IN_PROGRESS));

        subtaskOne = createSubtaskOne(NEW);
        inMemoryManagerTest.saveSubtask(subtaskOne);

        subtaskTwo = createSubtaskTwo(NEW);
        inMemoryManagerTest.saveSubtask(subtaskTwo);

        final Set<Subtask> subtasks = inMemoryManagerTest.getEpicSubtask(ID_EPIC_TWO);

        assertNotNull(subtasks, TASKS_NOT_RETURN);
        assertEquals(2, subtasks.size(), INCORRECT_NUMBER_TASKS);
        assertTrue(subtasks.contains(subtaskOne), TASKS_NOT_EQUAL);
        assertTrue(subtasks.contains(subtaskTwo), TASKS_NOT_EQUAL);
    }

    @Test
    void saveSubtaskGetSubtaskByIdGetSubtasks() {
        inMemoryManagerTest.saveEpic(createEpicTwo(IN_PROGRESS));
        subtaskOne = createSubtaskOne(NEW);
        inMemoryManagerTest.saveSubtask(subtaskOne);

        final Subtask savedSubtaskOne = inMemoryManagerTest.getSubtasks().get(ID_SUBTASK_ONE);

        assertNotNull(savedSubtaskOne, TASK_NOT_FOUND);
        assertEquals(subtaskOne, savedSubtaskOne, TASKS_NOT_EQUAL);

        final Map<Long, Subtask> subtasks = inMemoryManagerTest.getSubtasks();

        assertNotNull(subtasks, TASKS_NOT_RETURN);
        assertEquals(1, subtasks.size(), INCORRECT_NUMBER_TASKS);
        assertEquals(subtaskOne, subtasks.get(ID_SUBTASK_ONE), TASKS_NOT_EQUAL);
    }

    @Test
    void updateSubtask() {
        inMemoryManagerTest.saveEpic(createEpicTwo(NEW));
        inMemoryManagerTest.saveSubtask(createSubtaskOne(DONE));

        subtaskUpdate = createSubtaskUpdate(NEW);
        inMemoryManagerTest.updateSubtask(subtaskUpdate);

        final Map<Long, Subtask> subtasks = inMemoryManagerTest.getSubtasks();

        assertNotNull(subtasks, TASKS_NOT_RETURN);
        assertEquals(1, subtasks.size(), INCORRECT_NUMBER_TASKS);
        assertEquals(subtaskUpdate, subtasks.get(ID_SUBTASK_ONE), TASKS_NOT_EQUAL);
    }

    @Test
    void deleteSubtaskById() {
        inMemoryManagerTest.saveEpic(createEpicTwo(DONE));
        inMemoryManagerTest.saveSubtask(createSubtaskOne(NEW));

        inMemoryManagerTest.deleteSubtaskById(ID_SUBTASK_ONE);

        final Map<Long, Subtask> subtasks = inMemoryManagerTest.getSubtasks();

        assertEquals(0, subtasks.size(), TASKS_NOT_DELETED);
    }

    @Test
    void shouldReturnCorrectIdEpic() {
        epicOne = createEpicTwo(DONE);
        inMemoryManagerTest.saveEpic(epicOne);

        subtaskOne = createSubtaskOne(NEW);
        inMemoryManagerTest.saveSubtask(createSubtaskOne(NEW));

        final Epic epic = inMemoryManagerTest.getEpics().get(subtaskOne.getTaskEpicId());

        assertEquals(epic, epicOne, "Эпики не совпадают");

    }

    @Test
    void deleteAllTasks() {
        inMemoryManagerTest.saveTask(createTaskOne(NEW));
        inMemoryManagerTest.saveTask(createTaskTwo(IN_PROGRESS));
        inMemoryManagerTest.saveEpic(createEpicOne(IN_PROGRESS));
        inMemoryManagerTest.saveEpic(createEpicTwo(DONE));
        inMemoryManagerTest.saveSubtask(createSubtaskOne(NEW));
        inMemoryManagerTest.saveSubtask(createSubtaskTwo(IN_PROGRESS));

        inMemoryManagerTest.deleteAllTasks();

        final Map<Long, Task> tasks = inMemoryManagerTest.getTasks();
        final Map<Long, Epic> epics = inMemoryManagerTest.getEpics();
        final Map<Long, Subtask> subtasks = inMemoryManagerTest.getSubtasks();
        final Set<Task> allTasks = inMemoryManagerTest.getAllTasks();

        assertEquals(0, tasks.size(), "Не совпадает кол-во задач в списке Task");
        assertEquals(0, epics.size(), "Не совпадает кол-во задач в списке Epic");
        assertEquals(0, subtasks.size(), "Не совпадает кол-во задач в списке Subtask");
        assertEquals(0, allTasks.size(), "Не совпадает кол-во всех задач в общем списке");
    }

    @Test
    void updateEpicTime() {
        inMemoryManagerTest.saveEpic(createEpicOne(NEW));
        epicTwo = createEpicTwo(NEW);
        inMemoryManagerTest.saveEpic(epicTwo);
        inMemoryManagerTest.saveSubtask(createSubtaskOne(IN_PROGRESS));
        inMemoryManagerTest.saveSubtask(createSubtaskTwo(DONE));
        Epic epicTest = new Epic(ID_EPIC_TWO,
                EPIC_NAME_TWO,
                EPIC_DESCRIPTION_TWO,
                IN_PROGRESS,
                LocalDateTime.of(2022, 5, 21, 13, 0),
                420);

        assertEquals(inMemoryManagerTest.getEpics().get(ID_EPIC_ONE), createEpicOne(NEW), TASKS_NOT_EQUAL);
        assertEquals(
                inMemoryManagerTest.getEpics().get(ID_EPIC_TWO), epicTest, TASKS_NOT_EQUAL);
    }
}