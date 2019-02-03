package ru.nchernetsov.mergesort;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nchernetsov.sort.Utils.createRandomLongArray;
import static ru.nchernetsov.sort.Utils.isArraySorted;

public class BottomUpMergeSortTest {

    @Test
    public void sortTest1() {
        long[] array = createRandomLongArray(100, -100, 100);

        BottomUpMergeSort.sort(array);

        assertThat(isArraySorted(array)).isTrue();
    }
}
