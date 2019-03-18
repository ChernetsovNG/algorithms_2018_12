package ru.nchernetsov;

public class Heap {

    private int[] heapArray;

    private int currentSize;

    public Heap(int size) {
        heapArray = new int[size];
        currentSize = 0;
    }

    /**
     * Построить пирамиду из заданного массива
     *
     * @param array массив
     * @return пирамида
     */
    public static Heap buildHeap(int[] array) {
        int size = array.length;
        Heap heap = new Heap(size);
        // заполняем пирамиду данными из массива
        for (int i = 0; i < size; i++) {
            heap.insertAt(i, array[i]);
            heap.incrementSize();
        }
        // восстанавливаем свойства пирамиды
        for (int j = size / 2 - 1; j >= 0; j--) {
            heap.drown(j);
        }
        return heap;
    }

    /**
     * Получить элемент из корня пирамиды
     *
     * @return корневой элемент
     */
    public int remove() {
        int root = heapArray[0];
        heapArray[0] = heapArray[--currentSize];
        drown(0);
        return root;
    }

    /**
     * "Утопить" элемент по заданному индексу
     *
     * @param index индекс
     */
    private void drown(int index) {
        int largerChild;
        int top = heapArray[index];  // сохранение корня
        while (index < currentSize / 2) {  // пока у узла имеется хотя бы один потомок
            int leftChildIndex = 2 * index + 1;
            int rightChildIndex = leftChildIndex + 1;

            // Определение большего потомка
            if (rightChildIndex < currentSize && heapArray[leftChildIndex] < heapArray[rightChildIndex]) {
                largerChild = rightChildIndex;
            } else {
                largerChild = leftChildIndex;
            }

            if (top >= heapArray[largerChild]) {
                break;
            }

            heapArray[index] = heapArray[largerChild];  // потомок сдвигается вверх
            index = largerChild;  // переход вниз
        }
        heapArray[index] = top;  // index <- корень
    }

    private void insertAt(int index, int newNode) {
        heapArray[index] = newNode;
    }

    private void incrementSize() {
        currentSize++;
    }
}
