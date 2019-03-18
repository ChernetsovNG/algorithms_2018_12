package ru.nchernetsov;

import java.util.Iterator;
import java.util.Stack;

public class RedBlackTree<T extends Comparable<T>> implements Iterable<T> {

    private final RedBlackNode<T> nil = new RedBlackNode<>();

    private RedBlackNode<T> root = nil;

    public RedBlackTree() {
        root.parent = nil;
        root.left = nil;
        root.right = nil;
    }

    /**
     * Вставка нового узла в дерево
     *
     * @param key ключ вставляемого узла
     */
    public void insert(T key) {
        insert(new RedBlackNode<>(key));
    }

    /**
     * Поиск в дереве узла по заданному ключу
     *
     * @param key ключ для поиска
     * @return узел с заданным ключом или null, если узел не найден
     */
    public RedBlackNode<T> search(T key) {
        RedBlackNode<T> current = root;
        while (!isNil(current)) {
            if (current.key.equals(key)) {
                return current;
            } else if (current.key.compareTo(key) < 0) {
                current = current.right;
            } else {
                current = current.left;
            }
        }
        return null;
    }

    public void remove(T key) {
        RedBlackNode<T> z = search(key);
        if (z != null) {
            doRemove(z);
        }
    }

    /**
     * Удалить узел из дерева
     *
     * @param v удаляемый узел
     */
    public void remove(RedBlackNode<T> v) {
        RedBlackNode<T> z = search(v.key);
        if (z != null) {
            doRemove(z);
        }
    }

    /**
     * Очистить дерево
     */
    public void clear() {
        root.parent = nil;
        root.left = nil;
        root.right = nil;
    }

    private void doRemove(RedBlackNode<T> z) {
        RedBlackNode<T> x;
        RedBlackNode<T> y;

        if (isNil(z.left) || isNil(z.right)) {
            y = z;
        } else {
            y = treeSuccessor(z);
        }

        if (!isNil(y.left)) {
            x = y.left;
        } else {
            x = y.right;
        }

        x.parent = y.parent;

        if (isNil(y.parent)) {
            root = x;
        } else if (!isNil(y.parent.left) && y.parent.left == y) {
            y.parent.left = x;
        } else if (!isNil(y.parent.right) && y.parent.right == y) {
            y.parent.right = x;
        }

        if (y != z) {
            z.key = y.key;
        }

        fixNodeData(x, y);

        if (y.color == RedBlackNode.BLACK) {
            removeFixup(x);
        }
    }

    /**
     * Вставка в данное дерево нового узла
     *
     * @param z вставляемый узел
     */
    private void insert(RedBlackNode<T> z) {

        // Create a reference to root & initialize a node to nil
        RedBlackNode<T> y = nil;
        RedBlackNode<T> x = root;

        while (!isNil(x)) {
            y = x;
            if (z.key.compareTo(x.key) < 0) {
                x.numLeft++;
                x = x.left;
            } else {
                x.numRight++;
                x = x.right;
            }
        }

        z.parent = y;

        if (isNil(y)) {
            root = z;
        } else if (z.key.compareTo(y.key) < 0) {
            y.left = z;
        } else {
            y.right = z;
        }

        z.left = nil;
        z.right = nil;
        z.color = RedBlackNode.RED;

        insertFixup(z);
    }

    /**
     * Возвращает узел со следующим ключом, большим, чем ключ заданного узла
     *
     * @param x заданный узел
     * @return tree successor
     */
    private RedBlackNode<T> treeSuccessor(RedBlackNode<T> x) {
        if (!isNil(x.left)) {
            return treeMinimum(x.right);
        }
        RedBlackNode<T> y = x.parent;
        while (!isNil(y) && x == y.right) {
            x = y;
            y = y.parent;
        }
        return y;
    }

    private RedBlackNode<T> treeMinimum(RedBlackNode<T> node) {
        while (!isNil(node.left)) {
            node = node.left;
        }
        return node;
    }

