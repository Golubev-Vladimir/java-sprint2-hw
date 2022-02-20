import java.util.ArrayList;

import java.util.HashMap;

public class Manager {
    private int id = 0;
    private HashMap<Integer, Object> tasks = new HashMap<>(); // 1. Хранение
    private HashMap<Integer, Object> epics = new HashMap<>();
    private HashMap<Integer, Object> subTasks = new HashMap<>();

    ArrayList<Object> getAllTasks() { // 2.1. Получение списка всех задач
        ArrayList<Object> allTasks = new ArrayList<>();
        allTasks.add(tasks);
        allTasks.add(epics);
        allTasks.add(subTasks); //наверное можно было subTasks не включать в allTasks , т.к. они уже включены в epics
        return allTasks;
    }

    Object getTasksByType(String type) { // отдельный метод, хотя можно использовать простое геттер, условия задачи???
        if (type == null) {
            return "Неправильно заданы параметры";
        }
        switch (type) {
            case "Task":
                return tasks;
            case "Epic":
                return epics;
            case "SubTask":
                return subTasks;
        }
        return "Такой задачи пока нет";
    }

    void deleteAllTasks() {                       // 2.2. Удаление всех задач
        tasks.clear();
        epics.clear();
        subTasks.clear();
    }

    Object getTaskById(int id) {                 //2.3 Получение по идентификатору
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        }
        if (epics.containsKey(id)) {
            return epics.get(id);
        }
        if (subTasks.containsKey(id)) {
            return subTasks.get(id);
        }
        return null;
    }

    public Object createTask(Object object) {             //2.4 Создание. Сам объект должен передаваться в качестве параметра.
        if (object == null) {
            return "Неправильно заданы параметры";
        }
        switch (object.getClass().getName()) {
            case "Task":
                ((Task) object).setId(++id);
                tasks.put(((Task) object).getId(), object);
                break;
            case "Epic":
                ((Epic) object).setId(++id);
                epics.put(((Epic) object).getId(), object);
                break;
            case "SubTask":
                ((SubTask) object).setId(++id);
                subTasks.put(((SubTask) object).getId(), object);
                break;
            default:
                System.out.println("Не сохранено. Неизвестный тип задачи");
        }
        return object; // возвращаем для удобства самопроверки в Main
    }

    public void updateTask(Object object) {//2.5 Обновление.Новая версия объекта c верным id передаются в виде параметра
        if (object == null) {
            System.out.println("Неправильно заданы параметры");
            switch (object.getClass().getName()) {
                case "Task":
                    tasks.put(((Task) object).getId(), object);
                    break;
                case "Epic":
                    epics.put(((Epic) object).getId(), object);
                    break;
                case "SubTask":
                    subTasks.put(((SubTask) object).getId(), object);
                    break;
                default:
                    System.out.println("Не сохранено. Неизвестный тип задачи");
            }
        }
    }

    void deleteTaskById(int id) {                   //2.6 Удаление по идентификатору.
        for (int i : tasks.keySet()) {
            if (i == id) {
                tasks.remove(i);
                return;
            }
        }
        for (int i : epics.keySet()) {
            if (i == id) {
                epics.remove(i);
                return;
            }
        }
        for (int i : subTasks.keySet()) {
            if (i == id) {
                subTasks.remove(i);
                return;
            }
        }
    }

    Object getSubTaskOfEpic(Epic epic) { //3.1 Получение списка всех подзадач определённого эпика.
        for (Object i : epics.values())
            if (i.equals(epic)) {
                return i;
            }
        return null;
    }

    @Override
    public String toString() { // для вывода в консоль списка всех задач.
        return tasks.values() + "\n" + epics.values() + "\n" + subTasks.values();
    }

    public HashMap<Integer, Object> getTasks() {
        return tasks;
    }

    public void setTasks(HashMap<Integer, Object> tasks) {
        this.tasks = tasks;
    }

    public HashMap<Integer, Object> getEpics() {
        return epics;
    }

    public void setEpics(HashMap<Integer, Object> epics) {
        this.epics = epics;
    }

    public HashMap<Integer, Object> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(HashMap<Integer, Object> subTasks) {
        this.subTasks = subTasks;
    }
}