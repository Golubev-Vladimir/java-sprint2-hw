import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import service.Counter;
import service.Managers;
import service.TaskManager;

public class Main {
    static String line = "---------------";

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
        inMemoryTaskManager.saveSubtask(new Subtask(counter.generateId(), epicBuyFlat.getId(),
                "Рассчитаться с риелтором", "передать деньги",
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

        inMemoryTaskManager.updateEpic(new Epic(5, "Школа 21")); // тест обновление

        inMemoryTaskManager.deleteEpicById(1); //тест удаление
        inMemoryTaskManager.deleteTaskById(10);

        System.out.println(line); // тест печать всех задач
        inMemoryTaskManager.getTasks().values().forEach(System.out::println);
        System.out.println(line);

        inMemoryTaskManager.getEpics().values().forEach(System.out::println);
        System.out.println(line);

        inMemoryTaskManager.getSubtasks().values().forEach(System.out::println);
        System.out.println(line);

        inMemoryTaskManager.getTaskById(9); //тест запрос истории
        inMemoryTaskManager.getTaskById(10);
        inMemoryTaskManager.getEpicById(1);
        inMemoryTaskManager.getSubtaskById(2);
        inMemoryTaskManager.getSubtaskById(3);
        inMemoryTaskManager.getSubtaskById(4);
        inMemoryTaskManager.getEpicById(8);
        inMemoryTaskManager.getSubtaskById(2);
        inMemoryTaskManager.getEpicById(1);
        inMemoryTaskManager.getSubtaskById(4);

        for (Task task : inMemoryTaskManager.history()) { //тест печать id задач
            System.out.print(" " + task.getId() + " ");
        }
        System.out.println("\n" + line);
        inMemoryTaskManager.history().forEach(System.out::println); //тест печать задач
        System.out.println(line);
    }
}