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

        AVLTree avlTreeRoot = avlTree.getRoot();

        assertThat(avlTreeRoot.getHeight()).isEqualTo((byte) 4);
        assertThat(avlTreeRoot.min()).isEqualTo(2);
        assertThat(avlTreeRoot.max()).isEqualTo(20);

        assertThat(avlTreeRoot.getKey()).isEqualTo(10);
        assertThat(avlTreeRoot.getLeft().getKey()).isEqualTo(5);
        assertThat(avlTreeRoot.getRight().getKey()).isEqualTo(20);
        assertThat(avlTreeRoot.getLeft().getLeft().getKey()).isEqualTo(4);
        assertThat(avlTreeRoot.getLeft().getRight().getKey()).isEqualTo(7);
        assertThat(avlTreeRoot.getRight().getLeft().getKey()).isEqualTo(12);
        assertThat(avlTreeRoot.getRight().getRight()).isNull();
        assertThat(avlTreeRoot.getLeft().getLeft().getLeft().getKey()).isEqualTo(2);
        assertThat(avlTreeRoot.getLeft().getLeft().getRight()).isNull();
        assertThat(avlTreeRoot.getLeft().getRight().getLeft()).isNull();
        assertThat(avlTreeRoot.getLeft().getRight().getRight().getKey()).isEqualTo(10);
    }
}
