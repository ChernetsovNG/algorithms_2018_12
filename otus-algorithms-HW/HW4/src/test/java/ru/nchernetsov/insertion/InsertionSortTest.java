package ru.nchernetsov.insertion;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nchernetsov.SortSpeedTest.createRandomIntArray;
import static ru.nchernetsov.Utils.arrayIsSorted;
import static ru.nchernetsov.insertion.InsertionSort.insertionSort;

public class InsertionSortTest {

    @Test
    public void insertionSortTest1() {
        int[] array = createRandomIntArray(100, -100, 100);

        insertionSort(array);

        assertThat(arrayIsSorted(array)).isTrue();
    }
}
