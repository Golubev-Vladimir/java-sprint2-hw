package main.model;

import main.service.FileBackedTasksManagerLoader;

import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    protected final long taskId;
    protected final String taskName;
    protected final String taskDescription;

    protected LocalDateTime taskStarTime;
    protected LocalDateTime taskEndTime;
    protected StatusTask taskStatus;
    protected long taskDuration;

    public Task(long taskId, String taskName, String taskDescription, StatusTask taskStatus,
                LocalDateTime taskStarTime, long taskDuration) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
        this.taskStarTime = taskStarTime;
        this.taskDuration = taskDuration;
        this.taskEndTime = taskStarTime.plusMinutes(taskDuration);
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

    public void setTaskStarTime(LocalDateTime taskStarTime) {
        this.taskStarTime = taskStarTime;
    }

    public void setTaskDuration(long taskDuration) {
        this.taskDuration = taskDuration;
    }

    public void setTaskEndTime(LocalDateTime taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    public long getTaskId() {
        return taskId;
    }

    public LocalDateTime getTaskStarTime() {
        return taskStarTime;
    }

    public long getTaskDuration() {
        return taskDuration;
    }

    public LocalDateTime getTaskEndTime() {
        return taskEndTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task otherTask = (Task) o;
        return (taskId == otherTask.taskId)
                && Objects.equals(taskDescription, otherTask.taskDescription)
                && Objects.equals(taskStatus, otherTask.taskStatus)
                && Objects.equals(taskName, otherTask.taskName)
                && Objects.equals(taskStarTime, otherTask.taskStarTime)
                && Objects.equals(taskDuration, otherTask.taskDuration)
                && Objects.equals(taskEndTime, otherTask.taskEndTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, taskName, taskDescription, taskStatus, taskStarTime, taskDuration, taskEndTime);
    }

    @Override
    public String toString() {
        return getTaskId() +
                "," + TypeTask.TASK +
                "," + getTaskName() +
                "," + getTaskStatus() +
                "," + getTaskDescription() +
                "," + getTaskStarTime().format(FileBackedTasksManagerLoader.TASK_TIME_FORMAT) +
                "," + getTaskEndTime().format(FileBackedTasksManagerLoader.TASK_TIME_FORMAT);
    }
}