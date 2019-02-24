package ru.nchernetsov.sort;

import java.util.Random;

public class Utils {

    /**
     * Отсортирован ли массив (по возрастанию)?
     *
     * @param array массив
     * @return true - если массив отсортирован, false - если не отсортирован
     */
    public static boolean isArraySorted(int[] array) {

        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                return false;
            }
        }

        return true;
    }

    public static boolean isArraySorted(long[] array) {

        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                return false;
            }
        }

        return true;
    }

    public static int[] createRandomIntArray(int length, int from, int to) {
        int[] array = new int[length];

        Random random = new Random();

        for (int i = 0; i < length; i++) {
            array[i] = random.nextInt(to + 1 - from) + from;
        }

        return array;
    }

    public static long[] createRandomLongArray(int length, int from, int to) {
        long[] array = new long[length];

        Random random = new Random();

        for (int i = 0; i < length; i++) {
            array[i] = (long) (random.nextInt(to + 1 - from) + from);
        }

        return array;
    }

    public static int max(int[] array) {
        int max = Integer.MIN_VALUE;
        for (int elem : array) {
            if (elem > max) {
                max = elem;
            }
        }
        return max;
    }

    public static long max(long[] array) {
        long max = Long.MIN_VALUE;
        for (long elem : array) {
            if (elem > max) {
                max = elem;
            }
        }
        return max;
    }
}
