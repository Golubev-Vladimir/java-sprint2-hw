package test;

import model.Epic;
import model.StatusTask;
import model.Subtask;
import model.Task;

import java.time.LocalDateTime;

public class DataClassForAllTestClasses {

    private DataClassForAllTestClasses() {
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
    static final long ID_EPIC_ONE = 3;
    static final long ID_EPIC_TWO = 4;
    static final long ID_SUBTASK_ONE = 5;
    static final long ID_SUBTASK_TWO = 6;

    static final LocalDateTime START_TIME_TASK_ONE = LocalDateTime.of (2022, 5, 21, 10, 20);
    static final LocalDateTime START_TIME_TASK_TWO = LocalDateTime.of (2022, 5, 22, 11, 0);
    static final LocalDateTime START_TIME_TASK_UPDATE = LocalDateTime.of (2022, 6, 10, 12, 5);
    static final LocalDateTime START_TIME_EPIC_ONE = LocalDateTime.of (2022, 5, 21, 12, 10);
    static final LocalDateTime START_TIME_EPIC_TWO = LocalDateTime.of (2022, 5, 21, 14, 0);
    static final LocalDateTime START_TIME_EPIC_UPDATE = LocalDateTime.of (2022, 7, 15, 9, 15);
    static final LocalDateTime START_TIME_SUBTASK_ONE = LocalDateTime.of (2022, 5, 21, 13, 0);
    static final LocalDateTime START_TIME_SUBTASK_TWO = LocalDateTime.of (2022, 5, 21, 15, 0);
    static final LocalDateTime START_TIME_SUBTASK_UPDATE = LocalDateTime.of (2022, 6, 11, 22, 30);

    static final int DURATION_TASK_ONE = 60;
    static final int DURATION_TASK_TWO = 120;
    static final int DURATION_TASK_UPDATE = 80;
    static final int DURATION_EPIC_ONE = 40;
    static final int DURATION_EPIC_TWO = 1000;
    static final int DURATION_EPIC_UPDATE = 150;
    static final int DURATION_SUBTASK_ONE = 200;
    static final int DURATION_SUBTASK_TWO = 300;
    static final int DURATION_SUBTASK_UPDATE = 450;

    static final String TASK_NOT_FOUND = "Задача не найдена.";
    static final String TASKS_NOT_EQUAL = "Задачи не совпадают.";
    static final String TASKS_NOT_RETURN = "Задачи не возвращаются.";
    static final String VIEWED_TASKS_NOT_RETURN = "Просмотренные задачи не возвращаются.";
    static final String INCORRECT_NUMBER_TASKS = "Неверное количество задач.";
    static final String INCORRECT_NUMBER_VIEWED_TASKS = "Неверное количество просмотренных задач.";
    static final String TASKS_NOT_DELETED = "Задачи не удалены";
    static final String VIEWED_TASK_NOT_EQUAL = "Задачи не удалены";


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

    static Epic createEpicOne(StatusTask statusTask) {
        return new Epic(
                ID_EPIC_ONE,
                EPIC_NAME_ONE,
                EPIC_DESCRIPTION_ONE,
                statusTask,
                START_TIME_EPIC_ONE,
                DURATION_EPIC_ONE);
    }

    static Epic createEpicTwo(StatusTask statusTask) {
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

    static Subtask createSubtaskOne(StatusTask statusTask) {
        return new Subtask(
                ID_SUBTASK_ONE,
                ID_EPIC_TWO,
                SUBTASK_NAME_ONE,
                SUBTASK_DESCRIPTION_ONE,
                statusTask,
                START_TIME_SUBTASK_ONE,
                DURATION_SUBTASK_ONE);
    }

    static Subtask createSubtaskTwo(StatusTask statusTask) {
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
}