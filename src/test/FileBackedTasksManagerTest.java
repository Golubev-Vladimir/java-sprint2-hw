package test;

import model.StatusTask;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.Managers;
import service.TaskManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static test.DataClassForAllTestClasses.*;

class FileBackedTasksManagerTest {
    static final String FILE_PATH_SAVE = "JUnitSaveTask.csv";

    TaskManager fileBackedManagerTest;

    @BeforeEach
    void beforeEach() {
        fileBackedManagerTest = Managers.getFileBackedTasksManager(FILE_PATH_SAVE);
    }

    @AfterEach
    void afterEach() {
        fileBackedManagerTest.deleteAllTasks(); // т.е. после удаления файл тоже очищается от задач,
        fileBackedManagerTest = null;           // остается только шапка и задвоение не происходит
    }

    @Test
    void shouldSaveTaskInFileIsRight() throws IOException {
        fileBackedManagerTest.saveTask(createTaskOne(StatusTask.IN_PROGRESS));

        final String expectedString = "id,type,name,status,description, startTime, endTime, epic\n" +
                "1,TASK,TaskName1,IN_PROGRESS,TaskDescription1,21.05.2022 10:20,21.05.2022 11:20\n\n";
        assertEquals(expectedString, Files.readString(Path.of(FILE_PATH_SAVE)));
    }

    @Test
    void shouldSaveHistoryInFileIsRight() throws IOException {
        fileBackedManagerTest.saveTask(createTaskOne(StatusTask.NEW));
        fileBackedManagerTest.saveTask(createTaskTwo(StatusTask.IN_PROGRESS));
        fileBackedManagerTest.saveEpic(createEpicTwo(StatusTask.DONE));
        fileBackedManagerTest.saveSubtask(createSubtaskOne(StatusTask.NEW));
        fileBackedManagerTest.saveSubtask(createSubtaskTwo(StatusTask.IN_PROGRESS));

        fileBackedManagerTest.getTaskById(2);
        fileBackedManagerTest.getTaskById(1);
        fileBackedManagerTest.getSubtaskById(6);
        fileBackedManagerTest.getEpicById(4);
        fileBackedManagerTest.getSubtaskById(5);

        final String expectedString = "id,type,name,status,description, startTime, endTime, epic\n" +
                "1,TASK,TaskName1,NEW,TaskDescription1,21.05.2022 10:20,21.05.2022 11:20\n" +
                "4,EPIC,EpicName2,IN_PROGRESS,EpicDescription2,21.05.2022 13:00,21.05.2022 20:00\n" +
                "5,SUBTASK,SubtaskName1,NEW,SubtaskDescription1,21.05.2022 13:00,21.05.2022 16:20,4\n" +
                "6,SUBTASK,SubtaskName2,IN_PROGRESS,SubtaskDescription2,21.05.2022 15:00,21.05.2022 20:00,4\n" +
                "2,TASK,TaskName2,IN_PROGRESS,TaskDescription2,22.05.2022 11:00,22.05.2022 13:00\n" +
                "\n" +
                "2,1,6,4,5,";

        assertEquals(expectedString, Files.readString(Path.of(FILE_PATH_SAVE)));
    }
}