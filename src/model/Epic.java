package model;

public class Epic extends Task {

    public Epic(String name, long id) {
        super(name, id);
    }

    @Override
    public String toString() {
        return "Epic: " + getName() +
                ". УИН: " + getId() +
                ". Статус: " + getStatus();
    }
}