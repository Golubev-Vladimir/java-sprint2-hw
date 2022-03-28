import model.Epic;
import model.StatusTask;
import model.Subtask;
import model.Task;
import service.Managers;
import service.TaskManager;

import static service.Counter.generateId;

public class Main {
    static String line = "-".repeat(15);

    public static void main(String[] args) {  //THIS CLASS WAS CREATED FOR TESTING APPLICATION
        TaskManager inMemoryTaskManager = Managers.getDefault();

        Epic epicBuyFlat = new Epic(generateId(), "BUY FLAT");
        inMemoryTaskManager.saveEpic(epicBuyFlat);
        inMemoryTaskManager.saveSubtask(new Subtask(generateId(), epicBuyFlat.getTaskId(),
                "Купить новую квартиру", "найти риелтора, выбрать нужный вариант", StatusTask.DONE));
        inMemoryTaskManager.saveSubtask(new Subtask(generateId(), epicBuyFlat.getTaskId(),
                "Зарегистрировать право собственности", "собрать все документы, передать документы в Росреестр",
                StatusTask.DONE));
        inMemoryTaskManager.saveSubtask(new Subtask(generateId(), epicBuyFlat.getTaskId(),
                "Рассчитаться с риелтором", "передать деньги",
                StatusTask.DONE));

        Epic epicRepairFlat = new Epic(generateId(), "REPAIR FLAT");
        inMemoryTaskManager.saveEpic(epicRepairFlat);
        inMemoryTaskManager.saveSubtask(new Subtask(generateId(), epicRepairFlat.getTaskId(),
                "Найти прораба", "согласовать сроки, обсудить стоимость", StatusTask.NEW));
        inMemoryTaskManager.saveSubtask(new Subtask(generateId(), epicRepairFlat.getTaskId(),
                "Купить материалы", "выбрать магазин, заказать доставку", StatusTask.DONE));

        Epic epicMove = new Epic(generateId(), "MOVE");
        inMemoryTaskManager.saveEpic(epicMove);

        inMemoryTaskManager.saveTask(new Task(generateId(), "Уборка",
                "вытереть пыль, пропылесосить, Помыть пол", StatusTask.DONE));
        inMemoryTaskManager.saveTask(new Task(generateId(), "Новоселье",
                "купить шампанское, купить торт", StatusTask.IN_PROGRESS));

        Epic epicHomework = new Epic(generateId(), "Homework");
        inMemoryTaskManager.saveEpic(epicHomework);

        Epic epicStudy = new Epic(generateId(), "Study");
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
            System.out.print(" " + task.getTaskId() + " ");
        }
        System.out.println("\n" + line);
        inMemoryTaskManager.history().forEach(System.out::println); //тест печать задач
        System.out.println(line);
    }
}