package ru.nchernetsov.AhoCorasick;

import java.util.Map;

public class Trie {

    private TrieNode root;

    public Trie() {
        root = new TrieNode("");
    }

    public void insert(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            String currentContent = current.getContent();
            current = current.getChildren().computeIfAbsent(
                word.charAt(i), c -> new TrieNode(currentContent + ch));
        }
        current.setEndOfWord(true);
    }

    public boolean search(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode node = current.getChildren().get(ch);
            if (node == null) {
                return false;
            }
            current = node;
        }
        return current.isEndOfWord();
    }

    public void delete(String word) {
        delete(root, word, 0);
    }

    private boolean delete(TrieNode current, String word, int index) {
        if (index == word.length()) {
            if (!current.isEndOfWord()) {
                return false;
            }
            current.setEndOfWord(false);
            return current.getChildren().isEmpty();
        }
        char ch = word.charAt(index);
        TrieNode node = current.getChildren().get(ch);
        if (node == null) {
            return false;
        }
        boolean shouldDeleteCurrentNode = delete(node, word, index + 1) && !node.isEndOfWord();
        if (shouldDeleteCurrentNode) {
            current.getChildren().remove(ch);
            return current.getChildren().isEmpty();
        }
        return false;
    }

    public TrieNode getRoot() {
        return root;
    }

    @Override
    public String toString() {
        return "Trie{" +
            "root=" + root +
            '}';
    }
}
