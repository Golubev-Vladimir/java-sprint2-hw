package model;

import java.time.LocalDateTime;
import java.util.Objects;

import static model.TypeTask.SUBTASK;
import static service.FileBackedTasksManagerLoader.TASK_TIME_FORMAT;

public class Subtask extends Task {
    private final long taskEpicId;

    public Subtask(long taskId, long taskEpicId, String taskName, String taskDescription, StatusTask taskStatus,
                   LocalDateTime taskStarTime, long taskDuration) {
        super(taskId, taskName, taskDescription, taskStatus, taskStarTime, taskDuration);
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
                && Objects.equals(getTaskName(), otherSubtask.getTaskName())
                && Objects.equals(getTaskStarTime(), otherSubtask.getTaskStarTime())
                && Objects.equals(getTaskDuration(), otherSubtask.getTaskDuration())
                && Objects.equals(getTaskEndTime(), otherSubtask.getTaskEndTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTaskId(), taskEpicId, getTaskName(), getTaskDescription(), getTaskStatus(),
                getTaskStarTime(), getTaskDuration(), getTaskEndTime());
    }

    @Override
    public String toString() {
        return getTaskId() +
                "," + SUBTASK +
                "," + getTaskName() +
                "," + getTaskStatus() +
                "," + getTaskDescription() +
                "," + getTaskStarTime().format(TASK_TIME_FORMAT) +
                "," + getTaskEndTime().format(TASK_TIME_FORMAT) +
                "," + getTaskEpicId();
    }
}