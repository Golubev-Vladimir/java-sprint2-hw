import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();

        String name = "Уборка"; //1 Task
        ArrayList<String> description = new ArrayList<>();
        description.add("Вытереть пыль");
        description.add("Пропылесосить");
        description.add("Помыть пол");
        String status = "NEW";
        manager.createTask(new Task(name, description, status));  // Создали 1 Task

        name = "Сходить в магазин"; //2 Task
        ArrayList<String> description1 = new ArrayList<>();
        description1.add("Купить молоко");
        description1.add("Купить мясо");
        description1.add("Купить хлеб");
        status = "IN_PROGRESS";
        manager.createTask(new Task(name, description1, status));  // Создали 2 Task

        ArrayList<String> description22 = new ArrayList<>(); //создаем описание 1 subTask для 1 Epic
        description22.add("Найти риелтора");
        description22.add("Выбрать нужный вариант");

        ArrayList<String> description33 = new ArrayList<>(); //создаем описание 2 subTask 1 Epic
        description33.add("Купить коробки");
        description33.add("Собрать вещи");

        name = "ПЕРЕЕЗД"; //  СОЗДАЕМ 1 Epic
        ArrayList<SubTask> description2 = new ArrayList<>();
        description2.add((SubTask) manager.createTask(new SubTask("Купить новую квартиру", description22, "DONE")));
        description2.add((SubTask) manager.createTask(new SubTask("Перевести вещи", description33, "DONE")));
        manager.createTask(new Epic(name, description2)); //СОЗДАЛИ 1 Epic


        ArrayList<String> description32 = new ArrayList<>(); //создаем описание 1 сабтаск для 2 Epic
        description32.add("Купить запчасти");
        description32.add("Тех. обслуживание");

        name = "РЕМОНТ АВТО"; //  СОЗДАЕМ 2 Epic
        ArrayList<SubTask> description3 = new ArrayList<>();
        description3.add((SubTask) manager.createTask(new SubTask("Приехать в Автосервис", description32, "NEW")));
        manager.createTask(new Epic(name, description3)); //СОЗДАЛИ 2 Epic


        ((Task) manager.getSubTasks().get(6)).setStatus("DONE");

        for(Object i: manager.getTasks().values()) {
            System.out.println(i);
        }
        for(Object i: manager.getEpics().values()) {
            System.out.println(i);
        }
        for(Object i: manager.getSubTasks().values()) {
            System.out.println(i);
        }
    }
}