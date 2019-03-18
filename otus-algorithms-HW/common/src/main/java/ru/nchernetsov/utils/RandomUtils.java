package ru.nchernetsov.utils;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {

    /**
     * Генерация массива случайных целых чисел в звданном диапазоне
     *
     * @param count количество генерируемых чисел
     * @param min   нижняя граница диапазона
     * @param max   верхняя граница диапазона
     * @return массив случайных чисел
     */
    public static int[] generateRandomIntArrayInRange(int count, int min, int max) {
        int[] array = new int[count];
        for (int i = 0; i < count; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
            array[i] = randomNum;
        }
        return array;
    }
}
