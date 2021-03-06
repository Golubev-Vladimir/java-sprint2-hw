package test.service;

import main.model.Epic;
import main.model.StatusTask;
import main.model.Subtask;
import main.model.Task;
import main.service.Managers;
import main.service.TaskManager;

import java.time.LocalDateTime;

import static main.model.StatusTask.IN_PROGRESS;
import static main.model.StatusTask.NEW;

public class TestDataUtils {

    private TestDataUtils() {
    }

    static final String TASK_NAME_ONE = "TaskName1";
    static final String TASK_NAME_TWO = "TaskName2";
    static final String TASK_NAME_UPDATE = "TaskNameUpdate";
    static final String EPIC_NAME_ONE = "EpicName1";
    static final String EPIC_NAME_TWO = "EpicName2";
    static final String EPIC_NAME_UPDATE = "EpicNameUpdate";
    static final String SUBTASK_NAME_ONE = "SubtaskName1";
    static final String SUBTASK_NAME_TWO = "SubtaskName2";
    static final String SUBTASK_NAME_UPDATE = "SubtaskNameUpdate";

    static final String TASK_DESCRIPTION_ONE = "TaskDescription1";
    static final String TASK_DESCRIPTION_TWO = "TaskDescription2";
    static final String TASK_DESCRIPTION_UPDATE = "TaskDescriptionUpdate";
    static final String EPIC_DESCRIPTION_ONE = "EpicDescription1";
    static final String EPIC_DESCRIPTION_TWO = "EpicDescription2";
    static final String EPIC_DESCRIPTION_UPDATE = "EpicDescriptionUpdate";
    static final String SUBTASK_DESCRIPTION_ONE = "SubtaskDescription1";
    static final String SUBTASK_DESCRIPTION_TWO = "SubtaskDescription2";
    static final String SUBTASK_DESCRIPTION_UPDATE = "SubtaskDescriptionUpdate";

    static final long ID_TASK_ONE = 1;
    static final long ID_TASK_TWO = 2;
    public static final long ID_EPIC_ONE = 3;
    public static final long ID_EPIC_TWO = 4;
    static final long ID_SUBTASK_ONE = 5;
    static final long ID_SUBTASK_TWO = 6;

    static final LocalDateTime START_TIME_TASK_ONE =       LocalDateTime.of(2022, 5, 21, 10, 20);
    static final LocalDateTime START_TIME_TASK_TWO =       LocalDateTime.of(2022, 5, 21, 11, 20);
    static final LocalDateTime START_TIME_EPIC_ONE =       LocalDateTime.of(2022, 5, 21, 13, 20);
    static final LocalDateTime START_TIME_EPIC_TWO =       LocalDateTime.of(2022, 5, 21, 14,  0);
    static final LocalDateTime START_TIME_SUBTASK_ONE =    LocalDateTime.of(2022, 5, 21, 16,  0);
    static final LocalDateTime START_TIME_SUBTASK_TWO =    LocalDateTime.of(2022, 5, 21, 19, 20);

    static final LocalDateTime START_TIME_TASK_UPDATE =    LocalDateTime.of(2022, 5, 21, 10, 20);
    static final LocalDateTime START_TIME_EPIC_UPDATE =    LocalDateTime.of(2022, 5, 21, 13, 20);
    static final LocalDateTime START_TIME_SUBTASK_UPDATE = LocalDateTime.of(2022, 5, 21, 16,  0);

    static final int DURATION_TASK_ONE = 60;
    static final int DURATION_TASK_TWO = 120;
    static final int DURATION_EPIC_ONE = 40;
    static final int DURATION_EPIC_TWO = 120;
    static final int DURATION_SUBTASK_ONE = 200;
    static final int DURATION_SUBTASK_TWO = 150;

    static final int DURATION_TASK_UPDATE = 80;
    static final int DURATION_EPIC_UPDATE = 150;
    static final int DURATION_SUBTASK_UPDATE = 450;

