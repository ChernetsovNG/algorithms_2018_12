package ru.nchernetsov;

/**
 * Стек на базе массива
 */
public class Stack<E> {

    private int maxSize;

    private E[] stackArray;

    private int top;

    @SuppressWarnings("unchecked")
    public Stack(int size) {
        this.maxSize = size;
        stackArray = (E[]) new Object[maxSize];
        top = -1;
    }

    public void push(E element) {
        if (isFull()) {
            throw new IllegalStateException("Stack is full");
        }
        stackArray[++top] = element;
    }

    @SuppressWarnings("unchecked")
    public E pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return stackArray[top--];
    }

    @SuppressWarnings("unchecked")
    public E peek() {
        return stackArray[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top == maxSize - 1;
    }
}
