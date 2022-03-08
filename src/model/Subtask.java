package model;

import java.util.Objects;

public class Subtask extends Task {
    private long epicId;

    public Subtask(long id, long epicId, String name, String description, Status status) {
        super(id, name, description, status);
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
        return (epicId == otherSubtask.epicId)
                && Objects.equals(getDescription(), otherSubtask.getDescription())
                && Objects.equals(getStatus(), otherSubtask.getStatus())
                && (getId() == otherSubtask.getId())
                && Objects.equals(getName(), otherSubtask.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), epicId, getName(), getDescription(), getStatus());
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