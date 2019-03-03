package ru.nchernetsov.hash.chain;

import java.util.Iterator;

/**
 * Связный список, отсортированный в порядке возрастания ключей элементов
 *
 * @param <T> тип
 */
class SortedList<T extends Comparable<T>> implements Iterable<T> {

    private Link<T> first;

    /**
     * Количество элементов в списке
     */
    private int size;

    SortedList() {
        first = null;
        size = 0;
    }

    /**
     * Вставка в порядке сортировки
     *
     * @param link вставляемый элемент
     */
    void insert(Link<T> link) {
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
    void delete(T key) {
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
    Link<T> find(T key) {
        Link<T> current = first;
        while (current != null && current.getKey().compareTo(key) <= 0) {
            if (current.getKey().compareTo(key) == 0) {  // элемент найден
                return current;
            }
            current = current.next;
        }
        return null;  // элемент не найден
    }

    /**
     * Очистить список
     */
    public void clear() {
        first = null;
        size = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            Link<T> current = first;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (hasNext()) {
                    T key = current.getKey();
                    current = current.next;
                    return key;
                }
                return null;
            }
        };
    }

    public int getSize() {
        return size;
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
    Link<T> next;

    Link(T it) {
        data = it;
    }

    T getKey() {
        return data;
    }
}
