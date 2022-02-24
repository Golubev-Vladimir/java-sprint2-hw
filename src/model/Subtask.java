package model;

public class Subtask extends Task {
    private long epicId;

    public Subtask(String name, String description, Status status, long id, long epicId) {
        super(name, description, status, id);
        this.epicId = epicId;
    }

    public long getEpicId() {
        return epicId;
    }

    public void setEpicId(long epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "SubTask: " + getName() +
                ". Описание: " + getDescription() +
                ". УИН: " + getId() +
                ". УИН Epic: " + getEpicId() +
                ". Статус: " + getStatus();
    }
}