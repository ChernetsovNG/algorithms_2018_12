package ru.nchernetsov.radixSort;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nchernetsov.sort.Utils.createRandomIntArray;
import static ru.nchernetsov.sort.Utils.isArraySorted;

public class RadixSortTest {

    @Test
    public void sortTest1() {
        int[] array = new int[]{11, 9, 355, 212, 77};

        int[] sortedArray = RadixSort.sort(array);

        assertThat(isArraySorted(sortedArray)).isTrue();
    }

    @Test
    public void sortTest2() {
        int[] array = createRandomIntArray(10000, 0, 1000);

        int[] sortedArray = RadixSort.sort(array);

        assertThat(isArraySorted(sortedArray)).isTrue();
    }
}
