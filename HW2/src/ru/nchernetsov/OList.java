package ru.nchernetsov;

public class OList<T> {

    private ListItem<T> head;

    private ListItem<T> tail;

    public OList() {
        head = null;
        tail = null;
    }

    public ListItem<T> head() {
        return head;
    }

    public ListItem<T> tail() {
        return tail;
    }

    void add(ListItem<T> item) {
        if (head == null) {
            head = item;
            tail = item;
        } else {
            tail.setNext(item);
            tail = item;
        }
    }
}
