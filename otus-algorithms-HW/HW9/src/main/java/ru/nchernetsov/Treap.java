package ru.nchernetsov;

import ru.nchernetsov.common.Pair;

import java.util.Optional;
import java.util.Random;

/**
 * Декартово дерево (tree + heap = treap)
 */
public class Treap {

    /**
     * Ключ, дерево по ключам является деревом поиска
     */
    private int x;

    /**
     * Приоритет, дерево по приоритетам является пирамидой
     */
    private int y;

    private Treap left;

    private Treap right;

    /**
     * Размер поддерева с корнем в текущей вершине
     */
    private int size;

    public Treap(int x, int y) {
        this.x = x;
        this.y = y;
        this.left = null;
        this.right = null;
        this.size = 1;
    }

    public Treap(int x, int y, Treap left, Treap right) {
        this.x = x;
        this.y = y;
        this.left = left;
        this.right = right;
        this.size = 1;
    }

    /**
     * Вставка нового элемента в дерево
     *
     * @param x вставляемый элемент
     * @return дерево после вставки элемента
     */
    public Treap add(int x) {
        Pair<Treap, Treap> thisSplit = this.split(x);
        Treap left = thisSplit.getKey();
        Treap right = thisSplit.getValue();
        Treap m = new Treap(x, new Random().nextInt());
        return merge(merge(left, m), right);
    }

    public Treap add(int x, int y) {
        Pair<Treap, Treap> thisSplit = this.split(x);
        Treap left = thisSplit.getKey();
        Treap right = thisSplit.getValue();
        Treap m = new Treap(x, y);
        return merge(merge(left, m), right);
    }

    /**
     * Удалить из дерева элемент с ключом x
     *
     * @param x ключ удаляемого элемента
     * @return дерево после удаления элемента
     */
    public Treap remove(int x) {
        Pair<Treap, Treap> thisSplit = this.split(x - 1);
        Treap l = thisSplit.getKey();
        Treap r = thisSplit.getValue();
        if (r == null) {
            throw new ElementNotFoundException(String.format("Element %d not found in treap", x - 1));
        }
        Pair<Treap, Treap> rightSplit = r.split(x);
        r = rightSplit.getValue();
        if (r == null) {
            throw new ElementNotFoundException(String.format("Element %d not found in treap", x));
        }
        return merge(l, r);
    }

    /**
     * Поиск в декартовом дереве k-го по значению ключа элемента
     *
     * @param k искомая порядковая статистика
     * @return k-ый по значению ключа элемент
     */
    public Optional<Integer> kThElement(int k) {
        Treap cur = this;
        while (cur != null) {
            int sizeLeft = sizeOf(cur.left);
            if (sizeLeft == k) {
                return Optional.of(cur.x);
            }
            cur = sizeLeft > k ? cur.left : cur.right;
            if (sizeLeft < k) {
                k -= sizeLeft + 1;
            }
        }
        return Optional.empty();
    }

    public static int sizeOf(Treap treap) {
        return treap == null ? 0 : treap.size;
    }

    /**
     * Слияние двух декартовых деревьев в одно корректное декартово дерево
     *
     * @param left  левое декартово дерево
     * @param right правое декартово дерево
     * @return результат слияния
     */
    private static Treap merge(Treap left, Treap right) {
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }

        Treap answer;
        if (left.getY() > right.getY()) {
            Treap newRight = merge(left.getRight(), right);
            answer = new Treap(left.getX(), left.getY(), left.getLeft(), newRight);
        } else {
            Treap newLeft = merge(left, right.getLeft());
            answer = new Treap(right.getX(), right.getY(), newLeft, right.getRight());
        }

        answer.recalcSize();
        return answer;
    }

    /**
     * Разделение дерева на 2 таким образом, чтобы в одном из них оказались все элементы
     * исходного дерева с ключами, меньшими x, а в другом - с большими
     *
     * @param x ключ, по которому разделяется дерево
     * @return пара из левого и правого деревьев, на которые разделили данное дерево
     */
    private Pair<Treap, Treap> split(int x) {
        Treap newTree = null;
        Treap L;
        Treap R;
        if (this.getX() <= x) {
            if (right == null) {
                R = null;
            } else {
                Pair<Treap, Treap> rightSplit = right.split(x);
                newTree = rightSplit.getKey();
                R = rightSplit.getValue();
            }
            L = new Treap(this.x, this.y, left, newTree);
            L.recalcSize();
        } else {
            if (left == null) {
                L = null;
            } else {
                Pair<Treap, Treap> leftSplit = left.split(x);
                L = leftSplit.getKey();
                newTree = leftSplit.getValue();
            }
            R = new Treap(this.x, this.y, newTree, right);
            R.recalcSize();
        }
        return Pair.of(L, R);
    }

    private void recalcSize() {
        this.size = sizeOf(left) + sizeOf(right) + 1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Treap getLeft() {
        return left;
    }

    public Treap getRight() {
        return right;
    }

    public int getSize() {
        return size;
    }
}
