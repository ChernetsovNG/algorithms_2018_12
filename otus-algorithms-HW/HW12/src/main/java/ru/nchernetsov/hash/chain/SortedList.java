package ru.nchernetsov.hash.chain;

/**
 * Связный список, отсортированный в порядке возрастания ключей элементов
 *
 * @param <T> тип
 */
class SortedList<T extends Comparable<T>> {

    private Link<T> first;

    /**
     * Количество элементов в списке
     */
    private int size;

    public SortedList() {
        first = null;
        size = 0;
    }

    /**
     * Вставка в порядке сортировки
     *
     * @param link вставляемый элемент
     */
    public void insert(Link<T> link) {
        T key = link.getKey();
        Link<T> previous = null;  // начиная с первого элемента
        Link<T> current = first;
        while (current != null && key.compareTo(current.getKey()) > 0) {  // до конца списка
            previous = current;
            current = current.next;  // переход к следующему элементу
        }
        if (previous == null) {  // в начале списка
            first = link;
        } else {
            previous.next = link;
        }
        link.next = current;
        size++;
    }

    /**
     * Удаление элемента
     *
     * @param key удаляемый элемент
     */
    public void delete(T key) {
        if (first == null) {
            return;
        }
        Link<T> previous = null;  // начиная с первого элемента
        Link<T> current = first;
        while (current != null && key.compareTo(current.getKey()) != 0) {
            previous = current;
            current = current.next;
        }
        if (previous == null) {  // если удаляем первый элемент
            first = first.next;
        } else {
            if (current == null) {
                return;
            }
            previous.next = current.next;  // удалить текущий элемент
        }
        size--;
    }

    /**
     * Поиск элемента с заданным ключом
     *
     * @param key ключ
     * @return найденный элемент, или null, если такого элемента нет
     */
    public Link<T> find(T key) {
        Link<T> current = first;
        while (current != null && current.getKey().compareTo(key) <= 0) {
            if (current.getKey().compareTo(key) == 0) {  // элемент найден
                return current;
            }
            current = current.next;
        }
        return null;  // элемент не найден
    }
}

class Link<T extends Comparable> {

    /**
     * Данные
     */
    private T data;

    /**
     * Следующий элемент списка
     */
    public Link<T> next;

    public Link(T it) {
        data = it;
    }

    public T getKey() {
        return data;
    }
}
