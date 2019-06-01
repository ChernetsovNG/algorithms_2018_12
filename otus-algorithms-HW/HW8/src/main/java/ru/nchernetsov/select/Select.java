package ru.nchernetsov.select;

import ru.nchernetsov.tuples.Pair;

import java.util.Arrays;

import static ru.nchernetsov.sort.InsertionSort.insertionSort;

public class Select {

    /**
     * Наивный алгоритм выбора k-й порядковой статистика в заданном массиве. Время работы: O(n*log(n))
     *
     * @param array массив
     * @param k     номер порядковой статистики (отсчитывается от единицы)
     * @return k-ю порядковую статистику в массиве
     */
    public static long nLogNselect(long[] array, int k) {
        if (k < 1 || k > array.length) {
            throw new IllegalArgumentException("Wrong number k = " + k);
        }
        Arrays.sort(array);
        return array[k - 1];
    }

    /**
     * Алгоритм выбора k-й порядковой статистики, работающий за время O(n)
     *
     * @param array массив
     * @param k     номер порядковой статистики (отсчитывается от единицы)
     * @return k-ю порядковую статистику в массиве
     */
    public static long quickSelect(long[] array, int k) {
        /*
        1. Разделение: оригинальный массив делится на ⌈n/5⌉ частей
        2. Для каждого подмассива находится медиана; они объединяются в массив медиан
        3. В массиве медиан рекурсивно при помощи Select ищется медиана медиан, далее x
        4. Применяется Partition (как в Quicksort) для разделения массива на две части - меньше либо равных x, и больших элементов.
           Получим k "меньших" элементов, и (n−k) "больших".
        5. Если k = i, то возвращаем x как целевой элемент.
           Иначе, ищем либо i-й среди меньшей части, либо, если i > k, (i−k)-й - среди большей части
         */
        if (k < 1 || k > array.length) {
            throw new IllegalArgumentException("Wrong number k = " + k + " in array = " + Arrays.toString(array));
        }

        if (array.length <= 5) {
            insertionSort(array);
            return array[k - 1];
        }

        // 1. Разделение
        long[][] parts = chunk(array);

        // 2. Нахождение медиан подмассивов
        long[] medians = getMedians(parts);

        // 3. Путём рекурсивного вызова процедуры select находим медиану медиан
        long medianOfMedians;
        if (medians.length == 1 || medians.length == 2) {
            insertionSort(medians);
            medianOfMedians = medians[0];
        } else if (medians.length % 2 == 0) {  // если медиан четное количество, то берём нижнюю медиану
            medianOfMedians = quickSelect(medians, (int) Math.floor((medians.length + 1) / 2.0));
        } else {
            medianOfMedians = quickSelect(medians, (medians.length + 1) / 2);
        }

        // 4. Входной массив разбивается на части относительно медианы медиан
        Pair<long[], long[]> bottomUpperParts = partition(array, medianOfMedians);

        // части разбиения
        long[] bottomPart = bottomUpperParts.getFirst();
        long[] upperPart = bottomUpperParts.getSecond();

        int bottomPartLength = bottomPart.length;
        int upperPartLength = upperPart.length;
        int pivotPartLength = array.length - bottomPartLength - upperPartLength;

        // 5. Может получиться, что в массиве было несколько элементов со значением, равным опорному. Это нужно учесть
        if (k <= bottomPartLength) {
            return quickSelect(bottomPart, k);
        } else if (k <= bottomPartLength + pivotPartLength) {
            return medianOfMedians;
        } else {
            return quickSelect(upperPart, k - bottomPartLength - pivotPartLength);
        }
    }

    // разделение массива на части по 5 элементов
    private static long[][] chunk(long[] array) {
        int n = array.length;
        int partsCount = (int) Math.ceil(n / 5.0);
        long[][] parts = new long[partsCount][];
        // части до предпоследней включительно
        for (int i = 0; i < partsCount - 1; i++) {
            long[] part = new long[5];
            System.arraycopy(array, i * 5, part, 0, 5);
            parts[i] = part;
        }
        // последняя часть может содержать меньше 5-ти элементов
        int lastI = partsCount - 1;
        long[] lastPart;
        int nMod5 = n % 5;
        if (nMod5 == 0) {
            lastPart = new long[5];
            System.arraycopy(array, lastI * 5, lastPart, 0, 5);
        } else {
            lastPart = new long[nMod5];
            System.arraycopy(array, lastI * 5, lastPart, 0, nMod5);
        }
        parts[lastI] = lastPart;
        return parts;
    }

    private static long[] getMedians(long[][] parts) {
        int partsCount = parts.length;
        for (long[] part : parts) {
            insertionSort(part);
        }
        long[] medians = new long[partsCount];
        for (int i = 0; i < partsCount - 1; i++) {
            medians[i] = parts[i][2];
        }
        long[] lastPart = parts[partsCount - 1];
        int lastPartLength = lastPart.length;
        long lastMedian;
        if (lastPartLength == 1 || lastPartLength == 2) {
            lastMedian = lastPart[0];
        } else if (lastPartLength == 3 || lastPartLength == 4) {
            lastMedian = lastPart[1];
        } else if (lastPart.length == 5) {
            lastMedian = lastPart[2];
        } else {
            throw new IllegalArgumentException("lastPart.length = " + lastPart.length);
        }
        medians[partsCount - 1] = lastMedian;
        return medians;
    }

    /**
     * Разбивает заданный массив на 2 подмассива: с элементами, меньшими опорного, и с элементами, большими опорного
     *
     * @param array массив
     * @param pivot опорный элемент
     * @return пару из двух подмассивов
     */
    static Pair<long[], long[]> partition(long[] array, long pivot) {
        int smallerCount = 0;
        int largerCount = 0;

        for (long elem : array) {
            if (elem < pivot) {
                smallerCount++;
            } else if (elem > pivot) {
                largerCount++;
            }
        }

        long[] smallerArray = new long[smallerCount];
        long[] largerArray = new long[largerCount];

        int i1 = 0;
        int i2 = 0;
        for (long elem : array) {
            if (elem < pivot) {
                smallerArray[i1++] = elem;
            } else if (elem > pivot) {
                largerArray[i2++] = elem;
            }
        }

        return Pair.of(smallerArray, largerArray);
    }
}
