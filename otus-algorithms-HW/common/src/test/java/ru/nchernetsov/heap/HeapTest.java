package ru.nchernetsov.heap;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nchernetsov.heap.MaxHeap.buildMaxHeap;
import static ru.nchernetsov.heap.MinHeap.buildMinHeap;

public class HeapTest {

    @Test
    public void removeMinHeapTest1() {
        MinHeap<Integer> heap = buildMinHeap(new Integer[]{3, 2, 1});

        Integer element1 = heap.remove();
        Integer element2 = heap.remove();
        Integer element3 = heap.remove();

        assertThat(element1).isEqualTo(1);
        assertThat(element2).isEqualTo(2);
        assertThat(element3).isEqualTo(3);

        assertThat(heap.size()).isEqualTo(0);
    }

    @Test(expected = IllegalStateException.class)
    public void removeMinHeapTest2() {
        MinHeap<Integer> heap = buildMinHeap(new Integer[]{3, 2, 1});

        Integer element1 = heap.remove();
        Integer element2 = heap.remove();
        Integer element3 = heap.remove();

        assertThat(element1).isEqualTo(1);
        assertThat(element2).isEqualTo(2);
        assertThat(element3).isEqualTo(3);

        heap.remove();
    }

    @Test
    public void removeMaxHeapTest1() {
        MaxHeap<Integer> heap = buildMaxHeap(new Integer[]{3, 2, 1});

        Integer element1 = heap.remove();
        Integer element2 = heap.remove();
        Integer element3 = heap.remove();

        assertThat(element1).isEqualTo(3);
        assertThat(element2).isEqualTo(2);
        assertThat(element3).isEqualTo(1);

        assertThat(heap.size()).isEqualTo(0);
    }

    @Test(expected = IllegalStateException.class)
    public void removeMaxHeapTest2() {
        MaxHeap<Integer> heap = buildMaxHeap(new Integer[]{3, 2, 1});

        Integer element1 = heap.remove();
        Integer element2 = heap.remove();
        Integer element3 = heap.remove();

        assertThat(element1).isEqualTo(3);
        assertThat(element2).isEqualTo(2);
        assertThat(element3).isEqualTo(1);

        heap.remove();
    }
}
