package ru.nchernetsov;

public class Utils {

    /**
     * Отсортирован ли массив (по возрастанию)?
     *
     * @param array массив
     * @return true - если массив отсортирован, false - если не отсортирован
     */
    public static boolean arrayIsSorted(int[] array) {

        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                return false;
            }
        }

        return true;
    }
}
