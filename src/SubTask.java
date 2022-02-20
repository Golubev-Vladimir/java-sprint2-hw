public class SubTask extends Task {

    public SubTask(String name, Object description, String status) {
        super(name, description, status);
    }

    @Override
    public String toString() {
        return "SubTask: " + getName() +
                ". Описание: " + getDescription() +
                ". УИН: " + getId() +
                ". Статус: " + getStatus();

    }
}