public class Task {
    private String name;
    private String description;
    private Status status;
    private long id;

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task() {
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

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public void setId(long id) {
        this.id = id;
    }
}