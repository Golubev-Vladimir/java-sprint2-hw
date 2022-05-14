package onlyfortest;

import model.StatusTask;
import model.Task;
import service.Managers;
import service.TaskManager;

import static service.Counter.generateId;
import static service.Printer.print;

public class Main {
    static final String CAT_SUCCESS = (" ,_     _\n" +
            " |\\\\_,-~/\n" +
            " / _  _ |    ,--.\n" +
            "(  @  @ )   / ,-'\n" +
            " \\  _T_/-._( (\n" +
            " /         `. \\\n" +
            "|         _  \\ |\n" +
            " \\ \\ ,  /      |\n" +
            "  || |-_\\__   /\n" +
            " ((_/`(____,-'\n" +
            "Успех!");

    public static void main(String[] args) {  //THIS CLASS WAS CREATED FOR TESTING APPLICATION
        TaskManager fileBackedTasksManager = Managers.getFileBackedTasksManager("fileTasks.csv");
        fileBackedTasksManager.saveTask(new Task(fileBackedTasksManager.getLastId() + generateId(),
                "Сдать ТЗ-5", "Придется немного поработать", StatusTask.IN_PROGRESS));

        fileBackedTasksManager.getEpicById(7);
        fileBackedTasksManager.getTaskById(13);
        fileBackedTasksManager.getSubtaskById(5);
        fileBackedTasksManager.history();
        print(CAT_SUCCESS);

        /*fileBackedTasksManager.saveTask(new Task(generateId(), "Уборка",
                "вытереть пыль; пропылесосить; Помыть пол", StatusTask.DONE));
        fileBackedTasksManager.saveTask(new Task(generateId(), "Новоселье",
                "купить шампанское; купить торт", StatusTask.IN_PROGRESS));

        Epic epicBuyFlat = new Epic(generateId(), "BUY FLAT", "Покупка квартиры",
                StatusTask.IN_PROGRESS);
        fileBackedTasksManager.saveEpic(epicBuyFlat);
        fileBackedTasksManager.saveSubtask(new Subtask(generateId(), epicBuyFlat.getTaskId(),
                "Купить новую квартиру", "найти риелтора; выбрать нужный вариант",
                StatusTask.DONE));
        fileBackedTasksManager.saveSubtask(new Subtask(generateId(), epicBuyFlat.getTaskId(),
                "Зарегистрировать право собственности", "собрать все документы; передать документы в Росреестр",
                StatusTask.DONE));
        fileBackedTasksManager.saveSubtask(new Subtask(generateId(), epicBuyFlat.getTaskId(),
                "Рассчитаться с риелтором", "передать деньги",
                StatusTask.DONE));

        Epic epicRepairFlat = new Epic(generateId(), "REPAIR FLAT", "Ремонт квартиры",
                StatusTask.IN_PROGRESS);
        fileBackedTasksManager.saveEpic(epicRepairFlat);
        fileBackedTasksManager.saveSubtask(new Subtask(generateId(), epicRepairFlat.getTaskId(),
                "Найти прораба", "согласовать сроки; обсудить стоимость", StatusTask.NEW));
        fileBackedTasksManager.saveSubtask(new Subtask(generateId(), epicRepairFlat.getTaskId(),
                "Купить материалы", "выбрать магазин; заказать доставку", StatusTask.NEW));

        Epic epicMove = new Epic(generateId(), "MOVE", "Переезд", StatusTask.IN_PROGRESS);
        fileBackedTasksManager.saveEpic(epicMove);

        Epic epicHomework = new Epic(generateId(), "Homework", "Домашнее задание",
                StatusTask.IN_PROGRESS);
        fileBackedTasksManager.saveEpic(epicHomework);

        Epic epicStudy = new Epic(generateId(), "Study", "Учеба", StatusTask.IN_PROGRESS);
        fileBackedTasksManager.saveEpic(epicStudy);

        fileBackedTasksManager.updateEpic(new Epic(5, "School 21", "Школа 21",
                StatusTask.IN_PROGRESS)); // тест обновление

        fileBackedTasksManager.deleteEpicById(7); //тест удаление
        fileBackedTasksManager.deleteTaskById(1);
        fileBackedTasksManager.deleteTaskById(4);
        fileBackedTasksManager.deleteSubtaskById(6);

        System.out.print(System.lineSeparator()); // тест печать всех задач
        fileBackedTasksManager.getTasks().values().forEach(System.out::println);
        System.out.print(System.lineSeparator());

        fileBackedTasksManager.getEpics().values().forEach(System.out::println);
        System.out.print(System.lineSeparator());

        fileBackedTasksManager.getSubtasks().values().forEach(System.out::println);
        System.out.print(System.lineSeparator());

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
        System.out.print(System.lineSeparator());

        for (Task task : fileBackedTasksManager.history()) { //тест печать id задач
            System.out.print(task.getTaskId() + ",");
        }
        System.out.print(System.lineSeparator() + System.lineSeparator());
        fileBackedTasksManager.history().forEach(System.out::println); //тест печать задач
        System.out.print(System.lineSeparator());*/
    }
}