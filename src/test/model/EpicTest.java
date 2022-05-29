package test.model;

import main.model.StatusTask;
import main.service.InMemoryTaskManager;
import main.service.Managers;
import main.service.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static test.service.TestDataUtils.*;

class EpicTest {
    TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getDefault();
    }

    @AfterEach
    public void afterEach() {
        taskManager.deleteAllTasks();
        taskManager = null;
    }

    @Test
    public void shouldStatusEpicIsInProgressWithoutSubtasks() {
        taskManager.saveEpic(createEpicOne(StatusTask.IN_PROGRESS));
        Assertions.assertEquals(StatusTask.IN_PROGRESS, taskManager.getEpics()
                .get(ID_EPIC_ONE).getTaskStatus());
    }

    @Test
    public void shouldStatusEpicIsNewWithSubtasksWithStatusNew() {
        taskManager.saveEpic(createEpicTwo(StatusTask.DONE));
        taskManager.saveSubtask(createSubtaskOne(StatusTask.NEW));
        taskManager.saveSubtask(createSubtaskTwo(StatusTask.NEW));
        Assertions.assertEquals(StatusTask.NEW, taskManager.getEpics().get(ID_EPIC_TWO).getTaskStatus());
    }

    @Test
    public void shouldStatusEpicIsDoneWithSubtasksStatusDone() {
        taskManager.saveEpic(createEpicTwo(StatusTask.NEW));
        taskManager.saveSubtask(createSubtaskOne(StatusTask.DONE));
        taskManager.saveSubtask(createSubtaskTwo(StatusTask.DONE));
        Assertions.assertEquals(StatusTask.DONE, taskManager.getEpics().get(ID_EPIC_TWO).getTaskStatus());
    }

    @Test
    public void shouldStatusEpicIsInProgressWithSubtasksStatusNewAndDone() {
        taskManager.saveEpic(createEpicTwo(StatusTask.NEW));
        taskManager.saveSubtask(createSubtaskOne(StatusTask.NEW));
        taskManager.saveSubtask(createSubtaskTwo(StatusTask.DONE));
        Assertions.assertEquals(StatusTask.IN_PROGRESS, taskManager.getEpics().get(ID_EPIC_TWO).getTaskStatus());
    }

    @Test
    public void shouldStatusEpicIsInProgressWithSubtasksWithStatusInProgress() {
        taskManager.saveEpic(createEpicTwo(StatusTask.NEW));
        taskManager.saveSubtask(createSubtaskOne(StatusTask.IN_PROGRESS));
        taskManager.saveSubtask(createSubtaskTwo(StatusTask.IN_PROGRESS));
        Assertions.assertEquals(StatusTask.IN_PROGRESS, taskManager.getEpics().get(ID_EPIC_TWO).getTaskStatus());
    }
}
