import model.Epic;
import model.StatusTask;
import model.Subtask;
import model.Task;
import service.Managers;
import service.TaskManager;

import java.io.IOException;

import static service.Counter.generateId;

public class Main {
    static String line = "-".repeat(15);

    public static void main(String[] args) throws IOException {  //THIS CLASS WAS CREATED FOR TESTING APPLICATION
        TaskManager fileBackedTasksManager = Managers.getSaveFile("fileTasks.csv");

        fileBackedTasksManager.saveTask(new Task(generateId(), "Уборка",
                "вытереть пыль, пропылесосить, Помыть пол", StatusTask.DONE));
        fileBackedTasksManager.saveTask(new Task(generateId(), "Новоселье",
                "купить шампанское, купить торт", StatusTask.IN_PROGRESS));

        Epic epicBuyFlat = new Epic(generateId(), "BUY FLAT", "Покупка квартиры");
        fileBackedTasksManager.saveEpic(epicBuyFlat);
        fileBackedTasksManager.saveSubtask(new Subtask(generateId(), epicBuyFlat.getTaskId(),
                "Купить новую квартиру", "найти риелтора, выбрать нужный вариант", StatusTask.DONE));
        fileBackedTasksManager.saveSubtask(new Subtask(generateId(), epicBuyFlat.getTaskId(),
                "Зарегистрировать право собственности", "собрать все документы, передать документы в Росреестр",
                StatusTask.DONE));
        fileBackedTasksManager.saveSubtask(new Subtask(generateId(), epicBuyFlat.getTaskId(),
                "Рассчитаться с риелтором", "передать деньги",
                StatusTask.NEW));

        Epic epicRepairFlat = new Epic(generateId(), "REPAIR FLAT", "Ремонт квартиры");
        fileBackedTasksManager.saveEpic(epicRepairFlat);
        fileBackedTasksManager.saveSubtask(new Subtask(generateId(), epicRepairFlat.getTaskId(),
                "Найти прораба", "согласовать сроки, обсудить стоимость", StatusTask.NEW));
        fileBackedTasksManager.saveSubtask(new Subtask(generateId(), epicRepairFlat.getTaskId(),
                "Купить материалы", "выбрать магазин, заказать доставку", StatusTask.DONE));

        Epic epicMove = new Epic(generateId(), "MOVE", "Переезд");
        fileBackedTasksManager.saveEpic(epicMove);

        Epic epicHomework = new Epic(generateId(), "Homework", "Домашнее задание");
        fileBackedTasksManager.saveEpic(epicHomework);

        Epic epicStudy = new Epic(generateId(), "Study", "Учеба");
        fileBackedTasksManager.saveEpic(epicStudy);

        fileBackedTasksManager.updateEpic(new Epic(5, "School 21", "Школа 21")); // тест обновление

        fileBackedTasksManager.deleteEpicById(7); //тест удаление
        fileBackedTasksManager.deleteTaskById(1);
        fileBackedTasksManager.deleteTaskById(4);
        fileBackedTasksManager.deleteSubtaskById(6);

        System.out.println(line); // тест печать всех задач
        fileBackedTasksManager.getTasks().values().forEach(System.out::println);
        System.out.println(line);

        fileBackedTasksManager.getEpics().values().forEach(System.out::println);
        System.out.println(line);

        fileBackedTasksManager.getSubtasks().values().forEach(System.out::println);
        System.out.println(line);

        fileBackedTasksManager.getTaskById(1); //тест запрос истории
        fileBackedTasksManager.getTaskById(2);
        fileBackedTasksManager.getEpicById(3);
        fileBackedTasksManager.getSubtaskById(4);
        fileBackedTasksManager.getSubtaskById(5);
        fileBackedTasksManager.getSubtaskById(6);
        fileBackedTasksManager.getEpicById(7);
        fileBackedTasksManager.getSubtaskById(8);
        fileBackedTasksManager.getSubtaskById(9);
        fileBackedTasksManager.getEpicById(10);
        fileBackedTasksManager.getEpicById(11);
        fileBackedTasksManager.getEpicById(12);
        fileBackedTasksManager.getTaskById(12);
        fileBackedTasksManager.getSubtaskById(4);

        for (Task task : fileBackedTasksManager.history()) { //тест печать id задач
            System.out.print(task.getTaskId() + ",");
        }
        System.out.println("\n" + line);
        fileBackedTasksManager.history().forEach(System.out::println); //тест печать задач
        System.out.println(line);
    }
}