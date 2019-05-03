package ru.nchernetsov.heap;

/**
 * Невозрастающая пирамида (в корне - минимальный элемент)
 *
 * @param <E> тип элемента
 */
public class MinHeap<E extends Comparable<E>> extends Heap<E> {

    public MinHeap(int size) {
        super(size);
    }

    /**
     * Построить пирамиду из заданного массива
     *
     * @param array массив
     * @return пирамида
     */
    public static <E extends Comparable<E>> MinHeap<E> buildMinHeap(E[] array) {
        int size = array.length;
        MinHeap<E> heap = new MinHeap<>(size);
        fillHeapFromArray(heap, array);
        return heap;
    }

    @Override
    void drown(int index) {
        int smallerChild;
        E top = heapArray[index];  // сохранение корня
        while (index < currentSize / 2) {  // пока у узла имеется хотя бы один потомок
            int leftChildIndex = 2 * index + 1;
            int rightChildIndex = leftChildIndex + 1;

            // Определение меньшего потомка
            if (rightChildIndex >= currentSize || heapArray[leftChildIndex].compareTo(heapArray[rightChildIndex]) < 0) {
                smallerChild = leftChildIndex;
            } else {
                smallerChild = rightChildIndex;
            }

            if (top.compareTo(heapArray[smallerChild]) <= 0) {
                break;
            }

            heapArray[index] = heapArray[smallerChild];  // потомок сдвигается вверх
            index = smallerChild;  // переход вниз
        }
        heapArray[index] = top;  // index <- корень
    }

    @Override
    void siftUp(int index) {
        int i = index;
        int parent = (i - 1) / 2;
        while (i > 0 && heapArray[parent].compareTo(heapArray[i]) > 0) {
            swap(i, parent);
            i = parent;
            parent = (i - 1) / 2;
        }
    }
}
