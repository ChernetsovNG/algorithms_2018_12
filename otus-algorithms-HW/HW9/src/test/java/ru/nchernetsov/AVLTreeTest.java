package ru.nchernetsov;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class AVLTreeTest {

    @Test
    public void avlTreeTest1() {
        AVLTree avlTree = new AVLTree();

        avlTree.insert(10);
        avlTree.insert(5);
        avlTree.insert(20);
        avlTree.insert(4);
        avlTree.insert(7);
        avlTree.insert(12);
        avlTree.insert(2);
        avlTree.insert(10);

        assertThat(avlTree.getRoot().getHeight()).isEqualTo((byte) 4);
        assertThat(avlTree.min()).isEqualTo(2);
        assertThat(avlTree.max()).isEqualTo(20);

        assertThat(avlTree.getRoot().getKey()).isEqualTo(10);
        assertThat(avlTree.getRoot().getLeft().getKey()).isEqualTo(5);
        assertThat(avlTree.getRoot().getRight().getKey()).isEqualTo(20);
        assertThat(avlTree.getRoot().getLeft().getLeft().getKey()).isEqualTo(4);
        assertThat(avlTree.getRoot().getLeft().getRight().getKey()).isEqualTo(7);
        assertThat(avlTree.getRoot().getRight().getLeft().getKey()).isEqualTo(12);
        assertThat(avlTree.getRoot().getRight().getRight()).isNull();
        assertThat(avlTree.getRoot().getLeft().getLeft().getLeft().getKey()).isEqualTo(2);
        assertThat(avlTree.getRoot().getLeft().getLeft().getRight()).isNull();
        assertThat(avlTree.getRoot().getLeft().getRight().getLeft()).isNull();
        assertThat(avlTree.getRoot().getLeft().getRight().getRight().getKey()).isEqualTo(10);

    }
}