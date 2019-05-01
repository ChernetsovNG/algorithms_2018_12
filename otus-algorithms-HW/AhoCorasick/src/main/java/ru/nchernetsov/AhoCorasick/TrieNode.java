package ru.nchernetsov.AhoCorasick;

import java.util.HashMap;
import java.util.Map;

public class TrieNode {

    private String content;

    private Map<Character, TrieNode> children;

    private boolean endOfWord;

    public TrieNode(String content) {
        this.content = content;
        this.children = new HashMap<>();
    }

    public String getContent() {
        return content;
    }

    public Map<Character, TrieNode> getChildren() {
        return children;
    }

    public void setEndOfWord(boolean endOfWord) {
        this.endOfWord = endOfWord;
    }

    public boolean isEndOfWord() {
        return endOfWord;
    }

    @Override
    public String toString() {
        return "TrieNode{" +
            "content='" + content + '\'' +
            ", children=" + children +
            ", endOfWord=" + endOfWord +
            '}';
    }
}
