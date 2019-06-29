package ru.nchernetsov.AhoCorasick;

import java.util.List;

public class AhoCorasick {

    /**
     * Алгоритм Ахо-Корасик поиска всех вхождений слов из заданного набора (P1, ..., Pk) в заданный текст (T)
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
