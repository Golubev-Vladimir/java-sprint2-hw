package test;

import model.Epic;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.Managers;
import service.TaskManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static model.StatusTask.IN_PROGRESS;
import static model.StatusTask.NEW;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static test.DataClassForAllTestClasses.*;

class FileBackedTasksManagerLoaderTest {
    static final String FILE_PATH_LOAD = "JUnitLoadTask.csv";

    TaskManager fileBackedManagerTest;

    @BeforeEach
    void beforeEach() {
        fileBackedManagerTest = Managers.getFileBackedTasksManager(FILE_PATH_LOAD);
    }

    @AfterEach
    void deleteAllData() {
        fileBackedManagerTest = null;
    }

    @Test
    void loadTaskAndHistoryFromFile() {
        final Set<Task> allTasks = fileBackedManagerTest.getAllTasks();

        assertEquals(5, allTasks.size(), INCORRECT_NUMBER_TASKS);

        assertEquals(fileBackedManagerTest.getTasks().get(ID_TASK_ONE), createTaskOne(NEW), TASKS_NOT_EQUAL);
        assertEquals(fileBackedManagerTest.getTasks().get(ID_TASK_TWO), createTaskTwo(IN_PROGRESS), TASKS_NOT_EQUAL);
        assertEquals(
                fileBackedManagerTest.getEpics().get(ID_EPIC_TWO),
                new Epic(
                        ID_EPIC_TWO,
                        EPIC_NAME_TWO,
                        EPIC_DESCRIPTION_TWO,
                        IN_PROGRESS,
                        LocalDateTime.of(2022, 5, 21, 13, 0),
                        420),
                TASKS_NOT_EQUAL);
        assertEquals(fileBackedManagerTest.getSubtasks().get(ID_SUBTASK_ONE), createSubtaskOne(NEW), TASKS_NOT_EQUAL);
        assertEquals(fileBackedManagerTest.getSubtasks().get(ID_SUBTASK_TWO), createSubtaskTwo(IN_PROGRESS), TASKS_NOT_EQUAL);

        final List<Task> tasksHistory = fileBackedManagerTest.history();

        assertNotNull(tasksHistory, VIEWED_TASKS_NOT_RETURN);
        assertEquals(5, tasksHistory.size(), INCORRECT_NUMBER_VIEWED_TASKS);
        assertEquals(2, tasksHistory.get(0).getTaskId(), VIEWED_TASK_NOT_EQUAL);
        assertEquals(1, tasksHistory.get(1).getTaskId(), VIEWED_TASK_NOT_EQUAL);
        assertEquals(6, tasksHistory.get(2).getTaskId(), VIEWED_TASK_NOT_EQUAL);
        assertEquals(4, tasksHistory.get(3).getTaskId(), VIEWED_TASK_NOT_EQUAL);
        assertEquals(5, tasksHistory.get(4).getTaskId(), VIEWED_TASK_NOT_EQUAL);
    }
}