package ru.nchernetsov;

import org.junit.Test;

import java.util.Random;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class TreapTest {

    @Test
    public void treapTest1() {
        Treap treap = new Treap(7, 10);

        treap = treap.add(4, 6);
        treap = treap.add(13, 8);
        treap = treap.add(2, 4);
        treap = treap.add(6, 2);
        treap = treap.add(9, 7);
        treap = treap.add(14, 4);
        treap = treap.add(0, 3);
        treap = treap.add(3, 3);
        treap = treap.add(5, 1);
        treap = treap.add(11, 3);

        assertThat(treap.getX()).isEqualTo(7);
        assertThat(treap.getLeft().getX()).isEqualTo(4);
        assertThat(treap.getRight().getX()).isEqualTo(13);
        assertThat(treap.getLeft().getLeft().getX()).isEqualTo(2);
        assertThat(treap.getLeft().getRight().getX()).isEqualTo(6);
        assertThat(treap.getLeft().getLeft().getLeft().getX()).isEqualTo(0);
        assertThat(treap.getLeft().getLeft().getRight().getX()).isEqualTo(3);
        assertThat(treap.getLeft().getRight().getLeft().getX()).isEqualTo(5);
        assertThat(treap.getLeft().getRight().getRight()).isNull();
        assertThat(treap.getRight().getLeft().getX()).isEqualTo(9);
        assertThat(treap.getRight().getLeft().getLeft()).isNull();
        assertThat(treap.getRight().getLeft().getRight().getX()).isEqualTo(11);
        assertThat(treap.getRight().getRight().getX()).isEqualTo(14);
        assertThat(treap.getRight().getRight().getLeft()).isNull();
        assertThat(treap.getRight().getRight().getRight()).isNull();
    }

    @Test
    public void treapTest2() {
        Treap treap = new Treap(0, 0);
        for (int i = 1; i < 1000; i++) {
            treap = treap.add(i, 1001 - i);
        }

        Random r = new Random();
        for (int i = 0; i < 50; i++) {
            int n = r.nextInt(1000);
            treap = treap.remove(n);
        }
    }

    @Test
    public void treapTest3() {
        Treap tree = new Treap(0, 0);
        for (int i = 1; i < 1000; i++)
            tree = tree.add(i);

        for (int i = 0; i < 500; i++) {
            System.out.println(i);
            tree = tree.remove(i);
        }
    }
}
