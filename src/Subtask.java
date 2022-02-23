public class Subtask extends Task {
    private long idOfEpic;

    public Subtask(String name, String description, Status status) {
        super(name, description, status);
    }

    public long getIdOfEpic() {
        return idOfEpic;
    }

    public void setIdOfEpic(long idOfEpic) {
        this.idOfEpic = idOfEpic;
    }

    @Override
    public String toString() {
        return "SubTask: " + getName() +
                ". Описание: " + getDescription() +
                ". УИН: " + getId() +
                ". УИН Epic: " + getIdOfEpic() +
                ". Статус: " + getStatus();
    }
}