    static final String TASK_NOT_FOUND = "???????????? ???? ??????????????.";
    static final String TASKS_NOT_EQUAL = "???????????? ???? ??????????????????.";
    static final String TASKS_NOT_RETURN = "???????????? ???? ????????????????????????.";
    static final String VIEWED_TASKS_NOT_RETURN = "?????????????????????????? ???????????? ???? ????????????????????????.";
    static final String INCORRECT_NUMBER_TASKS = "???????????????? ???????????????????? ??????????.";
    static final String INCORRECT_NUMBER_VIEWED_TASKS = "???????????????? ???????????????????? ?????????????????????????? ??????????.";
    static final String TASKS_NOT_DELETED = "???????????? ???? ??????????????";
    static final String VIEWED_TASK_NOT_EQUAL = "???????????? ???? ??????????????";


    static Task createTaskOne(StatusTask statusTask) {
        return new Task(
                ID_TASK_ONE,
                TASK_NAME_ONE,
                TASK_DESCRIPTION_ONE,
                statusTask,
                START_TIME_TASK_ONE,
                DURATION_TASK_ONE);
    }

    static Task createTaskTwo(StatusTask statusTask) {
        return new Task(
                ID_TASK_TWO,
                TASK_NAME_TWO,
                TASK_DESCRIPTION_TWO,
                statusTask,
                START_TIME_TASK_TWO,
                DURATION_TASK_TWO);
    }

    static Task createTaskUpdate(StatusTask statusTask) {
        return new Task(
                ID_TASK_ONE,
                TASK_NAME_UPDATE,
                TASK_DESCRIPTION_UPDATE,
                statusTask,
                START_TIME_TASK_UPDATE,
                DURATION_TASK_UPDATE);
    }

    public static Epic createEpicOne(StatusTask statusTask) {
        return new Epic(
                ID_EPIC_ONE,
                EPIC_NAME_ONE,
                EPIC_DESCRIPTION_ONE,
                statusTask,
                START_TIME_EPIC_ONE,
                DURATION_EPIC_ONE);
    }

    public static Epic createEpicTwo(StatusTask statusTask) {
        return new Epic(
                ID_EPIC_TWO,
                EPIC_NAME_TWO,
                EPIC_DESCRIPTION_TWO,
                statusTask,
                START_TIME_EPIC_TWO,
                DURATION_EPIC_TWO);
    }

    static Epic createEpicUpdate(StatusTask statusTask) {
        return new Epic(
                ID_EPIC_ONE,
                EPIC_NAME_UPDATE,
                EPIC_DESCRIPTION_UPDATE,
                statusTask,
                START_TIME_EPIC_UPDATE,
                DURATION_EPIC_UPDATE);
    }

    public static Subtask createSubtaskOne(StatusTask statusTask) {
        return new Subtask(
                ID_SUBTASK_ONE,
                ID_EPIC_TWO,
                SUBTASK_NAME_ONE,
                SUBTASK_DESCRIPTION_ONE,
                statusTask,
                START_TIME_SUBTASK_ONE,
                DURATION_SUBTASK_ONE);
    }

    public static Subtask createSubtaskTwo(StatusTask statusTask) {
        return new Subtask(
                ID_SUBTASK_TWO,
                ID_EPIC_TWO,
                SUBTASK_NAME_TWO,
                SUBTASK_DESCRIPTION_TWO,
                statusTask,
                START_TIME_SUBTASK_TWO,
                DURATION_SUBTASK_TWO);
    }

    static Subtask createSubtaskUpdate(StatusTask statusTask) {
        return new Subtask(
                ID_SUBTASK_ONE,
                ID_EPIC_TWO,
                SUBTASK_NAME_UPDATE,
                SUBTASK_DESCRIPTION_UPDATE,
                statusTask,
                START_TIME_SUBTASK_UPDATE,
                DURATION_SUBTASK_UPDATE);
    }

    public static void main(String[] args) {

        TaskManager taskManager = Managers.getDefault();
        taskManager.saveTask(createTaskOne(NEW));
        taskManager.saveTask(createTaskTwo(NEW));

        taskManager.saveEpic(createEpicOne(NEW));
        taskManager.saveEpic(createEpicTwo(NEW));
        taskManager.saveSubtask(createSubtaskOne(IN_PROGRESS));
        taskManager.saveSubtask(createSubtaskTwo(IN_PROGRESS));
    }
}