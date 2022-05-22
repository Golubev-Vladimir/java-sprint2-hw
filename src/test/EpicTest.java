package test;

import model.StatusTask;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.Managers;
import service.TaskManager;

import static test.DataClassForAllTestClasses.*;

class EpicTest {
    TaskManager inMemoryTaskManagerEpicTest;

    @BeforeEach
    public void beforeEach() {
        inMemoryTaskManagerEpicTest = Managers.getDefault();
    }

    @AfterEach
    public void afterEach() {
        inMemoryTaskManagerEpicTest.deleteAllTasks();
        inMemoryTaskManagerEpicTest = null;
    }

    @Test
    public void shouldStatusEpicIsInProgressWithoutSubtasks() {
        inMemoryTaskManagerEpicTest.saveEpic(createEpicOne(StatusTask.IN_PROGRESS));
        Assertions.assertEquals(StatusTask.IN_PROGRESS, inMemoryTaskManagerEpicTest.getEpics()
                .get(ID_EPIC_ONE).getTaskStatus());
    }

    @Test
    public void shouldStatusEpicIsNewWithSubtasksWithStatusNew() {
        inMemoryTaskManagerEpicTest.saveEpic(createEpicTwo(StatusTask.DONE));
        inMemoryTaskManagerEpicTest.saveSubtask(createSubtaskOne(StatusTask.NEW));
        inMemoryTaskManagerEpicTest.saveSubtask(createSubtaskTwo(StatusTask.NEW));
        Assertions.assertEquals(StatusTask.NEW, inMemoryTaskManagerEpicTest.getEpics().get(ID_EPIC_TWO).getTaskStatus());
    }

    @Test
    public void shouldStatusEpicIsDoneWithSubtasksStatusDone() {
        inMemoryTaskManagerEpicTest.saveEpic(createEpicTwo(StatusTask.NEW));
        inMemoryTaskManagerEpicTest.saveSubtask(createSubtaskOne(StatusTask.DONE));
        inMemoryTaskManagerEpicTest.saveSubtask(createSubtaskTwo(StatusTask.DONE));
        Assertions.assertEquals(StatusTask.DONE, inMemoryTaskManagerEpicTest.getEpics().get(ID_EPIC_TWO).getTaskStatus());
    }

    @Test
    public void shouldStatusEpicIsInProgressWithSubtasksStatusNewAndDone() {
        inMemoryTaskManagerEpicTest.saveEpic(createEpicTwo(StatusTask.NEW));
        inMemoryTaskManagerEpicTest.saveSubtask(createSubtaskOne(StatusTask.NEW));
        inMemoryTaskManagerEpicTest.saveSubtask(createSubtaskTwo(StatusTask.DONE));
        Assertions.assertEquals(StatusTask.IN_PROGRESS, inMemoryTaskManagerEpicTest.getEpics().get(ID_EPIC_TWO).getTaskStatus());
    }

    @Test
    public void shouldStatusEpicIsInProgressWithSubtasksWithStatusInProgress() {
        inMemoryTaskManagerEpicTest.saveEpic(createEpicTwo(StatusTask.NEW));
        inMemoryTaskManagerEpicTest.saveSubtask(createSubtaskOne(StatusTask.IN_PROGRESS));
        inMemoryTaskManagerEpicTest.saveSubtask(createSubtaskTwo(StatusTask.IN_PROGRESS));
        Assertions.assertEquals(StatusTask.IN_PROGRESS, inMemoryTaskManagerEpicTest.getEpics().get(ID_EPIC_TWO).getTaskStatus());
    }
}
