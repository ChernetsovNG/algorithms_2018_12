package ru.nchernetsov.heapSort;

import ru.nchernetsov.SortType;

import static ru.nchernetsov.Utils.createRandomIntArray;
import static ru.nchernetsov.heapSort.HeapSort.heapSort;

/**
 * Тестируем скорость сортировки массива
 */
public class SortSpeedTest {

    /**
     * Возвращаем время сортировки случайного массива заданной длины в миллисекундах
     *
     * @param length   длина случайного массива
     * @param from     нижняя граница диапазона элементов массива
     * @param to       верхняя граница диапазона элементов массива
     * @param sortType вид сортировки
     * @return время сортировки массива в миллисекундах
     */
    public static double getSortTimeOfRandomArrayMs(int length, int from, int to, SortType sortType) {
        int[] array = createRandomIntArray(length, from, to);

        long startTime = System.nanoTime();

        switch (sortType) {
            case HEAP:
                heapSort(array);
                break;
        }

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1e6;
    }
}
