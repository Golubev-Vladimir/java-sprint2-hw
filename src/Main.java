import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import service.Manager;
import service.Counter;

public class Main {
    public static void main(String[] args) {  //THIS CLASS WAS CREATED FOR TESTING APPLICATION
        Manager manager = new Manager();
        Counter counter = new Counter();

        Epic epicBuyFlat = new Epic("BUY FLAT", counter.generateId());
        manager.saveEpic(epicBuyFlat);
        manager.saveSubtask(new Subtask(
                "Купить новую квартиру",
                "найти риелтора, выбрать нужный вариант",
                Status.DONE,counter.generateId(), epicBuyFlat.getId()));
        manager.saveSubtask(new Subtask(
                "Зарегистрировать право собственности",
                "собрать все документы, передать документы в Росреестр",
                Status.DONE, counter.generateId(), epicBuyFlat.getId()));

        Epic epicRepairFlat = new Epic("REPAIR FLAT", counter.generateId());
        manager.saveEpic(epicRepairFlat);
        manager.saveSubtask(new Subtask(
                "Найти прораба",
                "согласовать сроки, обсудить стоимость",
                Status.NEW, counter.generateId(), epicRepairFlat.getId()));
        manager.saveSubtask(new Subtask(
                "Купить материалы",
                "выбрать магазин, заказать доставку",
                Status.DONE, counter.generateId(), epicRepairFlat.getId()));

        Epic epicMove = new Epic("MOVE", counter.generateId());
        manager.saveEpic(epicMove);

        manager.saveTask(new Task("Уборка", "вытереть пыль, пропылесосить, Помыть пол",
                Status.DONE, counter.generateId()));
        manager.saveTask(new Task("Новоселье", "купить шампанское, купить торт",
                Status.IN_PROGRESS, counter.generateId()));

        manager.updateEpic(new Epic("sdfbs", 4));

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
        System.out.println();
        System.out.println(manager.getEpicSubtask(1));

    }
}