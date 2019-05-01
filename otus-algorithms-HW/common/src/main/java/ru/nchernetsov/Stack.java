package ru.nchernetsov;

/**
 * Стек на базе массива
 */
public class Stack<E> {

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
}
