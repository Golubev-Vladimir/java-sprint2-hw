package model;

import java.util.Objects;

public class Epic extends Task {

    public Epic(long id, String name) {
        super(id, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Epic otherEpic = (Epic) o;
        return (getId() == otherEpic.getId())
                && Objects.equals(getDescription(), otherEpic.getDescription())
                && Objects.equals(getStatus(), otherEpic.getStatus())
                && Objects.equals(getName(), otherEpic.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getStatus());
    }

    @Override
    public String toString() {
        return "Epic: " + getName() +
                ". УИН: " + getId() +
                ". Статус: " + getStatus();
    }
}