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

        Epic epicBuyFlat = new Epic(counter.generateId(), "BUY FLAT");
        inMemoryTaskManager.saveEpic(epicBuyFlat);
        inMemoryTaskManager.saveSubtask(new Subtask(counter.generateId(), epicBuyFlat.getId(),
                "Купить новую квартиру", "найти риелтора, выбрать нужный вариант", Status.DONE));
        inMemoryTaskManager.saveSubtask(new Subtask(counter.generateId(), epicBuyFlat.getId(),
                "Зарегистрировать право собственности", "собрать все документы, передать документы в Росреестр",
                Status.DONE));

        Epic epicRepairFlat = new Epic(counter.generateId(), "REPAIR FLAT");
        inMemoryTaskManager.saveEpic(epicRepairFlat);
        inMemoryTaskManager.saveSubtask(new Subtask(counter.generateId(), epicRepairFlat.getId(),
                "Найти прораба", "согласовать сроки, обсудить стоимость", Status.NEW));
        inMemoryTaskManager.saveSubtask(new Subtask(counter.generateId(), epicRepairFlat.getId(),
                "Купить материалы", "выбрать магазин, заказать доставку", Status.DONE));

        Epic epicMove = new Epic(counter.generateId(), "MOVE");
        inMemoryTaskManager.saveEpic(epicMove);

        inMemoryTaskManager.saveTask(new Task(counter.generateId(), "Уборка",
                "вытереть пыль, пропылесосить, Помыть пол", Status.DONE));
        inMemoryTaskManager.saveTask(new Task(counter.generateId(), "Новоселье",
                "купить шампанское, купить торт", Status.IN_PROGRESS));

        Epic epicHomework = new Epic(counter.generateId(), "Homework");
        inMemoryTaskManager.saveEpic(epicHomework);

        Epic epicStudy = new Epic(counter.generateId(), "Study");
        inMemoryTaskManager.saveEpic(epicStudy);

        inMemoryTaskManager.updateEpic(new Epic(4, "Школа 21")); // тест

        System.out.println("---------------"); // тест
        for (Task task : inMemoryTaskManager.getTasks().values()) {
            System.out.println(task);
        }
        System.out.println("---------------");

        for (Task task : inMemoryTaskManager.getEpics().values()) {
            System.out.println(task);
        }
        System.out.println("---------------");

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