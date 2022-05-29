package test.service;

import main.model.Epic;
import main.model.StatusTask;
import main.model.Task;
import main.service.FileBackedTasksManager;
import main.service.Managers;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static main.model.StatusTask.IN_PROGRESS;
import static main.model.StatusTask.NEW;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static test.service.TestDataUtils.*;

public class FileBackedTasksManagerTest extends TaskManagerTest {

    static final String TEST_DATA_FILE_SAVE = "JUnitSaveTask.csv";
    static final String TEST_DATA_FILE_LOAD = "JUnitLoadTask.csv";

    public FileBackedTasksManagerTest() {
        super(Managers.getFileBackedTasksManager(TEST_DATA_FILE_SAVE));
    }

    @Test
    void shouldSaveTaskInFileIsRight() throws IOException {
        Task task = createTaskOne(StatusTask.IN_PROGRESS);
        taskManager.saveTask(task);

        final String expectedString = "id,type,name,status,description, startTime, endTime, epic\n" +
                "1,TASK,TaskName1,IN_PROGRESS,TaskDescription1,21.05.2022 10:20,21.05.2022 11:20\n\n";
        assertEquals(expectedString, Files.readString(Path.of(TEST_DATA_FILE_SAVE)));
        taskManager.deleteTaskById(task.getTaskId());
    }

    @Test
    void shouldSaveHistoryInFileIsRight() throws IOException {
        taskManager.saveTask(createTaskOne(StatusTask.NEW));
        taskManager.saveTask(createTaskTwo(StatusTask.IN_PROGRESS));
        taskManager.saveEpic(createEpicTwo(StatusTask.DONE));
        taskManager.saveSubtask(createSubtaskOne(StatusTask.NEW));
        taskManager.saveSubtask(createSubtaskTwo(StatusTask.IN_PROGRESS));

        taskManager.getTaskById(2);
        taskManager.getTaskById(1);
        taskManager.getSubtaskById(6);
        taskManager.getEpicById(4);
        taskManager.getSubtaskById(5);

        final String expectedString = "id,type,name,status,description, startTime, endTime, epic\n" +
                "1,TASK,TaskName1,NEW,TaskDescription1,21.05.2022 10:20,21.05.2022 11:20\n" +
                "2,TASK,TaskName2,IN_PROGRESS,TaskDescription2,21.05.2022 11:20,21.05.2022 13:20\n" +
                "4,EPIC,EpicName2,IN_PROGRESS,EpicDescription2,21.05.2022 16:00,21.05.2022 21:50\n" +
                "5,SUBTASK,SubtaskName1,NEW,SubtaskDescription1,21.05.2022 16:00,21.05.2022 19:20,4\n" +
                "6,SUBTASK,SubtaskName2,IN_PROGRESS,SubtaskDescription2,21.05.2022 19:20,21.05.2022 21:50,4\n" +
                "\n" +
                "2,1,6,4,5,";

        assertEquals(expectedString, Files.readString(Path.of(TEST_DATA_FILE_SAVE)));
        taskManager.deleteAllTasks();
    }

    @Test
    void loadTaskAndHistoryFromFile() {
        FileBackedTasksManager taskManagerLoad = Managers.getFileBackedTasksManager(TEST_DATA_FILE_LOAD);
        final Set<Task> allTasks = taskManagerLoad.getAllTasks();

        assertEquals(5, allTasks.size(), INCORRECT_NUMBER_TASKS);

        assertEquals(taskManagerLoad.getTasks().get(ID_TASK_ONE), createTaskOne(NEW), TASKS_NOT_EQUAL);
        assertEquals(taskManagerLoad.getTasks().get(ID_TASK_TWO), createTaskTwo(IN_PROGRESS), TASKS_NOT_EQUAL);
        assertEquals(
                taskManagerLoad.getEpics().get(ID_EPIC_TWO),
                new Epic(
                        ID_EPIC_TWO,
                        EPIC_NAME_TWO,
                        EPIC_DESCRIPTION_TWO,
                        IN_PROGRESS,
                        LocalDateTime.of(2022, 5, 21, 16, 0),
                        350),
                TASKS_NOT_EQUAL);
        assertEquals(taskManagerLoad.getSubtasks().get(ID_SUBTASK_ONE), createSubtaskOne(NEW), TASKS_NOT_EQUAL);
        assertEquals(taskManagerLoad.getSubtasks().get(ID_SUBTASK_TWO), createSubtaskTwo(IN_PROGRESS), TASKS_NOT_EQUAL);

        final List<Task> tasksHistory = taskManagerLoad.history();

        assertNotNull(tasksHistory, VIEWED_TASKS_NOT_RETURN);
        assertEquals(5, tasksHistory.size(), INCORRECT_NUMBER_VIEWED_TASKS);
        assertEquals(2, tasksHistory.get(0).getTaskId(), VIEWED_TASK_NOT_EQUAL);
        assertEquals(1, tasksHistory.get(1).getTaskId(), VIEWED_TASK_NOT_EQUAL);
        assertEquals(6, tasksHistory.get(2).getTaskId(), VIEWED_TASK_NOT_EQUAL);
        assertEquals(4, tasksHistory.get(3).getTaskId(), VIEWED_TASK_NOT_EQUAL);
        assertEquals(5, tasksHistory.get(4).getTaskId(), VIEWED_TASK_NOT_EQUAL);
    }
}