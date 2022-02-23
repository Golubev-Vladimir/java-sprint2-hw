import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    private long idCounter = 0;
    long idEpicForSubtask;
    boolean statusNew;
    boolean statusDone;
    ArrayList<Subtask> subtaskList;
    private final HashMap<Long, Task> tasks = new HashMap<>();
    private final HashMap<Long, Epic> epics = new HashMap<>();
    private final HashMap<Long, Subtask> subtasks = new HashMap<>();

    public HashMap<Long, Task> getTasks() {
        return tasks;
    }

    public HashMap<Long, Epic> getEpics() {
        return epics;
    }

    public HashMap<Long, Subtask> getSubtasks() {
        return subtasks;
    }

    Object getTasksByType(Type type) {
        if (type == null) {
            return "Неправильно заданы параметры";
        }
        switch (type) {
            case Task:
                return tasks;
            case Epic:
                return epics;
            case Subtask:
                return subtasks;
        }
        return null;
    }

    void deleteAllTasks() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
    }

    Task getTaskById(long id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        }
        return null;
    }

    Task getEpicById(long id) {
        if (epics.containsKey(id)) {
            return epics.get(id);
        }
        return null;
    }

    Task getSubtaskById(long id) {
        if (subtasks.containsKey(id)) {
            return subtasks.get(id);
        }
        return null;
    }

    public void saveTask(Task task) {
        tasks.put(++idCounter, task);
        task.setId(idCounter);
    }

    public void saveEpic(Epic epic) {
        epics.put(++idCounter, epic);
        epic.setId(idCounter);
        subtaskList = new ArrayList<>();
        epic.setSubtaskOfEpic(subtaskList);
        idEpicForSubtask = idCounter;
        epic.setStatus(Status.NEW);
    }

    public void saveSubtask(Subtask subtask) {
        subtasks.put(++idCounter, subtask);
        subtask.setId(idCounter);
        subtaskList.add(subtask);
        subtask.setIdOfEpic(idEpicForSubtask);

        statusNew = true;
        statusDone = true;

        for (Subtask i : subtaskList) {
            if (!i.getStatus().equals(Status.NEW)) {
                statusNew = false;
            }
            if (!i.getStatus().equals(Status.DONE)) {
                statusDone = false;
            }
        }
        if (statusNew) {
            epics.get(idEpicForSubtask).setStatus(Status.NEW);
        } else if (statusDone) {
            epics.get(idEpicForSubtask).setStatus(Status.DONE);
        } else {
            epics.get(idEpicForSubtask).setStatus(Status.IN_PROGRESS);
        }
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
        epic.setStatus(Status.NEW);
        statusNew = true;
        statusDone = true;
        for (Subtask subtask : epic.getSubtaskOfEpic()) {
            subtasks.remove(subtask.getId());
            subtasks.put(subtask.getId(), subtask);

            if (!subtask.getStatus().equals(Status.NEW)) {
                statusNew = false;
            }
            if (!subtask.getStatus().equals(Status.DONE)) {
                statusDone = false;
            }
        }
        if (statusNew) {
            epic.setStatus(Status.NEW);
        } else if (statusDone) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        statusNew = true;
        statusDone = true;
        for (Subtask i : epics.get(subtask.getIdOfEpic()).getSubtaskOfEpic()) {
            if (i.getId() == subtask.getId()) {
                epics.get(subtask.getIdOfEpic()).getSubtaskOfEpic().remove(i);
                epics.get(subtask.getIdOfEpic()).getSubtaskOfEpic().add(subtask);
                break;
            }
        }
        for (Subtask j : epics.get(subtask.getIdOfEpic()).getSubtaskOfEpic()) {
            if (!j.getStatus().equals(Status.NEW)) {
                statusNew = false;
            }
            if (!j.getStatus().equals(Status.DONE)) {
                statusDone = false;
            }
        }
        if (statusNew) {
            epics.get(subtask.getIdOfEpic()).setStatus(Status.NEW);
        } else if (statusDone) {
            epics.get(subtask.getIdOfEpic()).setStatus(Status.DONE);
        } else {
            epics.get(subtask.getIdOfEpic()).setStatus(Status.IN_PROGRESS);
        }
    }

    void deleteTaskById(long idForDelete) {
        for (long i : tasks.keySet()) {
            if (i == idForDelete) {
                tasks.remove(i);
                return;
            }
        }
        for (long i : epics.keySet()) {
            if (i == idForDelete) {
                epics.remove(i);
                ArrayList<Long> listSubtaskForDelete = new ArrayList<>();
                for (Subtask subtask : subtasks.values()) {
                    if (subtask.getIdOfEpic() == i) {
                        listSubtaskForDelete.add(subtask.getId());
                    }
                }
                for (long j : listSubtaskForDelete) {
                    subtasks.remove(j);
                }
                return;
            }
        }
        for (long i : subtasks.keySet()) {
            if (i == idForDelete) {
                idEpicForSubtask = subtasks.get(i).getIdOfEpic(); //узнали Epic
                for (Epic epic : epics.values()) {
                    if (epic.getId() == idEpicForSubtask) {
                        epic.getSubtaskOfEpic().remove(subtasks.get(i));
                    }
                }
                subtasks.remove(i);
                return;
            }
        }
    }

    Subtask getSubTaskOfEpic(long idOfEpic) {
        for (Subtask subtask : epics.get(idOfEpic).getSubtaskOfEpic()) {
            return subtask;
        }
        return null;
    }

    @Override
    public String toString() {
        return tasks.values() + "\n" + epics.values() + "\n" + subtasks.values();
    }
}