    private void insertFixup(RedBlackNode<T> z) {
        RedBlackNode<T> y;
        while (z.parent.color == RedBlackNode.RED) {
            if (z.parent == z.parent.parent.left) {
                y = z.parent.parent.right;
                if (y.color == RedBlackNode.RED) {
                    z.parent.color = RedBlackNode.BLACK;
                    y.color = RedBlackNode.BLACK;
                    z.parent.parent.color = RedBlackNode.RED;
                    z = z.parent.parent;
                } else if (z == z.parent.right) {
                    z = z.parent;
                    leftRotate(z);
                } else {
                    z.parent.color = RedBlackNode.BLACK;
                    z.parent.parent.color = RedBlackNode.RED;
                    rightRotate(z.parent.parent);
                }
            } else {
                y = z.parent.parent.left;
                if (y.color == RedBlackNode.RED) {
                    z.parent.color = RedBlackNode.BLACK;
                    y.color = RedBlackNode.BLACK;
                    z.parent.parent.color = RedBlackNode.RED;
                    z = z.parent.parent;
                } else if (z == z.parent.left) {
                    z = z.parent;
                    rightRotate(z);
                } else {
                    z.parent.color = RedBlackNode.BLACK;
                    z.parent.parent.color = RedBlackNode.RED;
                    leftRotate(z.parent.parent);
                }
            }
        }
        root.color = RedBlackNode.BLACK;
    }

