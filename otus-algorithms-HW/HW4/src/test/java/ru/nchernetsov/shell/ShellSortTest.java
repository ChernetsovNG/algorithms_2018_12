package ru.nchernetsov.shell;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nchernetsov.sort.Utils.createRandomIntArray;
import static ru.nchernetsov.sort.Utils.isArraySorted;
import static ru.nchernetsov.shell.ShellSort.shellSort;

public class ShellSortTest {

    @Test
    public void shellSortTest1() {
        int[] array = createRandomIntArray(100, -100, 100);

        shellSort(array);

        assertThat(isArraySorted(array)).isTrue();
    }
}
