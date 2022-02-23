import java.util.List;

public class Epic extends Task {
    private List<Subtask> subtaskOfEpic;

    public Epic(String name, List<Subtask> subtaskOfEpic) {
        super();
        this.setName(name);
        this.subtaskOfEpic = subtaskOfEpic;
    }

    public List<Subtask> getSubtaskOfEpic() {
        return subtaskOfEpic;
    }

    public void setSubtaskOfEpic(List<Subtask> subtaskOfEpic) {
        this.subtaskOfEpic = subtaskOfEpic;
    }

    @Override
    public String toString() {
        return "Epic: " + getName() +
                ". Описание: " + getSubtaskOfEpic() +
                ". УИН: " + getId() +
                ". Статус: " + getStatus();
    }
}