package service;

import model.Epic;
import model.Subtask;
import model.Task;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class FileBackedTasksManager extends InMemoryTaskManager {
    FileReader readfileTasksHistory;

    public FileBackedTasksManager(String pathFile) throws IOException {
        readfileTasksHistory = new FileReader(pathFile, StandardCharsets.UTF_8);

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
        super.getTaskById(id);
        save();
        return getTaskById(id);
    }

    @Override
    public Optional<Epic> getEpicById(long id) {
        super.getEpicById(id);
        save();
        return getEpicById(id);
    }

    @Override
    public Optional<Subtask> getSubtaskById(long id) {
        super.getSubtaskById(id);
        save();
        return getSubtaskById(id);
    }

    public void save() {
        try (Writer writefileTasksHistory = new FileWriter("fileTasks.csv", StandardCharsets.UTF_8)) {
            writefileTasksHistory.write("id,type,name,status,description,epic\n");
            for (Task task : getTasks().values()) {
                writefileTasksHistory.write(task + "\n");
            }
            for (Epic epic : getEpics().values()) {
                writefileTasksHistory.write(epic + "\n");
            }
            for (Subtask subtask : getSubtasks().values()) {
                writefileTasksHistory.write(subtask + "\n");
            }
            writefileTasksHistory.write("\n");

            for (Task task : history()) {
                writefileTasksHistory.write(task.getTaskId() + ",");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}