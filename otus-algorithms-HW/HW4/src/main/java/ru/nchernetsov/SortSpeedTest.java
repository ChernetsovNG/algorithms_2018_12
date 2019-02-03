package ru.nchernetsov;

import ru.nchernetsov.sort.SortType;

import static ru.nchernetsov.sort.Utils.createRandomIntArray;
import static ru.nchernetsov.insertion.InsertionSort.insertionSort;
import static ru.nchernetsov.shell.ShellSort.shellSort;

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
                shellSort(array);
                break;
        }

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1e6;
    }
}
