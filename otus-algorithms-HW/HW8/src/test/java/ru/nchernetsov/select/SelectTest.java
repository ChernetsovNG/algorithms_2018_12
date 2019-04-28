package ru.nchernetsov.select;

import org.junit.Test;
import ru.nchernetsov.common.Pair;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nchernetsov.select.Select.*;
import static ru.nchernetsov.sort.Utils.createRandomLongArray;

public class SelectTest {

    @Test
    public void nLogNselectTest1() {
        long[] array = new long[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};

        long select0 = nLogNselect(array, 1);
        long select3 = nLogNselect(array, 4);
        long select7 = nLogNselect(array, 8);
        long select10 = nLogNselect(array, 11);

        assertThat(select0).isEqualTo(0L);
        assertThat(select3).isEqualTo(3L);
        assertThat(select7).isEqualTo(7L);
        assertThat(select10).isEqualTo(10L);
    }

    @Test
    public void quickSelectTest1() {
        long[] array = new long[]{12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};

        long select0 = quickSelect(array, 1);
        long select3 = quickSelect(array, 4);
        long select7 = quickSelect(array, 8);
        long select10 = quickSelect(array, 11);

        assertThat(select0).isEqualTo(0L);
        assertThat(select3).isEqualTo(3L);
        assertThat(select7).isEqualTo(7L);
        assertThat(select10).isEqualTo(10L);
    }

    @Test
    public void randomArrayTest1() {
        // повторяем тест 10 раз подряд
        for (int i = 1; i < 11; i++) {
            long[] array = createRandomLongArray(1000, 0, 10000);

            long[] arrayCopy1 = Arrays.copyOfRange(array, 0, array.length);
            long[] arrayCopy2 = Arrays.copyOfRange(array, 0, array.length);

            long nLogNselect = nLogNselect(arrayCopy1, 500);
            long quickSelect = quickSelect(arrayCopy2, 500);

            assertThat(quickSelect).isEqualTo(nLogNselect);
        }
    }

    @Test
    public void partitionTest1() {
        long[] array = new long[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        Pair<long[], long[]> bottomUpperParts = partition(array, 2);

        long[] bottomPart = bottomUpperParts.getFirst();
        long[] upperPart = bottomUpperParts.getSecond();

        assertThat(bottomPart).containsExactly(1);
        assertThat(upperPart).containsExactly(3, 4, 5, 6, 7, 8, 9, 10);
    }
}
