package ru.nchernetsov.heapSort;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nchernetsov.sort.Utils.createRandomIntArray;
import static ru.nchernetsov.sort.Utils.isArraySorted;
import static ru.nchernetsov.heapSort.HeapSort.heapSort;

public class HeapSortTest {

    @Test
    public void heapSortTest1() {
        int[] array = createRandomIntArray(100, -100, 100);

        heapSort(array);

        assertThat(isArraySorted(array)).isTrue();
    }
}
