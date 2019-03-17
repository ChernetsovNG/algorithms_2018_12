package ru.nchernetsov;

public class Utils {

    public static <T extends Comparable<T>> T max(T... elements) {
        if (elements.length == 1) {
            return elements[0];
        }
        T max = elements[0];
        for (int i = 1; i < elements.length; i++) {
            if (elements[i].compareTo(max) > 0) {
                max = elements[i];
            }
        }
        return max;
    }

    public static <T extends Comparable<T>> T min(T... elements) {
        if (elements.length == 1) {
            return elements[0];
        }
        T min = elements[0];
        for (int i = 1; i < elements.length; i++) {
            if (elements[i].compareTo(min) < 0) {
                min = elements[i];
            }
        }
        return min;
    }
}
