package ru.nchernetsov.hash.chain;

import org.junit.Test;
import ru.nchernetsov.RedBlackTree;

import static org.assertj.core.api.Assertions.assertThat;

public class ChainHashTableTest {

    @Test
    public void chainHashTableTest1() {
        ChainHashTable<Integer> hashTable = new ChainHashTable<>(10);

        for (int i = 0; i < 100; i++) {
            hashTable.insert(i);
        }

        Entry<Integer>[] hashArray = hashTable.getHashArray();
        for (Entry<Integer> entry : hashArray) {
            assertThat(entry.getSize()).isEqualTo(10);
        }
    }

    @Test
    public void chainHashTableTest2() {
        // Добавляем 32 элемента в одну и ту же ячейку таблицы, и проверяем, что список превратился в дерево
        ChainHashTable<Integer> hashTable = new ChainHashTable<>(10);

        for (int i = 0; i < 32; i++) {
            hashTable.insert(7);
        }

        Entry<Integer>[] hashArray = hashTable.getHashArray();

        Entry<Integer> entry0 = hashArray[0];
        Entry<Integer> entry1 = hashArray[1];
        Entry<Integer> entry2 = hashArray[2];
        Entry<Integer> entry3 = hashArray[3];
        Entry<Integer> entry4 = hashArray[4];
        Entry<Integer> entry5 = hashArray[5];
        Entry<Integer> entry6 = hashArray[6];
        Entry<Integer> entry7 = hashArray[7];
        Entry<Integer> entry8 = hashArray[8];
        Entry<Integer> entry9 = hashArray[9];

        assertThat(entry0.getSize()).isEqualTo(0);
        assertThat(entry1.getSize()).isEqualTo(0);
        assertThat(entry2.getSize()).isEqualTo(0);
        assertThat(entry3.getSize()).isEqualTo(0);
        assertThat(entry4.getSize()).isEqualTo(0);
        assertThat(entry5.getSize()).isEqualTo(0);
        assertThat(entry6.getSize()).isEqualTo(0);
        assertThat(entry7.getSize()).isEqualTo(32);
        assertThat(entry8.getSize()).isEqualTo(0);
        assertThat(entry9.getSize()).isEqualTo(0);

        assertThat(entry7.getList().getSize()).isEqualTo(0);
        assertThat(entry7.getTree().size()).isEqualTo(32);
        assertThat(entry7.getTree().getHeight()).isLessThan(31);
    }
}
