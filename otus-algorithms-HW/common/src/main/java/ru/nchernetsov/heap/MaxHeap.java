package ru.nchernetsov.heap;

/**
 * Неубывающая пирамида (в корне - максимальный элемент)
 *
 * @param <E> тип элемента
 */
public class MaxHeap<E extends Comparable<E>> extends Heap<E> {

    public MaxHeap(int size) {
        super(size);
    }

    /**
     * Построить пирамиду из заданного массива
     *
     * @param array массив
     * @return пирамида
     */
    public static <E extends Comparable<E>> MaxHeap<E> buildMaxHeap(E[] array) {
        int size = array.length;
        MaxHeap<E> heap = new MaxHeap<>(size);
        fillHeapFromArray(heap, array);
        return heap;
    }

    @Override
    public void drown(int index) {
        int largerChild;
        E top = heapArray[index];  // сохранение корня
        while (index < currentSize / 2) {  // пока у узла имеется хотя бы один потомок
            int leftChildIndex = 2 * index + 1;
            int rightChildIndex = leftChildIndex + 1;

            // Определение большего потомка
            if (rightChildIndex < currentSize && heapArray[leftChildIndex].compareTo(heapArray[rightChildIndex]) < 0) {
                largerChild = rightChildIndex;
            } else {
                largerChild = leftChildIndex;
            }

            if (top.compareTo(heapArray[largerChild]) >= 0) {
                break;
            }

            heapArray[index] = heapArray[largerChild];  // потомок сдвигается вверх
            index = largerChild;  // переход вниз
        }
        heapArray[index] = top;  // index <- корень
    }

    @Override
    void siftUp(int index) {
        int i = index;
        int parent = (i - 1) / 2;
        while (i > 0 && heapArray[parent].compareTo(heapArray[i]) < 0) {
            swap(i, parent);
            i = parent;
            parent = (i - 1) / 2;
        }
    }
}
