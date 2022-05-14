package service;

import model.*;

import static service.Handler.fileParseForLoadHistory;
import static service.Handler.fileParseForLoadTasks;
import static service.InMemoryTaskManager.*;
import static service.Printer.print;

public class FileBackedTasksManagerLoader {
    private static final int ID_COLUMN_INDEX = 0;
    private static final int TYPE_COLUMN_INDEX = 1;
    private static final int NAME_COLUMN_INDEX = 2;
    private static final int STATUS_COLUMN_INDEX = 3;
    private static final int DESCRIPTION_COLUMN_INDEX = 4;
    private static final int EPIC_COLUMN_INDEX = 5;

    private static Task oldTask;

    private FileBackedTasksManagerLoader() {
    }

    static void loadTaskAndHistoryFromFile(String value) {
        String[] linesFile = fileParseForLoadTasks(value); //парсим единожды для экономии ресурсов
        for (int i = 1; i < linesFile.length - 1; i++) { //не берем первую (шапку) и последнюю (история) строки,экономим
            String[] taskStringLine = linesFile[i].split(Handler.SPLITTER);
            if (!linesFile[i].isBlank() && !linesFile[i].isEmpty()) { //отметаем пустые строки
                try {
                    switch (TypeTask.valueOf(taskStringLine[TYPE_COLUMN_INDEX])) {
                        case TASK:
                            createTaskFromHistory(taskStringLine);
                            break;
                        case EPIC:
                            createEpicFromHistory(taskStringLine);
                            break;
                        case SUBTASK:
                            createSubtaskFromHistory(taskStringLine);
                            break;
                        default:
                            break;
                    }
                } catch (RuntimeException e) { // подстраховываемся
                    print("Необработанная строка: <".toUpperCase() + linesFile[i]
                            + ">. Проверьте корректность параметров".toUpperCase());
                }
            }
        }
        loadHistoryFromString(value);
    }

    static void loadHistoryFromString(String value) {
        for (String items : fileParseForLoadHistory(value)) {
            for (Task task : allOldTasks) {
                if (task.getTaskId() == Long.parseLong(items)) {
                    inMemoryHistoryManager.add(task);
                }
            }
        }
    }

    static void createTaskFromHistory(String[] taskStringLine) {
        oldTask = new Task(Long.parseLong(taskStringLine[ID_COLUMN_INDEX]),
                taskStringLine[NAME_COLUMN_INDEX], taskStringLine[DESCRIPTION_COLUMN_INDEX],
                StatusTask.valueOf(taskStringLine[STATUS_COLUMN_INDEX]));
        tasks.put(oldTask.getTaskId(), oldTask);
        allOldTasks.add(oldTask);
        oldTask = null;
    }

    static void createEpicFromHistory(String[] taskStringLine) {
        oldTask = new Epic(Long.parseLong(taskStringLine[ID_COLUMN_INDEX]),
                taskStringLine[NAME_COLUMN_INDEX], taskStringLine[DESCRIPTION_COLUMN_INDEX],
                StatusTask.valueOf(taskStringLine[STATUS_COLUMN_INDEX]));
        epics.put(oldTask.getTaskId(), (Epic) oldTask);
        allOldTasks.add(oldTask);
        oldTask = null;
    }

    static void createSubtaskFromHistory(String[] taskStringLine) {
        oldTask = new Subtask(Long.parseLong(taskStringLine[ID_COLUMN_INDEX]),
                Long.parseLong(taskStringLine[EPIC_COLUMN_INDEX]), taskStringLine[NAME_COLUMN_INDEX],
                taskStringLine[DESCRIPTION_COLUMN_INDEX],
                StatusTask.valueOf(taskStringLine[STATUS_COLUMN_INDEX]));
        subtasks.put(oldTask.getTaskId(), (Subtask) oldTask);
        allOldTasks.add(oldTask);
        oldTask = null;
    }
}