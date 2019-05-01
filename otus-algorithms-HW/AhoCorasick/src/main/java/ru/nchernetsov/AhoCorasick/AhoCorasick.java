package ru.nchernetsov.AhoCorasick;

import java.util.List;

public class AhoCorasick {

    /**
     * Алгоритм Ахо-Корасик поиска вхождений слов из заданного набора в заданный текст
     *
     * @param text  текст
     * @param words набор слов
     */
    public static void ahoCorasickAlg(String text, String... words) {
        Trie trie = new Trie();
        int i = 0;
        for (String word : words) {
            trie.addString(word, ++i);
        }

        List<String> matches = trie.processText(text);

        System.out.println(matches);
    }
}
