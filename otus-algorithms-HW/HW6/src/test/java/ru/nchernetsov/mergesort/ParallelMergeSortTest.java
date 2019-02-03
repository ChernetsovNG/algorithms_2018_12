package ru.nchernetsov.mergesort;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static ru.nchernetsov.sort.Utils.createRandomLongArray;
import static ru.nchernetsov.sort.Utils.isArraySorted;

public class ParallelMergeSortTest {

    @Test
    public void sortTest1() {
        long[] array = createRandomLongArray(1000, -100, 100);

        ParallelMergeSort.sort(array, 4);

        assertThat(isArraySorted(array)).isTrue();
    }

    @Test
    public void sortTest2() {
        long[] array = createRandomLongArray(1000, -100, 100);

        ParallelMergeSort.sort(array, 8);

        assertThat(isArraySorted(array)).isTrue();
    }
}
