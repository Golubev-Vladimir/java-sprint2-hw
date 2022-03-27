package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskLinkedList<Task> {

    private int size = 0;
    public Node<Task> first;
    public Node<Task> last;

    public final List<Task> finalLastViewedTasks = new ArrayList<>();
    public final Map<Long, Node<Task>> mapFastGet = new HashMap<>();

    public void linkLast(Task task) {
        final Node<Task> l = last;
        final Node<Task> newNode = new Node<>(l, task, null);

        last = newNode;

        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    public void getTasks() {
        finalLastViewedTasks.clear();
        Node<Task> element = first;

        for (int i = 1; i <= size; i++) { // если нужно ограничить кол-во задач, выводимых getHistory(),
            if (element != null) {        // то можно использовать константу вместе переменной size
                finalLastViewedTasks.add(element.task);
                element = element.next;
            }
        }
    }

    public void removeNode(Node<Task> node) {
        final Node<Task> next = node.next;
        final Node<Task> prev = node.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        node.task = null;
        size--;
    }

    public static class Node<Task> {
        Task task;
        Node<Task> next;
        Node<Task> prev;

        public Node(Node<Task> prev, Task task, Node<Task> next) {
            this.task = task;
            this.next = next;
            this.prev = prev;
        }
    }
}