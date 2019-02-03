package ru.nchernetsov.countingSort;

import static ru.nchernetsov.sort.Utils.max;

/**
 * Сортировка подсчётом
 */
public class CountingSort {

    /**
     * Предполагается, что элементы во входном массиве имеют значяения от 0 до k - 1, где k > 0
     *
     * @param array входной массив
     * @return отсортированный массив
     */
    public static int[] sort(int[] array) {
        int n = array.length;

        int k = max(array) + 1;

        int[] count = new int[k];
        int[] sorted = new int[n];

        for (int i = 0; i < k; i++) {
            count[i] = 0;
        }

        // подсчёт количества каждого элемента во входном массиве
        for (int elem : array) {
            count[elem] = count[elem] + 1;
        }

        // подсчёт количества элементов, <= k - 1
        for (int j = 1; j < k; j++) {
            count[j] = count[j] + count[j - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            count[array[i]] = count[array[i]] - 1;
            sorted[count[array[i]]] = array[i];
        }

        return sorted;
    }

    /**
     * Вариант сортировки подсчётом для сортировки по заданному десятичному разряду
     *
     * @param array      массив
     * @param digitPlace десятичный разряд (1, 10, 100, ...)
     * @return отсортированный массив
     */
    public static int[] sort(int[] array, int digitPlace) {
        int n = array.length;

        int[] sorted = new int[n];
        int[] count = new int[10];

        for (int elem : array) {
            int digit = getDigit(elem, digitPlace);
            count[digit] += 1;
        }

        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            int digit = getDigit(array[i], digitPlace);
            sorted[count[digit] - 1] = array[i];
            count[digit]--;
        }

        return sorted;

    }

    private static int getDigit(int value, int digitPlace) {
        return (value / digitPlace) % 10;
    }
}
