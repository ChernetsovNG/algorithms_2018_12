package ru.nchernetsov;

public class ListItem<T> {

    private T item;

    private ListItem<T> next;

    public ListItem(T item) {
        this.item = item;
        next = null;
    }

    public T getItem() {
        return item;
    }

    public void setNext(ListItem<T> item) {
        next = item;
    }

    public ListItem<T> getNext() {
        return next;
    }
}
