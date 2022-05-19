package test;

import model.Epic;
import model.StatusTask;
import model.Subtask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.Managers;
import service.TaskManager;

import static service.Counter.generateId;

class EpicTest {
    Epic epic;
    Subtask subtask;

    static final String EPIC_NAME = "Яндекс-Практикум";
    static final String EPIC_DESCRIPTION = "Сдать ТЗ-6";
    static final String SUBTASK_NAME_ONE = "JUnit5";
    static final String SUBTASK_NAME_TWO = "TimePriority";
    static final String SUBTASK_DESCRIPTION_ONE = "Протестировать трекер задач";
    static final String SUBTASK_DESCRIPTION_TWO = "Добавить функцию - расставлять задачи по приоритетам";

    TaskManager inMemoryTaskManager = Managers.getDefault();

    private void createEpic() {
        epic = new Epic(generateId(), EPIC_NAME, EPIC_DESCRIPTION, StatusTask.IN_PROGRESS);
        inMemoryTaskManager.saveEpic(epic);
    }

    private void createSubtask(StatusTask status, Epic epic, String subtaskName, String subtaskDescription) {
        subtask = new Subtask(generateId(), epic.getTaskId(), subtaskName, subtaskDescription, status);
        inMemoryTaskManager.saveSubtask(subtask);
    }

    @BeforeEach
    public void beforeEach() {
        createEpic();
    }

    @Test
    public void shouldStatusEpicIsInProgressWithoutSubtasks() {
        Assertions.assertEquals(StatusTask.IN_PROGRESS, inMemoryTaskManager.getEpics().get(epic.getTaskId())
                .getTaskStatus());
    }

    @Test
    public void shouldStatusEpicIsNewWithSubtasksWithStatusNew() {
        createSubtask(StatusTask.NEW, epic, SUBTASK_NAME_ONE, SUBTASK_DESCRIPTION_ONE);
        createSubtask(StatusTask.NEW, epic, SUBTASK_NAME_TWO, SUBTASK_DESCRIPTION_TWO);
        Assertions.assertEquals(StatusTask.NEW, inMemoryTaskManager.getEpics().get(epic.getTaskId())
                .getTaskStatus());
    }

    @Test
    public void shouldStatusEpicIsDoneWithSubtasksStatusDone() {
        createSubtask(StatusTask.DONE, epic, SUBTASK_NAME_ONE, SUBTASK_DESCRIPTION_ONE);
        createSubtask(StatusTask.DONE, epic, SUBTASK_NAME_TWO, SUBTASK_DESCRIPTION_TWO);
        Assertions.assertEquals(StatusTask.DONE, inMemoryTaskManager.getEpics().get(epic.getTaskId())
                .getTaskStatus());
    }

    @Test
    public void shouldStatusEpicIsInProgressWithSubtasksStatusNewAndDone() {
        createSubtask(StatusTask.NEW, epic, SUBTASK_NAME_ONE, SUBTASK_DESCRIPTION_ONE);
        createSubtask(StatusTask.DONE, epic, SUBTASK_NAME_TWO, SUBTASK_DESCRIPTION_TWO);
        Assertions.assertEquals(StatusTask.IN_PROGRESS, inMemoryTaskManager.getEpics().get(epic.getTaskId())
                .getTaskStatus());
    }

    @Test
    public void shouldStatusEpicIsNewWithSubtasksWithStatusInProgress() {
        createSubtask(StatusTask.IN_PROGRESS, epic, SUBTASK_NAME_ONE, SUBTASK_DESCRIPTION_ONE);
        createSubtask(StatusTask.IN_PROGRESS, epic, SUBTASK_NAME_TWO, SUBTASK_DESCRIPTION_TWO);
        Assertions.assertEquals(StatusTask.IN_PROGRESS, inMemoryTaskManager.getEpics().get(epic.getTaskId())
                .getTaskStatus());
    }
}