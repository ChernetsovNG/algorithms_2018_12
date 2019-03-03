package ru.nchernetsov;

/**
 * АВЛ-дерево - сбалансированное по высоте двоичное дерево поиска,
 * в котором у любой вершины высота левого и правого поддеревьев
 * различаются не более, чем на 1
 */
public class AVLTree {

    /**
     * Ключ узла
     */
    private int key;

    /**
     * Высота поддерева с корнем в данном узле
     */
    private byte height;

    private AVLTree root;

    /**
     * Левое поддерево
     */
    private AVLTree left;

    /**
     * Правое поддерево
     */
    private AVLTree right;

    public AVLTree() {
    }

    public AVLTree(int key) {
        this.key = key;
        this.height = 1;
        this.left = null;
        this.right = null;
    }

    public void insert(int k) {
        this.root = doInsert(this.root, k);
    }

    public void remove(int k) {
        this.root = doRemove(this.root, k);
    }

    public AVLTree find(int k) {
        return doFind(this, k);
    }

    public int min() {
        AVLTree minNode = findMin(this);
        return minNode.getKey();
    }

    public int max() {
        AVLTree maxNode = findMax(this);
        return maxNode.getKey();
    }

    /**
     * Вставка ключа key в дерево
     *
     * @param p корень дерева, в которое вставляется ключ
     * @param k ключ
     * @return корень дерева после вставки ключа
     */
    private static AVLTree doInsert(AVLTree p, int k) {
        if (p == null) {
            return new AVLTree(k);
        } else if (k <= p.getKey()) {
            p.setLeft(doInsert(p.getLeft(), k));
        } else {
            p.setRight(doInsert(p.getRight(), k));
        }
        return balance(p);
    }

    /**
     * Удаление ключа k из дерева p
     *
     * @param p корневой узел дерева
     * @param k удаляемый ключ
     * @return корень дерева с удалённым ключом
     */
    private static AVLTree doRemove(AVLTree p, int k) {
        if (p == null) {
            return null;
        } else if (k < p.getKey()) {
            p.setLeft(doRemove(p.getLeft(), k));
        } else if (k > p.getKey()) {
            p.setRight(doRemove(p.getRight(), k));
        } else {  // k == p.getKey()
            AVLTree q = p.getLeft();
            AVLTree r = p.getRight();
            if (r == null) {
                return q;
            } else {
                AVLTree min = findMin(r);
                min.setRight(removeMin(r));
                min.setLeft(q);
                return balance(min);
            }
        }
        return balance(p);
    }

    /**
     * Поиск в дереве узла с ключом k
     *
     * @param p корневой узел дерева
     * @param k ключ для поиска
     * @return найденный узел или null, если узла с таким ключом в дереве нет
     */
    private static AVLTree doFind(AVLTree p, int k) {
        if (p == null) {
            return null;
        } else if (p.getKey() == k) {
            return p;
        } else if (k < p.getKey()) {
            return doFind(p.getLeft(), k);
        } else if (k > p.getKey()) {
            return doFind(p.getRight(), k);
        } else {
            return null;
        }
    }

    /**
     * Вычисление коэффициета сбалансированности для заданного узла
     *
     * @param node узел
     * @return коэффициент сбалансированности
     */
    private static int balanceFactor(AVLTree node) {
        return height(node.getRight()) - height(node.getLeft());
    }

    private static byte height(AVLTree node) {
        return node != null ? node.getHeight() : 0;
    }

    /**
     * Функция восстанавливает корректное значение поля height заданного узла
     *
     * @param node узел
     */
    private static void fixHeight(AVLTree node) {
        byte hLeft = height(node.getLeft());
        byte hRight = height(node.getRight());
        node.setHeight((byte) ((hLeft > hRight ? hLeft : hRight) + 1));
    }

    /**
     * Правый поворот дерева
     *
     * @param p корневой узел дерева
     * @return новый корень дерева после поворота
     */
    private static AVLTree rotateRight(AVLTree p) {
        AVLTree q = p.getLeft();
        p.setLeft(q.getRight());
        q.setRight(p);
        fixHeight(p);
        fixHeight(q);
        return q;
    }

    /**
     * Левый поворот дерева
     *
     * @param q корневой узел дерева
     * @return новый корень дерева после поворота
     */
    private static AVLTree rotateLeft(AVLTree q) {
        AVLTree p = q.getRight();
        q.setRight(p.getLeft());
        p.setLeft(q);
        fixHeight(q);
        fixHeight(p);
        return p;
    }

    /**
     * Балансировка узла p
     *
     * @param p узел
     * @return корень дерева после балансировки
     */
    private static AVLTree balance(AVLTree p) {
        fixHeight(p);
        if (balanceFactor(p) == 2) {
            if (balanceFactor(p.getRight()) < 0) {
                p.setRight(rotateRight(p.getRight()));
            }
            return rotateLeft(p);
        } else if (balanceFactor(p) == -2) {
            if (balanceFactor(p.getLeft()) > 0) {
                p.setLeft(rotateLeft(p.getLeft()));
            }
            return rotateRight(p);
        } else {  // балансировка не нужна
            return p;
        }
    }

    /**
     * Поиск узла с минимальным ключом в дереве p
     *
     * @param p корневой узел дерева
     * @return узел с минимальным ключом
     */
    private static AVLTree findMin(AVLTree p) {
        return p.getLeft() != null ? findMin(p.getLeft()) : p;
    }

    /**
     * Поиск узла с максимальным ключом в дереве p
     *
     * @param p корневой узел дерева
     * @return узел с максимальным ключом
     */
    private static AVLTree findMax(AVLTree p) {
        return p.getRight() != null ? findMax(p.getRight()) : p;
    }

    /**
     * Удаление узла с минимальным ключом из дерева p
     *
     * @param p корневой узел дерева
     * @return корень дерева, из которого удалён узел с минимальным ключом
     */
    private static AVLTree removeMin(AVLTree p) {
        if (p.getLeft() == null) {
            return p.getRight();
        } else {
            p.setLeft(removeMin(p.getLeft()));
            return balance(p);
        }
    }

    int getKey() {
        return key;
    }

    byte getHeight() {
        return height;
    }

    public AVLTree getRoot() {
        return root;
    }

    AVLTree getLeft() {
        return left;
    }

    AVLTree getRight() {
        return right;
    }

    private void setHeight(byte height) {
        this.height = height;
    }

    private void setLeft(AVLTree left) {
        this.left = left;
    }

    private void setRight(AVLTree right) {
        this.right = right;
    }
}
