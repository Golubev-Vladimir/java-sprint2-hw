package main.model;

import java.time.LocalDateTime;
import java.util.Objects;

import static main.model.TypeTask.EPIC;
import static main.service.FileBackedTasksManagerLoader.TASK_TIME_FORMAT;

public class Epic extends Task {

    public Epic(long taskId, String taskName, String taskDescription, StatusTask taskStatus,
                LocalDateTime taskStarTime, long taskDuration) {
        super(taskId, taskName, taskDescription, taskStatus, taskStarTime, taskDuration);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Epic otherEpic = (Epic) o;
        return (getTaskId() == otherEpic.getTaskId())
                && Objects.equals(getTaskDescription(), otherEpic.getTaskDescription())
                && Objects.equals(getTaskStatus(), otherEpic.getTaskStatus())
                && Objects.equals(getTaskName(), otherEpic.getTaskName())
                && Objects.equals(getTaskStarTime(), otherEpic.getTaskStarTime())
                && Objects.equals(getTaskDuration(), otherEpic.getTaskDuration())
                && Objects.equals(getTaskEndTime(), otherEpic.getTaskEndTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTaskId(), getTaskName(), getTaskDescription(), getTaskStatus(),
        getTaskStarTime(), getTaskDuration(), getTaskEndTime());
    }

    @Override
    public String toString() {
        return getTaskId() +
                "," + EPIC +
                "," + getTaskName() +
                "," + getTaskStatus() +
                "," + getTaskDescription() +
                "," + getTaskStarTime().format(TASK_TIME_FORMAT) +
                "," + getTaskEndTime().format(TASK_TIME_FORMAT);
    }
}