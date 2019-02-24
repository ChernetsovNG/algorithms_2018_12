package ru.nchernetsov;

public class RedBlackNode<T extends Comparable<T>> {

    public static final boolean BLACK = false;

    public static final boolean RED = true;

    public T key;

    RedBlackNode<T> parent;

    RedBlackNode<T> left;

    RedBlackNode<T> right;

    // Количество элементов слева от узла
    public int numLeft = 0;

    // Количество элементов справа от узла
    public int numRight = 0;

    // цвет узла
    public boolean color;

    RedBlackNode() {
        color = BLACK;
        numLeft = 0;
        numRight = 0;
        parent = null;
        left = null;
        right = null;
    }

    RedBlackNode(T key) {
        this();
        this.key = key;
    }
}
