package ru.nchernetsov.heapSort;

import static ru.nchernetsov.heapSort.Heap.buildHeap;

public class HeapSort {

    /**
     * Пирамидальная сортировка массива
     *
     * @param array сортируемый массив
     */
    public static void heapSort(int[] array) {
        int size = array.length;

        // преобразование массива в пирамиду
        Heap heap = buildHeap(array);

        // извлечение элементов из корня пирамиды с сохранением в конце массива
        for (int i = size - 1; i >= 0; i--) {
            int biggestNode = heap.remove();
            array[i] = biggestNode;
        }
    }
}
