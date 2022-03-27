package model;

import java.util.Objects;

public class Task {
    private final long id;
    private final String name;
    private String description;
    private Status status;

    public Task(long id, String name, String description, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task otherTask = (Task) o;
        return (id == otherTask.id)
                && Objects.equals(description, otherTask.description)
                && Objects.equals(status, otherTask.status)
                && Objects.equals(name, otherTask.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, status);
    }

    @Override
    public String toString() {
        return "Task: " + getName() +
                ". Описание: " + getDescription() +
                ". УИН: " + getId() +
                ". Статус: " + getStatus();
    }
}