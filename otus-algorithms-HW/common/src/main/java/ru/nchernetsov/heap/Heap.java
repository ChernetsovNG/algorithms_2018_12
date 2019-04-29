package ru.nchernetsov.heap;

public abstract class Heap<E extends Comparable<E>> {

    E[] heapArray;

    int currentSize;

    public Heap(int size) {
        heapArray = (E[]) new Comparable[size];
        currentSize = 0;
    }

    /**
     * Получить элемент из корня пирамиды
     *
     * @return корневой элемент
     */
    public E remove() {
        if (currentSize <= 0) {
            throw new IllegalStateException("Heap is empty");
        }
        E root = heapArray[0];
        heapArray[0] = heapArray[--currentSize];
        drown(0);
        return root;
    }

    /**
     * Добавить новый элемент в пирамиду
     *
     * @param element
     */
    public void add(E element) {
        if (currentSize >= heapArray.length) {
            throw new IllegalStateException("Heap array is full");
        }
        insertAt(currentSize, element);
        incrementSize();
        siftUp(currentSize - 1);
    }

    public void swap(int i, int j) {
        E temp = heapArray[i];
        heapArray[i] = heapArray[j];
        heapArray[j] = temp;
    }

    /**
     * Заполнить пирамиду данными из массива
     *
     * @param heap  пирамида
     * @param array массив
     * @param <E>   типовой параметр
     */
    static <E extends Comparable<E>> void fillHeapFromArray(Heap<E> heap, E[] array) {
        int size = array.length;
        for (int i = 0; i < size; i++) {
            heap.insertAt(i, array[i]);
            heap.incrementSize();
        }
        // восстанавливаем свойства пирамиды
        for (int j = size / 2 - 1; j >= 0; j--) {
            heap.drown(j);
        }
    }

    /**
     * "Утопить" элемент по заданному индексу
     *
     * @param index индекс
     */
    abstract void drown(int index);

    /**
     * Дать "всплыть" элементу по заданному индексу
     *
     * @param index индекс
     */
    abstract void siftUp(int index);

    void insertAt(int index, E newNode) {
        heapArray[index] = newNode;
    }

    void incrementSize() {
        currentSize++;
    }

    public int size() {
        return currentSize;
    }

    public boolean isNotEmpty() {
        return currentSize > 0;
    }
}
