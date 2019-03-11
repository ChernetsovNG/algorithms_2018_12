package ru.nchernetsov;

/**
 * Стек на базе массива
 */
public class Stack {

    private int maxSize;

    private int[] stackArray;

    private int top;

    public Stack(int size) {
        this.maxSize = size;
        stackArray = new int[maxSize];
        top = -1;
    }

    public void push(int element) {
        if (isFull()) {
            throw new IllegalStateException("Stack is full");
        }
        stackArray[++top] = element;
    }

    public int pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return stackArray[top--];
    }

    public int peek() {
        return stackArray[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top == maxSize - 1;
    }
}
