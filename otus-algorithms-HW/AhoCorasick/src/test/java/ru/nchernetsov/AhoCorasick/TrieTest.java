package ru.nchernetsov.AhoCorasick;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TrieTest {

    @Test
    public void insertTest() {
        Trie trie = new Trie();
        TrieNode root = trie.getRoot();
        assertThat(root.getChildren()).isEmpty();
        trie.insert("word");
        assertThat(root.getChildren()).hasSize(1);
        assertThat(root.getChildren().get('w').getChildren()).hasSize(1);
        assertThat(root.getChildren().get('w').getChildren().get('o').getChildren()).hasSize(1);
        assertThat(root.getChildren().get('w').getChildren().get('o').getChildren().get('r').getChildren()).hasSize(1);
        assertThat(root.getChildren().get('w').getChildren().get('o').getChildren().get('r').getChildren()
            .get('d').getChildren()).isEmpty();
    }

    @Test
    public void searchTest() {
        Trie trie = new Trie();
        trie.insert("Programming");
        trie.insert("is");
        trie.insert("a");
        trie.insert("way");
        trie.insert("of");
        trie.insert("life");
        assertThat(trie.search("way")).isTrue();
        assertThat(trie.search("waY")).isFalse();
    }

    @Test
    public void deleteTest() {
        Trie trie = new Trie();
        trie.insert("Programming");
        trie.insert("is");
        trie.insert("a");
        trie.insert("way");
        trie.insert("of");
        trie.insert("life");
        assertThat(trie.search("way")).isTrue();
        trie.delete("way");
        assertThat(trie.search("way")).isFalse();
    }
}
