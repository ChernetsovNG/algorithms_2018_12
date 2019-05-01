package ru.nchernetsov.AhoCorasick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trie {

    private TrieNode root;

    private Map<Integer, String> patterns;

    public Trie() {
        root = new TrieNode();
        patterns = new HashMap<>();
    }

    public void insert(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            current = current.getChildren().computeIfAbsent(
                word.charAt(i), c -> new TrieNode());
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

    // Функции для реализации алгоритма Ахо-Корасик

    // Функция для обработки текста
    List<String> processText(String text) {
        List<String> matchPatterns = new ArrayList<>();
        TrieNode cur = root;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            cur = getLink(cur, c);
            // todo
        }
        return matchPatterns;
    }

    // Функция для вычисления суффиксной ссылки
    private TrieNode getSuffixLink(TrieNode v) {
        if (v.suffixLink == null) {  // если суффиксная ссылка ещё не вычислена
            if (v == root || v.parent == root) {
                v.suffixLink = root;
            } else {
                v.suffixLink = getLink(getSuffixLink(v.parent), v.charToParent);
            }
        }
        return v.suffixLink;
    }

    // Функция для вычисления перехода
    private TrieNode getLink(TrieNode v, char c) {
        if (!v.go.containsKey(c)) {  // если переход по символу c ещё не вычислен
            if (v.children.containsKey(c)) {
                v.go.put(c, v.children.get(c));  // переход вниз по дереву
            } else if (v == root) {
                v.go.put(c, root);  // переход к корню
            } else {
                v.go.put(c, getLink(getSuffixLink(v), c));
            }
        }
        return v.go.get(c);
    }

    // Функция для вычисления сжатой суффиксной ссылки
    private TrieNode getUp(TrieNode v) {
        if (v.up == null) {  // если сжатая суффиксная ссылка ещё не вычислена
            if (getSuffixLink(v).isLeaf) {
                v.up = getSuffixLink(v);
            } else if (getSuffixLink(v) == root) {
                v.up = root;
            } else {
                v.up = getUp(getSuffixLink(v));
            }
        }
        return v.up;
    }

    // Функция для добавления строки в Trie
    void addString(String s, int patternNumber) {
        TrieNode cur = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!cur.children.containsKey(c)) {
                cur.children.put(c, new TrieNode());
                // Здесь также нужно обнулить указатели на переходы и сыновей
                TrieNode child = cur.children.get(c);
                child.suffixLink = null;
                child.up = null;
                child.parent = cur;
                child.charToParent = c;
                child.isLeaf = false;
            }
            cur = cur.children.get(c);
        }
        cur.isLeaf = true;
        cur.leafPatternNumber.add(patternNumber);
        patterns.put(patternNumber, s);
    }
}
