package model;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subtask otherSubtask = (Subtask) o;
        return Objects.equals(getName(), otherSubtask.getName())
                && Objects.equals(getDescription(), otherSubtask.getDescription())
                && Objects.equals(getStatus(), otherSubtask.getStatus())
                && (getId() == otherSubtask.getId())
                && (epicId == otherSubtask.epicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getStatus(), getId(), epicId);
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