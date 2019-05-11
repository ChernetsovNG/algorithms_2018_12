package ru.nchernetsov;

import java.util.Iterator;

/**
 * Стек на базе массива
 */
public class Stack<E> implements Iterable<E> {

    private MyArrayList<E> stackArray;

    public Stack() {
        this(10);
    }

    public Stack(int size) {
        stackArray = new MyArrayList<>(size);
    }

    public void push(E element) {
        stackArray.add(element);
    }

    public E pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        int size = stackArray.size();
        return stackArray.remove(size - 1);
    }

    public E peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        int size = stackArray.size();
        return stackArray.get(size - 1);
    }

    public boolean isEmpty() {
        return stackArray.size() == 0;
    }

    public void clear() {
        stackArray.clear();
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = stackArray.size() - 1;

            @Override
            public boolean hasNext() {
                return index >= 0;
            }

            @Override
            public E next() {
                return stackArray.get(index--);
            }
        };
    }
}
