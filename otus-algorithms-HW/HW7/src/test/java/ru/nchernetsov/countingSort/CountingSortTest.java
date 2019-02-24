package ru.nchernetsov.countingSort;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nchernetsov.sort.Utils.createRandomIntArray;
import static ru.nchernetsov.sort.Utils.isArraySorted;

public class CountingSortTest {

    @Test
    public void sortTest1() {
        int[] array = createRandomIntArray(10000, 0, 1000);

        int[] sortedArray = CountingSort.sort(array);

        assertThat(isArraySorted(sortedArray)).isTrue();
    }

    @Test
    public void sortTest2() {
        int[] array = new int[]{11, 9, 355, 212, 77};

        // сортировка по единицам
        int[] sortedArray1 = CountingSort.sort(array, 1);

        assertThat(sortedArray1).containsExactly(11, 212, 355, 77, 9);

        // сортировка по десяткам
        int[] sortedArray10 = CountingSort.sort(array, 10);

        assertThat(sortedArray10).containsExactly(9, 11, 212, 355, 77);

        // сортировка по сотням
        int[] sortedArray100 = CountingSort.sort(array, 100);

        assertThat(sortedArray100).containsExactly(11, 9, 77, 212, 355);
    }
}
