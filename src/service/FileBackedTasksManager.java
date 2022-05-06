package service;

import model.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static service.Handler.fileParseForLoadHistory;
import static service.Handler.fileParseForLoadTasks;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private final String pathFile;

    public FileBackedTasksManager(String pathFile) {
        this.pathFile = pathFile;
        loadTaskAndHistoryFromFile(pathFile);
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void saveTask(Task task) {
        super.saveTask(task);
        save();
    }

    @Override
    public void saveEpic(Epic epic) {
        super.saveEpic(epic);
        save();
    }

    @Override
    public void saveSubtask(Subtask subtask) {
        super.saveSubtask(subtask);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void updateEpicStatus(long epicId) {
        super.updateEpicStatus(epicId);
        save();
    }

    @Override
    public void deleteTaskById(long idDelete) {
        super.deleteTaskById(idDelete);
        save();
    }

    @Override
    public void deleteEpicById(long idDelete) {
        super.deleteEpicById(idDelete);
        save();
    }

    @Override
    public void deleteSubtaskById(long idDelete) {
        super.deleteSubtaskById(idDelete);
        save();
    }

    @Override
    public Optional<Task> getTaskById(long id) {
        Optional<Task> taskOptional = Optional.ofNullable(tasks.get(id));
        taskOptional.ifPresentOrElse(inMemoryHistoryManager::add,
                () -> System.out.println("Такой task c id = " + id + " в списке Tasks нет"));
        save();
        return taskOptional;
    }

    @Override
    public Optional<Epic> getEpicById(long id) {
        Optional<Epic> epicOptional = Optional.ofNullable(epics.get(id));
        epicOptional.ifPresentOrElse(inMemoryHistoryManager::add,
                () -> System.out.println("Такого epic c id = " + id + " в списке Epic нет"));
        save();
        return epicOptional;
    }

    @Override
    public Optional<Subtask> getSubtaskById(long id) {
        Optional<Subtask> subtaskOptional = Optional.ofNullable(subtasks.get(id));
        subtaskOptional.ifPresentOrElse(inMemoryHistoryManager::add,
                () -> System.out.println("Такого subtask c id = " + id + " в списке Subtask нет"));
        save();
        return subtaskOptional;
    }

    public void save() {
        try (FileWriter writeFileTasksHistory = new FileWriter(pathFile, StandardCharsets.UTF_8)) {
            if (pathFile == null) {
                throw new ManagerSaveException("Ошибка записи в файл");
            }
            writeFileTasksHistory.write("id,type,name,status,description,epic\n");
            for (Task task : getTasks().values()) {
                writeFileTasksHistory.write(task + "\n");
            }
            for (Epic epic : getEpics().values()) {
                writeFileTasksHistory.write(epic + "\n");
            }
            for (Subtask subtask : getSubtasks().values()) {
                writeFileTasksHistory.write(subtask + "\n");
            }
            writeFileTasksHistory.write("\n");
            writeFileTasksHistory.write(putHistoryToString(inMemoryHistoryManager));

        } catch (ManagerSaveException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    void loadTaskAndHistoryFromFile(String value) {
        Task oldTask;
        for (String line : fileParseForLoadTasks(value)) {
            String[] lineContains = line.split(",");
            for (String items : lineContains) {
                if (items.equals(TypeTask.TASK.name())) {
                    oldTask = new Task(Long.parseLong(lineContains[0]),
                            lineContains[2], lineContains[4], StatusTask.valueOf(lineContains[3]));
                    tasks.put(oldTask.getTaskId(), oldTask);
                    allOldTasks.add(oldTask);
                } else if (items.equals(TypeTask.EPIC.name())) {
                    oldTask = new Epic(Long.parseLong(lineContains[0]),
                            lineContains[2], lineContains[4], StatusTask.valueOf(lineContains[3]));
                    epics.put(oldTask.getTaskId(), (Epic) oldTask);
                    allOldTasks.add(oldTask);
                } else if (items.equals(TypeTask.SUBTASK.name())) {
                    oldTask = new Subtask(Long.parseLong(lineContains[0]),
                            Long.parseLong(lineContains[5]), lineContains[2], lineContains[4],
                            StatusTask.valueOf(lineContains[3]));
                    subtasks.put(oldTask.getTaskId(), (Subtask) oldTask);
                    allOldTasks.add(oldTask);
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

    static String putHistoryToString(HistoryManager manager) {
        StringBuilder historyViewedTasks = new StringBuilder();
        for (Task task : manager.getHistory()) {
            historyViewedTasks.append(task.getTaskId()).append(",");
        }
        return historyViewedTasks.toString();
    }
}