    /**
     * Левый поворот вокруг узла x
     *
     * @param x узел, вокруг которого выполняется поворот
     */
    private void leftRotate(RedBlackNode<T> x) {

        // обновление значений numLeft и numRight
        leftRotateFixup(x);

        RedBlackNode<T> y = x.right;
        x.right = y.left;

        if (!isNil(y.left)) {
            y.left.parent = x;
        }
        y.parent = x.parent;

        if (isNil(x.parent)) {
            root = y;
        } else if (x.parent.left == x) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    private void leftRotateFixup(RedBlackNode x) {

        if (isNil(x.left) && isNil(x.right.left)) {
            x.numLeft = 0;
            x.numRight = 0;
            x.right.numLeft = 1;
        } else if (isNil(x.left) && !isNil(x.right.left)) {
            x.numLeft = 0;
            x.numRight = 1 + x.right.left.numLeft + x.right.left.numRight;
            x.right.numLeft = 2 + x.right.left.numLeft + x.right.left.numRight;
        } else if (!isNil(x.left) && isNil(x.right.left)) {
            x.numRight = 0;
            x.right.numLeft = 2 + x.left.numLeft + x.left.numRight;
        } else {
            x.numRight = 1 + x.right.left.numLeft + x.right.left.numRight;
            x.right.numLeft = 3 + x.left.numLeft + x.left.numRight + x.right.left.numLeft + x.right.left.numRight;
        }
    }

    /**
     * Правый поворот
     *
     * @param y узел, вокруг которого выполняется поворот
     */
    private void rightRotate(RedBlackNode<T> y) {

        rightRotateFixup(y);

        RedBlackNode<T> x = y.left;
        y.left = x.right;

        if (!isNil(x.right)) {
            x.right.parent = y;
        }
        x.parent = y.parent;

        if (isNil(y.parent)) {
            root = x;
        } else if (y.parent.right == y) {
            y.parent.right = x;
        } else {
            y.parent.left = x;
        }

        x.right = y;
        y.parent = x;
    }

    private void rightRotateFixup(RedBlackNode y) {

        if (isNil(y.right) && isNil(y.left.right)) {
            y.numRight = 0;
            y.numLeft = 0;
            y.left.numRight = 1;
        } else if (isNil(y.right) && !isNil(y.left.right)) {
            y.numRight = 0;
            y.numLeft = 1 + y.left.right.numRight + y.left.right.numLeft;
            y.left.numRight = 2 + y.left.right.numRight + y.left.right.numLeft;
        } else if (!isNil(y.right) && isNil(y.left.right)) {
            y.numLeft = 0;
            y.left.numRight = 2 + y.right.numRight + y.right.numLeft;

        } else {
            y.numLeft = 1 + y.left.right.numRight + y.left.right.numLeft;
            y.left.numRight = 3 + y.right.numRight + y.right.numLeft + y.left.right.numRight + y.left.right.numLeft;
        }
    }

    private void fixNodeData(RedBlackNode<T> x, RedBlackNode<T> y) {

        RedBlackNode<T> current;
        RedBlackNode<T> track;

        if (isNil(x)) {
            current = y.parent;
            track = y;
        } else {
            current = x.parent;
            track = x;
        }

        while (!isNil(current)) {
            if (y.key != current.key) {
                if (y.key.compareTo(current.key) > 0) {
                    current.numRight--;
                }
                if (y.key.compareTo(current.key) < 0) {
                    current.numLeft--;
                }
            } else {
                if (isNil(current.left)) {
                    current.numLeft--;
                } else if (isNil(current.right)) {
                    current.numRight--;
                } else if (track == current.right) {
                    current.numRight--;
                } else if (track == current.left) {
                    current.numLeft--;
                }
            }

            track = current;
            current = current.parent;
        }
    }

    private void removeFixup(RedBlackNode<T> x) {

        RedBlackNode<T> w;

        while (x != root && x.color == RedBlackNode.BLACK) {
            if (x == x.parent.left) {
                w = x.parent.right;
                if (w.color == RedBlackNode.RED) {
                    w.color = RedBlackNode.BLACK;
                    x.parent.color = RedBlackNode.RED;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }
                if (w.left.color == RedBlackNode.BLACK &&
                    w.right.color == RedBlackNode.BLACK) {
                    w.color = RedBlackNode.RED;
                    x = x.parent;
                } else {
                    if (w.right.color == RedBlackNode.BLACK) {
                        w.left.color = RedBlackNode.BLACK;
                        w.color = RedBlackNode.RED;
                        rightRotate(w);
                        w = x.parent.right;
                    }
                    w.color = x.parent.color;
                    x.parent.color = RedBlackNode.BLACK;
                    w.right.color = RedBlackNode.BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            } else {
                w = x.parent.left;
                if (w.color == RedBlackNode.RED) {
                    w.color = RedBlackNode.BLACK;
                    x.parent.color = RedBlackNode.RED;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }
                if (w.right.color == RedBlackNode.BLACK &&
                    w.left.color == RedBlackNode.BLACK) {
                    w.color = RedBlackNode.RED;
                    x = x.parent;
                } else {
                    if (w.left.color == RedBlackNode.BLACK) {
                        w.right.color = RedBlackNode.BLACK;
                        w.color = RedBlackNode.RED;
                        leftRotate(w);
                        w = x.parent.left;
                    }
                    w.color = x.parent.color;
                    x.parent.color = RedBlackNode.BLACK;
                    w.left.color = RedBlackNode.BLACK;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        x.color = RedBlackNode.BLACK;
    }

    private boolean isNil(RedBlackNode node) {
        return node == nil;
    }

    public int size() {
        return root.numLeft + root.numRight + 1;
    }

    public RedBlackNode<T> getRoot() {
        return root;
    }

    public int getHeight() {
        return treeHeight(root);
    }

    private int treeHeight(RedBlackNode node) {
        if (isNil(node)) {
            return 0;
        } else {
            return Math.max(treeHeight(node.left), treeHeight(node.right)) + 1;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new RedBlackTreeIterator<>(root);
    }
}

/**
 * Итератор для обхода красно-чёрного дерева в порядке возраствния ключей
 *
 * @param <T>
 */
class RedBlackTreeIterator<T extends Comparable<T>> implements Iterator<T> {

    private Stack<RedBlackNode<T>> stack;

    RedBlackTreeIterator(RedBlackNode<T> root) {
        stack = new Stack<>();
        while (root != null) {
            stack.push(root);
            root = root.left;
        }
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }

    public T next() {
        RedBlackNode<T> node = stack.pop();
        T result = node.key;
        if (node.right != null) {
            node = node.right;
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }
        return result;
    }
}
