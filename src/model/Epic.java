package model;

import java.util.Objects;

public class Epic extends Task {

    public Epic(long taskId, String taskName) {
        super(taskId, taskName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Epic otherEpic = (Epic) o;
        return (getTaskId() == otherEpic.getTaskId())
                && Objects.equals(getTaskDescription(), otherEpic.getTaskDescription())
                && Objects.equals(getTaskStatus(), otherEpic.getTaskStatus())
                && Objects.equals(getTaskName(), otherEpic.getTaskName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTaskId(), getTaskName(), getTaskDescription(), getTaskStatus());
    }

    @Override
    public String toString() {
        return "Epic: " + getTaskName() +
                ". УИН: " + getTaskId() +
                ". Статус: " + getTaskStatus();
    }
}