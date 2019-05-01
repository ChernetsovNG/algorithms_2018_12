package ru.nchernetsov.AhoCorasick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrieNode {

    Map<Character, TrieNode> children;

    private boolean endOfWord;

    /**
     * Массив переходов (запоминаем переходы в ленивой рекурсии), используемый
     * для вычисления суффиксных ссылок
     */
    Map<Character, TrieNode> go;

    /**
     * Вершина - родитель
     */
    TrieNode parent;

    /**
     * Суффиксная ссылка (вычисляем в ленивой рекурсии)
     */
    TrieNode suffixLink;

    /**
     * Сжатая суффиксная ссылка
     */
    TrieNode up;

    /**
     * Символ, ведущий к родителю
     */
    char charToParent;

    /**
     * Является ли вершина терминалом
     */
    boolean isLeaf;

    /**
     * Номера строк, за которые отвечает терминал
     */
    List<Integer> leafPatternNumber;

    TrieNode() {
        this.children = new HashMap<>();
        this.go = new HashMap<>();
        this.leafPatternNumber = new ArrayList<>();
    }

    public Map<Character, TrieNode> getChildren() {
        return children;
    }

    void setEndOfWord(boolean endOfWord) {
        this.endOfWord = endOfWord;
    }

    boolean isEndOfWord() {
        return endOfWord;
    }
}
