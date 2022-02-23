import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {  //THis CLASS WAS CREATED FOR TESTING APPLICATION
        Manager manager = new Manager();

        String name = "Уборка";
        String description = "Вытереть пыль, Пропылесосить, Помыть пол";
        manager.saveTask(new Task(name, description, Status.NEW));

        name = "Сходить в магазин";
        description = "Купить молоко, Купить мясо, Купить хлеб";
        manager.saveTask(new Task(name, description, Status.IN_PROGRESS));

        manager.saveEpic(new Epic("ПЕРЕЕЗД", new ArrayList<>()));
        manager.saveSubtask(new Subtask("Купить новую квартиру", "Найти риелтора, Выбрать нужный вариант",
                Status.NEW));
        manager.saveSubtask(new Subtask("Перевести вещи", "Купить коробки, Собрать вещи", Status.DONE));

        manager.saveEpic(new Epic("РЕМОНТ АВТО", new ArrayList<>()));
        manager.saveSubtask(new Subtask("Тех. обслуживание", "Приехать в Автосервис", Status.DONE));

        manager.saveEpic(new Epic("ТЕХ.ЗАДАНИЕ 2 СПРИНТА", new ArrayList<>()));

        Subtask subtask = new Subtask("Купитка", "Кулема, машина", Status.NEW);
        subtask.setId(4);
        subtask.setIdOfEpic(3);
        manager.updateSubtask(subtask);

        for (Object i : manager.getTasks().values()) {
            System.out.println(i);
        }
        System.out.println();
        for (Object i : manager.getEpics().values()) {
            System.out.println(i);
        }
        System.out.println();
        for (Object i : manager.getSubtasks().values()) {
            System.out.println(i);
        }
    }
}