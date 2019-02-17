package ru.nchernetsov.insertion;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nchernetsov.sort.Utils.createRandomIntArray;
import static ru.nchernetsov.sort.Utils.isArraySorted;
import static ru.nchernetsov.insertion.InsertionSort.insertionSort;

public class InsertionSortTest {

    @Test
    public void insertionSortTest1() {
        int[] array = createRandomIntArray(100, -100, 100);

        insertionSort(array);

        assertThat(isArraySorted(array)).isTrue();
    }
}
