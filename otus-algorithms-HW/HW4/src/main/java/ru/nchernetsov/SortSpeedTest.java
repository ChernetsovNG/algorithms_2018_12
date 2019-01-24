package ru.nchernetsov;

import java.util.Random;

import static ru.nchernetsov.insertion.InsertionSort.insertionSort;

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
            case INSERTION:
                insertionSort(array);
                break;
            case SHELL:
                break;
        }

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1e6;
    }

    public static int[] createRandomIntArray(int length, int from, int to) {
        int[] array = new int[length];

        Random random = new Random();

        for (int i = 0; i < length; i++) {
            array[i] = random.nextInt(to + 1 - from) + from;
        }

        return array;
    }
}
