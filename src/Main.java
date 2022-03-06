import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import service.Counter;
import service.Managers;
import service.TaskManager;

public class Main {
    public static void main(String[] args) {  //THIS CLASS WAS CREATED FOR TESTING APPLICATION
        TaskManager inMemoryTaskManager = Managers.getDefault();
        Counter counter = new Counter();

        Epic epicBuyFlat = new Epic("BUY FLAT", counter.generateId());
        inMemoryTaskManager.saveEpic(epicBuyFlat);
        inMemoryTaskManager.saveSubtask(new Subtask(
                "Купить новую квартиру",
                "найти риелтора, выбрать нужный вариант",
                Status.DONE, counter.generateId(), epicBuyFlat.getId()));
        inMemoryTaskManager.saveSubtask(new Subtask(
                "Зарегистрировать право собственности",
                "собрать все документы, передать документы в Росреестр",
                Status.DONE, counter.generateId(), epicBuyFlat.getId()));

        Epic epicRepairFlat = new Epic("REPAIR FLAT", counter.generateId());
        inMemoryTaskManager.saveEpic(epicRepairFlat);
        inMemoryTaskManager.saveSubtask(new Subtask(
                "Найти прораба",
                "согласовать сроки, обсудить стоимость",
                Status.NEW, counter.generateId(), epicRepairFlat.getId()));
        inMemoryTaskManager.saveSubtask(new Subtask(
                "Купить материалы",
                "выбрать магазин, заказать доставку",
                Status.DONE, counter.generateId(), epicRepairFlat.getId()));

        Epic epicMove = new Epic("MOVE", counter.generateId());
        inMemoryTaskManager.saveEpic(epicMove);

        inMemoryTaskManager.saveTask(new Task("Уборка", "вытереть пыль, пропылесосить, Помыть пол",
                Status.DONE, counter.generateId()));
        inMemoryTaskManager.saveTask(new Task("Новоселье", "купить шампанское, купить торт",
                Status.IN_PROGRESS, counter.generateId()));

        Epic epicHomework = new Epic("Homework", counter.generateId());
        inMemoryTaskManager.saveEpic(epicHomework);

        Epic epicStudy = new Epic("Study", counter.generateId());
        inMemoryTaskManager.saveEpic(epicStudy);

        inMemoryTaskManager.updateEpic(new Epic("Школа 21", 4)); // тест

        System.out.println("---------------"); // тест
        for (Task task : inMemoryTaskManager.getTasks().values()) {
            System.out.println(task);
        }
        System.out.println("---------------");
        ;
        for (Task task : inMemoryTaskManager.getEpics().values()) {
            System.out.println(task);
        }
        System.out.println("---------------");
        ;
        for (Task task : inMemoryTaskManager.getSubtasks().values()) {
            System.out.println(task);
        }
        System.out.println("---------------");

        inMemoryTaskManager.getEpicById(1); // тест
        inMemoryTaskManager.getSubtaskById(2);
        inMemoryTaskManager.getSubtaskById(3);
        inMemoryTaskManager.getEpicById(4);
        inMemoryTaskManager.getSubtaskById(5);
        inMemoryTaskManager.getSubtaskById(6);
        inMemoryTaskManager.getEpicById(7);
        inMemoryTaskManager.getTaskById(8);
        inMemoryTaskManager.getTaskById(9);
        inMemoryTaskManager.getEpicById(10);
        inMemoryTaskManager.getEpicById(11);
        inMemoryTaskManager.getSubtaskById(3);
        inMemoryTaskManager.getEpicById(4);
        inMemoryTaskManager.getSubtaskById(5);
        inMemoryTaskManager.getEpicById(10);
        inMemoryTaskManager.getTaskById(8);
        inMemoryTaskManager.getSubtaskById(2);

        for (Task task : inMemoryTaskManager.history()) {
            System.out.print(" " + task.getId() + " ");
        }
        System.out.println("\n---------------");
    }
}