package model;

import java.util.Objects;

public class Task {
    protected final long taskId;
    protected final String taskName;
    protected String taskDescription;
    protected StatusTask taskStatus;

    public Task(long taskId, String taskName, String taskDescription, StatusTask taskStatus) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public StatusTask getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(StatusTask taskStatus) {
        this.taskStatus = taskStatus;
    }

    public long getTaskId() {
        return taskId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task otherTask = (Task) o;
        return (taskId == otherTask.taskId)
                && Objects.equals(taskDescription, otherTask.taskDescription)
                && Objects.equals(taskStatus, otherTask.taskStatus)
                && Objects.equals(taskName, otherTask.taskName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, taskName, taskDescription, taskStatus);
    }

    @Override
    public String toString() {
        return getTaskId() +
                "," + TypeTask.TASK +
                "," + getTaskName() +
                "," + getTaskStatus() +
                "," + getTaskDescription();
    }
}