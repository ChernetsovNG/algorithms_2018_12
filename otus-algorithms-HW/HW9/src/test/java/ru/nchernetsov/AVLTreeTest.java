package ru.nchernetsov;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class AVLTreeTest {

    @Test
    public void avlTreeTest1() {
        AVLTree avlTree = new AVLTree(10);

        avlTree.insert(5);
        avlTree.insert(20);
        avlTree.insert(4);
        avlTree.insert(7);
        avlTree.insert(12);
        avlTree.insert(2);
        avlTree.insert(10);

        assertThat(avlTree.getHeight()).isEqualTo((byte) 4);
        assertThat(avlTree.min()).isEqualTo(2);
        assertThat(avlTree.max()).isEqualTo(20);

        assertThat(avlTree.getKey()).isEqualTo(10);
        assertThat(avlTree.getLeft().getKey()).isEqualTo(5);
        assertThat(avlTree.getRight().getKey()).isEqualTo(20);
        assertThat(avlTree.getLeft().getLeft().getKey()).isEqualTo(4);
        assertThat(avlTree.getLeft().getRight().getKey()).isEqualTo(7);
        assertThat(avlTree.getRight().getLeft().getKey()).isEqualTo(12);
        assertThat(avlTree.getRight().getRight()).isNull();
        assertThat(avlTree.getLeft().getLeft().getLeft().getKey()).isEqualTo(2);
        assertThat(avlTree.getLeft().getLeft().getRight()).isNull();
        assertThat(avlTree.getLeft().getRight().getLeft()).isNull();
        assertThat(avlTree.getLeft().getRight().getRight().getKey()).isEqualTo(10);

    }
}
