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

    /**
     * Удаляет голову списка и возвращает её
     *
     * @return значение из головы списка
     */
    T remove() {
        if (head == null) {
            return null;
        }
        T headItemToReturn = head.getItem();
        head = head.getNext();
        return headItemToReturn;
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

    /**
     * Метод проверяем, если ли в списке не-null элементы
     *
     * @return пуст ли список?
     */
    boolean isEmpty() {
        return head == null;
    }
}
