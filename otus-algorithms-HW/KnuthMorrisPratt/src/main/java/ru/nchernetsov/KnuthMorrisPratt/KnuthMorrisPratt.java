package ru.nchernetsov.KnuthMorrisPratt;

import java.util.ArrayList;
import java.util.List;

public class KnuthMorrisPratt {

    public static List<Integer> KMP(String text, String pattern) {
        int m = pattern.length();
        int n = text.length();
        int[] table = computePrefix(pattern);
        int q = 0;  // сколько символов совпало
        List<Integer> matches = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            while (q > 0 && pattern.charAt(q) != text.charAt(i)) {
                q = table[q - 1];
            }
            if (pattern.charAt(q) == text.charAt(i)) {
                q += 1;
            }
            if (q == m) {  // совпали все символы паттерна
                matches.add(i - m + 1);
                q = table[q - 1];
            }
        }
        return matches;
    }

    public static int[] computePrefix(String pattern) {
        int m = pattern.length();
        int[] table = new int[m];
        int k = 0;  // сколько символов префикса совпало
        for (int q = 1; q < m; q++) {  // обход всех символов в паттерне
            while (k > 0 && pattern.charAt(k) != pattern.charAt(q)) {
                k = table[k - 1];
            }
            if (pattern.charAt(k) == pattern.charAt(q)) {
                k += 1;
            }
            table[q] = k;
        }
        return table;
    }
}
