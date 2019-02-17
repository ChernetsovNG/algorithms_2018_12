package ru.nchernetsov.heapSort;

import ru.nchernetsov.sort.SortType;

import static ru.nchernetsov.heapSort.SortSpeedTest.getSortTimeOfRandomArrayMs;

public class Main {

    public static void main(String[] args) {
        sortComparison(SortType.HEAP);
    }

    public static void sortComparison(SortType sortType) {
        // Сравним время сортировки вставками для массивов разной длины
        int[] lengths = new int[]{10, 100, 1000, 10_000, 100_000, 200_000, 500_000};

        int minLength = 10;

        double minTime = 1;

        System.out.println("Sort arrays by " + sortType + " sort");
        for (int i = 0; i < lengths.length; i++) {
            int length = lengths[i];
            double sortTime = getSortTimeOfRandomArrayMs(length, -1000, 1000, sortType);
            if (i == 0) {
                minTime = sortTime;
            }

            String output = String.format("Time to sort array of length %d is %.1f ms: relL = %d => relTime = %.1f",
                    length, sortTime, length / minLength, sortTime / minTime);
            System.out.println(output);
        }
    }
}
