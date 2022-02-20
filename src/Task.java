import java.util.ArrayList;

public class Task {
    private String name;
    private Object description;
    private String status;
    private int id;

    public Task(String name, ArrayList<SubTask> description) { //конструктор для наследника Epic
        this.name = name;
        this.description = description;
    }

    public Task(String name, Object description, String status) { //конструктор для наследника SubTask
        this.name = name;
        this.description = description;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task: " + getName() +
                ". Описание: " + getDescription() +
                ". УИН: " + getId() +
                ". Статус: " + getStatus();

    }

    public String getName() {
        return name;
    }

    public Object getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }
}