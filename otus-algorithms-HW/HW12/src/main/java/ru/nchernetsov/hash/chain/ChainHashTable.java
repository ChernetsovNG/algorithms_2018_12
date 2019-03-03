package ru.nchernetsov.hash.chain;

import ru.nchernetsov.RedBlackNode;
import ru.nchernetsov.RedBlackTree;

import java.lang.reflect.Array;

/**
 * Хеш-таблица, реализующая метод цепочек
 */
public class ChainHashTable<T extends Number & Comparable<T>> {

    /**
     * Когда размер превышает этот порог, связный список преобразуется в дерево, и наоборот
     */
    private static final int LIST_TO_TREE_THRESHOLD_DEFAULT = 32;

    private Entry<T>[] hashArray;

    private int arraySize;

    public ChainHashTable(int size) {
        arraySize = size;
        hashArray = (Entry<T>[]) Array.newInstance(Entry.class, arraySize);
        for (int i = 0; i < arraySize; i++) {  // заполнение массива списками
            hashArray[i] = new Entry<>(LIST_TO_TREE_THRESHOLD_DEFAULT);
        }
    }

    public ChainHashTable(int size, int listToTreeThreshold) {
        arraySize = size;
        hashArray = (Entry<T>[]) Array.newInstance(Entry.class, arraySize);
        for (int i = 0; i < arraySize; i++) {  // заполнение массива списками
            hashArray[i] = new Entry<>(listToTreeThreshold);
        }
    }

    /**
     * Вставка нового элемента
     *
     * @param elem элемент
     */
    public void insert(T elem) {
        int hash = hash(elem);
        hashArray[hash].insert(elem);
    }

    /**
     * Удаление элемента
     *
     * @param elem элемент
     */
    public void delete(T elem) {
        int hash = hash(elem);
        hashArray[hash].delete(elem);
    }

    public T find(T key) {
        int hash = hash(key);
        return hashArray[hash].find(key);
    }

    private int hash(T key) {
        return key.intValue() % arraySize;
    }

    public Entry<T>[] getHashArray() {
        return hashArray;
    }
}

/**
 * Ячейка хеш-таблицы, может быть связным списком или деревом поиска
 * в зависимости от количества элементов в ячейке
 *
 * @param <T> тип ключа
 */
class Entry<T extends Number & Comparable<T>> {

    private final int listToTreeThreshold;

    private final SortedList<T> list = new SortedList<>();

    private final RedBlackTree<T> tree = new RedBlackTree<>();

    private int size = 0;

    Entry(int listToTreeThreshold) {
        this.listToTreeThreshold = listToTreeThreshold;
    }

    void insert(T elem) {
        // если элементов мало, то вставляем новый элемент в связный список
        if (size >= 0 && size < listToTreeThreshold - 1) {
            list.insert(new Link<>(elem));
        } else {  // иначе преобразуем список в дерево, и вставляем новый элемент в дерево
            // копируем все элементы из списка в дерево
            tree.clear();
            for (T listElem : list) {
                tree.insert(listElem);
            }
            list.clear();  // очищаем список
            tree.insert(elem);  // вставляем в дерево новый элемент
        }
        size++;
    }

    void delete(T elem) {
        if (size == 0) {
            return;
        }
        // если элементов мало, то удаляем элемент из списка
        if (size > 0 && size < listToTreeThreshold) {
            list.delete(elem);
        } else if (size == listToTreeThreshold) {  // если элементов ровно пороговое значение, то преобразуем дерево снова в список
            list.clear();
            for (T treeElem : tree) {
                list.insert(new Link<>(treeElem));
            }
            tree.clear();
            list.delete(elem);
        } else {  // иначе удаляем элемент из дерева
            tree.remove(elem);
        }
        size--;
    }

    /**
     * Поиск элемента
     *
     * @param key ключ искомого элемента
     * @return найденный элемент
     */
    T find(T key) {
        if (size == 0) {
            return null;
        } else if (size < listToTreeThreshold) {
            Link<T> link = list.find(key);
            return link.getKey();
        } else {
            RedBlackNode<T> node = tree.search(key);
            return node.key;
        }
    }

    public SortedList<T> getList() {
        return list;
    }

    public RedBlackTree<T> getTree() {
        return tree;
    }

    public int getSize() {
        return size;
    }
}
