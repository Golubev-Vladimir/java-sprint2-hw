package test.service;

import main.model.Epic;
import main.model.Subtask;
import main.service.Managers;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static main.model.StatusTask.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static test.service.TestDataUtils.*;

class InMemoryTaskManagerTest extends TaskManagerTest {

    public InMemoryTaskManagerTest() {
        super(Managers.getDefault());
    }

    @Test
    void shouldReturnCorrectIdEpic() {
        Epic epicTwo = createEpicTwo(DONE);
        taskManager.saveEpic(epicTwo);

        Subtask subtaskOne = createSubtaskOne(NEW);
        taskManager.saveSubtask(subtaskOne);

        final Epic epic = taskManager.getEpics().get(subtaskOne.getTaskEpicId());

        assertEquals(epic, epicTwo, "Эпики не совпадают");
    }

    @Test
    void updateEpicTime() {
        taskManager.saveEpic(createEpicOne(NEW));
        Epic epicTwo = createEpicTwo(NEW);
        taskManager.saveEpic(epicTwo);
        taskManager.saveSubtask(createSubtaskOne(IN_PROGRESS));
        taskManager.saveSubtask(createSubtaskTwo(DONE));
        Epic epicTest = new Epic(ID_EPIC_TWO,
                EPIC_NAME_TWO,
                EPIC_DESCRIPTION_TWO,
                IN_PROGRESS,
                LocalDateTime.of(2022, 5, 21, 16, 0),
                350);

        assertEquals(taskManager.getEpics().get(ID_EPIC_ONE), createEpicOne(NEW), TASKS_NOT_EQUAL);
        assertEquals(taskManager.getEpics().get(ID_EPIC_TWO), epicTest, TASKS_NOT_EQUAL);
    }
}