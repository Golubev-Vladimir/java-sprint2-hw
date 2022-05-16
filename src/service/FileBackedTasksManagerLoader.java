package service;

import model.*;

import static service.Handler.fileParseForLoadTasks;
import static service.Handler.split;
import static service.InMemoryTaskManager.*;
import static service.Printer.print;

public class FileBackedTasksManagerLoader {
    private static final int ID_COLUMN_INDEX = 0;
    private static final int TYPE_COLUMN_INDEX = 1;
    private static final int NAME_COLUMN_INDEX = 2;
    private static final int STATUS_COLUMN_INDEX = 3;
    private static final int DESCRIPTION_COLUMN_INDEX = 4;
    private static final int EPIC_COLUMN_INDEX = 5;

    private FileBackedTasksManagerLoader() {
    }

    static void loadTaskAndHistoryFromFile(String value) {
        String[] linesFile = fileParseForLoadTasks(value); //парсим единожды для экономии ресурсов
        for (int i = 1; i < linesFile.length - 1; i++) { //не берем первую (шапку) и последнюю (история) строки,экономим
            String[] taskStringLine = split(linesFile[i]);
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
                    print("Необработанная строка: <" + linesFile[i]
                            + ">. Проверьте корректность параметров");
                }
            }
        }
        loadHistoryFromString(linesFile);
    }

    static void loadHistoryFromString(String[] linesFile) {
        String[] historyStringLine = split(linesFile[linesFile.length - 1]);
        for (String items : historyStringLine) {
            for (Task task : allOldTasks) {
                if (task.getTaskId() == Long.parseLong(items)) {
                    inMemoryHistoryManager.add(task);
                }
            }
        }
    }

    static void createTaskFromHistory(String[] taskStringLine) {
        Task oldTask;
        oldTask = new Task(Long.parseLong(taskStringLine[ID_COLUMN_INDEX]),
                taskStringLine[NAME_COLUMN_INDEX], taskStringLine[DESCRIPTION_COLUMN_INDEX],
                StatusTask.valueOf(taskStringLine[STATUS_COLUMN_INDEX]));
        tasks.put(oldTask.getTaskId(), oldTask);
        allOldTasks.add(oldTask);
    }

    static void createEpicFromHistory(String[] taskStringLine) {
        Epic oldTask;
        oldTask = new Epic(Long.parseLong(taskStringLine[ID_COLUMN_INDEX]),
                taskStringLine[NAME_COLUMN_INDEX], taskStringLine[DESCRIPTION_COLUMN_INDEX],
                StatusTask.valueOf(taskStringLine[STATUS_COLUMN_INDEX]));
        epics.put(oldTask.getTaskId(), oldTask);
        allOldTasks.add(oldTask);
    }

    static void createSubtaskFromHistory(String[] taskStringLine) {
        Subtask oldTask;
        oldTask = new Subtask(Long.parseLong(taskStringLine[ID_COLUMN_INDEX]),
                Long.parseLong(taskStringLine[EPIC_COLUMN_INDEX]), taskStringLine[NAME_COLUMN_INDEX],
                taskStringLine[DESCRIPTION_COLUMN_INDEX],
                StatusTask.valueOf(taskStringLine[STATUS_COLUMN_INDEX]));
        subtasks.put(oldTask.getTaskId(), oldTask);
        allOldTasks.add(oldTask);
    }
}