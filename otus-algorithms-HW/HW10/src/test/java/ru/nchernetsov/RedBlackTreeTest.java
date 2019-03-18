package ru.nchernetsov;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RedBlackTreeTest {

    @Test
    public void insertTest1() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        for (int i = 0; i < 1000; i++) {
            tree.insert(i);
        }
        assertThat(tree.size()).isEqualTo(1000);
    }
}
