import java.util.ArrayList;

import java.util.Objects;

public class Epic extends Task {
    boolean statusNew = true; // дополнительные 2 переменные, чтобы присваивать статус
    boolean statusDone = true;

    public Epic(String name, ArrayList<SubTask> description) {
        super(name, description);
        if (description == null) { //если нет подзадач (SubTask)
            setStatus("NEW");
            return;
        }
        for (SubTask subTask : description) { // присваиваем статус эпику в соответствии с условиями
            if (!subTask.getStatus().equals("NEW")) {
                statusNew = false;
            }
            if (!subTask.getStatus().equals("DONE")) {
                statusDone = false;
            }
        }
        if (statusNew) {
            setStatus("NEW");
        } else if (statusDone) {
            setStatus("DONE");
        } else {
            setStatus("IN_PROGRESS");
        }
    }

    @Override
    public String toString() {
        return "Epic: " + getName() +
                ". Описание: " + getDescription() +
                ". УИН: " + getId() +
                ". Статус: " + getStatus();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Epic otherEpic = (Epic) obj;
        return Objects.equals(getName(), otherEpic.getName()) &&
                Objects.equals(getDescription(), otherEpic.getDescription()) &&
                (getId() == otherEpic.getId());
    }
}