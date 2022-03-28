package model;

import java.util.Objects;

public class Subtask extends Task {
    private final long taskEpicId;

    public Subtask(long taskId, long taskEpicId, String taskName, String taskDescription, StatusTask taskStatus) {
        super(taskId, taskName, taskDescription, taskStatus);
        this.taskEpicId = taskEpicId;
    }

    public long getTaskEpicId() {
        return taskEpicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subtask otherSubtask = (Subtask) o;
        return (taskEpicId == otherSubtask.taskEpicId)
                && Objects.equals(getTaskDescription(), otherSubtask.getTaskDescription())
                && Objects.equals(getTaskStatus(), otherSubtask.getTaskStatus())
                && (getTaskId() == otherSubtask.getTaskId())
                && Objects.equals(getTaskName(), otherSubtask.getTaskName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTaskId(), taskEpicId, getTaskName(), getTaskDescription(), getTaskStatus());
    }

    @Override
    public String toString() {
        return "SubTask: " + getTaskName() +
                ". Описание: " + getTaskDescription() +
                ". УИН: " + getTaskId() +
                ". УИН Epic: " + getTaskEpicId() +
                ". Статус: " + getTaskStatus();
    }
}