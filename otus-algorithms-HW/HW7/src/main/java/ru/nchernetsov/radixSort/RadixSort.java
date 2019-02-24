package ru.nchernetsov.radixSort;

import ru.nchernetsov.countingSort.CountingSort;

import static ru.nchernetsov.sort.Utils.max;

public class RadixSort {

    public static int[] sort(int[] input) {

        // Для определения количества десятичных разрядов находим максимальный элемент
        int largestNum = max(input);

        for (int digitPlace = 1; largestNum / digitPlace > 0; digitPlace *= 10) {
            // Для сортировки каждого десятичного разряда используем сортировку подсчётом
            input = CountingSort.sort(input, digitPlace);
        }

        return input;
    }
}
