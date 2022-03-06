package model;

import java.util.Objects;

public class Epic extends Task {

    public Epic(String name, long id) {
        super(name, id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Epic otherEpic = (Epic) o;
        return Objects.equals(getName(), otherEpic.getName())
                && Objects.equals(getDescription(), otherEpic.getDescription())
                && Objects.equals(getStatus(), otherEpic.getStatus())
                && (getId() == otherEpic.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getStatus(), getId());
    }

    @Override
    public String toString() {
        return "Epic: " + getName() +
                ". УИН: " + getId() +
                ". Статус: " + getStatus();
    }
}