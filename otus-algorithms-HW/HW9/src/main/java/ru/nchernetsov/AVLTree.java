package ru.nchernetsov;

/**
 * АВЛ-дерево - сбалансированное по высоте двоичное дерево поиска,
 * в котором у любой вершины высота левого и правого поддеревьев
 * различаются не более, чем на 1
 */
public class AVLTree {

    /**
     * Корневой узел дерева
     */
    private Node root;

    public void insert(int k) {
        root = doInsert(root, k);
    }

    public void remove(int k) {
        root = doRemove(root, k);
    }

    public Node find(int k) {
        return doFind(root, k);
    }

    public int min() {
        Node minNode = findMin(root);
        return minNode.getKey();
    }

    public int max() {
        Node maxNode = findMax(root);
        return maxNode.getKey();
    }

    /**
     * Вставка ключа key в дерево
     *
     * @param p корень дерева, в которое вставляется ключ
     * @param k ключ
     * @return корень дерева после вставки ключа
     */
    private static Node doInsert(Node p, int k) {
        if (p == null) {
            return new Node(k);
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
    private static Node doRemove(Node p, int k) {
        if (p == null) {
            return null;
        } else if (k < p.getKey()) {
            p.setLeft(doRemove(p.getLeft(), k));
        } else if (k > p.getKey()) {
            p.setRight(doRemove(p.getRight(), k));
        } else {  // k == p.getKey()
            Node q = p.getLeft();
            Node r = p.getRight();
            if (r == null) {
                return q;
            } else {
                Node min = findMin(r);
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
    private static Node doFind(Node p, int k) {
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
    private static int balanceFactor(Node node) {
        return height(node.getRight()) - height(node.getLeft());
    }

    private static byte height(Node node) {
        return node != null ? node.getHeight() : 0;
    }

    /**
     * Функция восстанавливает корректное значение поля height заданного узла
     *
     * @param node узел
     */
    private static void fixHeight(Node node) {
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
    private static Node rotateRight(Node p) {
        Node q = p.getLeft();
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
    private static Node rotateLeft(Node q) {
        Node p = q.getRight();
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
    private static Node balance(Node p) {
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
    private static Node findMin(Node p) {
        return p.getLeft() != null ? findMin(p.getLeft()) : p;
    }

    /**
     * Поиск узла с максимальным ключом в дереве p
     *
     * @param p корневой узел дерева
     * @return узел с максимальным ключом
     */
    private static Node findMax(Node p) {
        return p.getRight() != null ? findMax(p.getRight()) : p;
    }

    /**
     * Удаление узла с минимальным ключом из дерева p
     *
     * @param p корневой узел дерева
     * @return корень дерева, из которого удалён узел с минимальным ключом
     */
    private static Node removeMin(Node p) {
        if (p.getLeft() == null) {
            return p.getRight();
        } else {
            p.setLeft(removeMin(p.getLeft()));
            return balance(p);
        }
    }

    public Node getRoot() {
        return root;
    }
}

class Node {

    /**
     * Ключ узла
     */
    private int key;

    /**
     * Высота поддерева с корнем в данном узле
     */
    private byte height;

    /**
     * Левое поддерево
     */
    private Node left;

    /**
     * Правое поддерево
     */
    private Node right;

    Node(int key) {
        this.key = key;
        this.left = null;
        this.right = null;
        this.height = 1;
    }

    int getKey() {
        return key;
    }

    byte getHeight() {
        return height;
    }

    Node getLeft() {
        return left;
    }

    Node getRight() {
        return right;
    }

    void setHeight(byte height) {
        this.height = height;
    }

    void setLeft(Node left) {
        this.left = left;
    }

    void setRight(Node right) {
        this.right = right;
    }
}
