package service;

import model.*;

import static service.Handler.fileParseForLoadTasks;
import static service.Handler.splitComma;
import static service.InMemoryTaskManager.*;
import static service.Printer.println;

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
            String[] taskStringLine = splitComma(linesFile[i]);
            if (!linesFile[i].isBlank() && !linesFile[i].isEmpty()) { //отметаем пустые строки
                try {
                    switch (TypeTask.valueOf(taskStringLine[TYPE_COLUMN_INDEX])) {
                        case TASK -> createTaskFromHistory(taskStringLine);
                        case EPIC -> createEpicFromHistory(taskStringLine);
                        case SUBTASK -> createSubtaskFromHistory(taskStringLine);
                    }
                } catch (RuntimeException e) { // подстраховываемся
                    println("Необработанная строка: <" + linesFile[i]
                            + ">. Проверьте корректность параметров");
                }
            }
        }
        loadHistoryFromString(linesFile);
    }

    static void loadHistoryFromString(String[] linesFile) {
        String[] historyStringLine = splitComma(linesFile[linesFile.length - 1]);
        for (String items : historyStringLine) {
            long idTask = Long.parseLong(items);
            allTasks.stream().filter(task -> task.getTaskId() == idTask).forEach(inMemoryHistoryManager::add);
            if (idTask > idLastTask) {
                idLastTask = idTask;
            }
        }
    }

    static void createTaskFromHistory(String[] taskStringLine) {
        Task oldTask = new Task(Long.parseLong(taskStringLine[ID_COLUMN_INDEX]),
                taskStringLine[NAME_COLUMN_INDEX], taskStringLine[DESCRIPTION_COLUMN_INDEX],
                StatusTask.valueOf(taskStringLine[STATUS_COLUMN_INDEX]));
        tasks.put(oldTask.getTaskId(), oldTask);
        allTasks.add(oldTask);
    }

    static void createEpicFromHistory(String[] taskStringLine) {
        Epic oldTask = new Epic(Long.parseLong(taskStringLine[ID_COLUMN_INDEX]),
                taskStringLine[NAME_COLUMN_INDEX], taskStringLine[DESCRIPTION_COLUMN_INDEX],
                StatusTask.valueOf(taskStringLine[STATUS_COLUMN_INDEX]));
        epics.put(oldTask.getTaskId(), oldTask);
        allTasks.add(oldTask);
    }

    static void createSubtaskFromHistory(String[] taskStringLine) {
        Subtask oldTask = new Subtask(Long.parseLong(taskStringLine[ID_COLUMN_INDEX]),
                Long.parseLong(taskStringLine[EPIC_COLUMN_INDEX]), taskStringLine[NAME_COLUMN_INDEX],
                taskStringLine[DESCRIPTION_COLUMN_INDEX],
                StatusTask.valueOf(taskStringLine[STATUS_COLUMN_INDEX]));
        subtasks.put(oldTask.getTaskId(), oldTask);
        allTasks.add(oldTask);
    }
}