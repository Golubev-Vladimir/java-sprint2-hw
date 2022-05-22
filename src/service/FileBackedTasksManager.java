package service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static service.FileBackedTasksManagerLoader.loadTaskAndHistoryFromFile;
import static service.Printer.println;

import model.*;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private final String pathFile;

    public FileBackedTasksManager(String pathFile) {
        this.pathFile = pathFile;
        loadTaskAndHistoryFromFile(pathFile);
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
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public Optional<Task> getTaskById(long id) {
        Optional<Task> taskOptional = Optional.ofNullable(tasks.get(id));
        taskOptional.ifPresentOrElse(inMemoryHistoryManager::add,
                () -> println("Такой task c id = " + id + " в списке Tasks нет"));
        save();
        return taskOptional;
    }

    @Override
    public Optional<Epic> getEpicById(long id) {
        Optional<Epic> epicOptional = Optional.ofNullable(epics.get(id));
        epicOptional.ifPresentOrElse(inMemoryHistoryManager::add,
                () -> println("Такого epic c id = " + id + " в списке Epic нет"));
        save();
        return epicOptional;
    }

    @Override
    public Optional<Subtask> getSubtaskById(long id) {
        Optional<Subtask> subtaskOptional = Optional.ofNullable(subtasks.get(id));
        subtaskOptional.ifPresentOrElse(inMemoryHistoryManager::add,
                () -> println("Такого subtask c id = " + id + " в списке Subtask нет"));
        save();
        return subtaskOptional;
    }

    private void save() {
        try {
            if (pathFile == null) {
                throw new ManagerSaveException("Не доступа к файлу для записи данных: ", pathFile);
            }
        } catch (ManagerSaveException exception) {
            println(exception.getDetailMessage());
        }
        try (FileWriter writeFileTasksHistory = new FileWriter(pathFile, StandardCharsets.UTF_8)) {
            writeFileTasksHistory.write(
                    "id,type,name,status,description, startTime, endTime, epic" + Handler.TASK_LINE_DELIMITER);
            for (Task task : allTasks) {
                writeFileTasksHistory.write(task + Handler.TASK_LINE_DELIMITER);
            }
            writeFileTasksHistory.write(Handler.TASK_LINE_DELIMITER);
            writeFileTasksHistory.write(putHistoryToString(inMemoryHistoryManager));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String putHistoryToString(HistoryManager manager) {
        StringBuilder historyViewedTasks = new StringBuilder();
        for (Task task : manager.getHistory()) {
            historyViewedTasks.append(task.getTaskId()).append(Handler.SPLITTER);
        }
        return historyViewedTasks.toString();
    }
}